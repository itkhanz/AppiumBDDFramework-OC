package com.itkhanz.runners;

import com.itkhanz.core.ServerManager;
import com.itkhanz.utils.GlobalParamsUtils;
import com.itkhanz.utils.TestUtils;
import io.cucumber.testng.CucumberPropertiesProvider;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

public class BaseRunner {
    //private TestNGCucumberRunner testNGCucumberRunner;
    private static final ThreadLocal<TestNGCucumberRunner> testNGCucumberRunner = new ThreadLocal<>();

    public static TestNGCucumberRunner getRunner(){
        return testNGCucumberRunner.get();
    }

    private static void setRunner(TestNGCucumberRunner testNGCucumberRunner1){
        testNGCucumberRunner.set(testNGCucumberRunner1);
    }


    @Parameters({"platformName", "platformVersion", "deviceName", "udid",
            "systemPort", "chromeDriverPort",
            "wdaLocalPort"})
    @BeforeClass(alwaysRun = true)
    public synchronized void setUpClass(ITestContext context,
                           String platformName, String platformVersion, String deviceName, String udid,
                           @Optional("10000")String systemPort,
                           @Optional("11000")String chromeDriverPort,
                           @Optional("8100")String wdaLocalPort) {

        //Initializing the global params
        GlobalParamsUtils params = new GlobalParamsUtils();
        params.setPlatformName(platformName);
        params.setplatformVersion(platformVersion);
        params.setUDID(udid);
        params.setDeviceName(deviceName);
        params.setDateTime(TestUtils.getFormattedDateTime());

        switch (platformName) {
            case "Android" -> {
                params.setSystemPort(systemPort);
                params.setChromeDriverPort(chromeDriverPort);
                System.setProperty("ANDROID_INFO", params.getPlatformName() + "  " + params.getplatformVersion() + " | " + params.getDeviceName());
            }
            case "iOS" -> {
                params.setWdaLocalPort(wdaLocalPort);
                System.setProperty("IOS_INFO",  params.getPlatformName() + "  " + params.getplatformVersion() + " | " + params.getDeviceName());
            }
        }


        //start the appium server
        new ServerManager().startServer();

        XmlTest currentXmlTest = context.getCurrentXmlTest();
        CucumberPropertiesProvider properties = currentXmlTest::getParameter;
        //testNGCucumberRunner = new TestNGCucumberRunner(this.getClass(), properties);
        setRunner(new TestNGCucumberRunner(this.getClass(), properties));
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) {
        //testNGCucumberRunner.runScenario(pickle.getPickle());
        getRunner().runScenario(pickle.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        //return testNGCucumberRunner.provideScenarios();
        return getRunner().provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        //if(testNGCucumberRunner != null) getRunner().finish();
        if(getRunner() != null) getRunner().finish();

        //stop the appium server
        new ServerManager().stopServer();
    }
}
