package com.itkhanz.core;

import com.itkhanz.utils.GlobalParamsUtils;
import com.itkhanz.utils.TestUtils;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.ThreadContext;

import java.io.File;
import java.util.HashMap;

public class ServerManager {
    private static ThreadLocal<AppiumDriverLocalService> server = new ThreadLocal<>();
    TestUtils utils = new TestUtils();
    GlobalParamsUtils params = new GlobalParamsUtils();

    public AppiumDriverLocalService getServer(){
        return server.get();
    }

    public void startServer(){
        utils.setRoutingForServerLogs();
        utils.log().info("*********** Starting Appium Server ***********");
        AppiumDriverLocalService service = getAppiumServiceForMac();
        service.start();
        if(service == null || !service.isRunning()){
            utils.log().fatal("Appium server not started. ABORT!!!");
            throw new AppiumServerHasNotBeenStartedLocallyException("Appium server not started. ABORT!!!");
        }
        service.clearOutPutStreams(); // -> Comment this if you want to see server logs in the console
        server.set(service);
        System.setProperty("APPIUM_SERVER_IP_ADDRESS", String.valueOf(getServer().getUrl()));
        utils.log().info("*********** Appium Server Started Successfully ***********");
    }

    public void stopServer() {
        if (getServer().isRunning()) {
            getServer().stop();
            server.remove();
            utils.log().info("*********** Appium Server Stopped ***********");
        }
    }

    private AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    private AppiumDriverLocalService getAppiumServiceForWindows() {
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withLogFile(new File(
                                "logs" + File.separator +
                                        "server" + File.separator +
                                        params.getDateTime() + File.separator +
                                        "server.log"
                        )
                )
        );
    }

    private AppiumDriverLocalService getAppiumServiceForMac() {
        GlobalParamsUtils params = new GlobalParamsUtils();
        //comment this line if you are running tests through maven surefire plugin
        HashMap<String, String> environment = getEnvironmentMapForAppiumServer();

        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("/Users/ibkh/.nvm/versions/node/v18.16.0/bin/node"))
                .withAppiumJS(new File("/Users/ibkh/.nvm/versions/node/v18.16.0/lib/node_modules/appium/index.js"))
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.USE_DRIVERS, "uiautomator2,xcuitest")
                .withEnvironment(environment)
                .withLogFile(new File(
                        "logs" + File.separator +
                                 "server" + File.separator +
                                 params.getDateTime() + File.separator +
                                 "server.log"
                        )
                )
        );
    }

    private HashMap<String, String> getEnvironmentMapForAppiumServer() {
        //IntelliJ does not have access to path and environment variables which are necessary to run appium server like JDK, Android SDK, cmdline-tools etc
        //so we need to provide it in the code by ourselves
        HashMap<String, String> environment = new HashMap<String, String>();
        //RUN echo $PATH
        //Add the PATH for Node, JDK, Maven, ANDROID_HOME, Android platform-tools, cmdline-tools
        final String NODE = "/Users/ibkh/.nvm/versions/node/v18.16.0/bin";
        final String MAVEN = "/usr/local/bin:/Library/Java/JavaVirtualMachines/jdk-17.0.2.jdk/Contents/Home/bin";
        final String JAVA = "/Library/Maven/apache-maven-3.9.2/bin";
        final String ANDROID_CMD_TOOLS = "/Users/ibkh/Library/Android/sdk/cmdline-tools";
        final String ANDROID_PLATFORM_TOOLS = "/Users/ibkh/Library/Android/sdk/platform-tools";

        final String zshrcPath = NODE + ":" + MAVEN + ":" + JAVA + ":" + ANDROID_CMD_TOOLS + ":" + ANDROID_PLATFORM_TOOLS;
        //RUN where xcode-select will give result as /usr/bin/xcode-select
        //Append to the PATH
        final String xcodeSelect = ":/usr/bin/";

        environment.put("PATH", zshrcPath + xcodeSelect);

        //ANDROID_HOME can be found by echo $ANDROID_HOME or opening SDK Manager in Android Studio
        final String ANDROID_HOME = "/Users/ibkh/Library/Android/sdk";
        environment.put("ANDROID_HOME", ANDROID_HOME);

        return environment;
    }

}
