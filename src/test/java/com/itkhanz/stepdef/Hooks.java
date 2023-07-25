package com.itkhanz.stepdef;

import com.itkhanz.core.DriverManager;
import com.itkhanz.core.ServerManager;
import com.itkhanz.pages.BasePage;
import com.itkhanz.utils.GlobalParamsUtils;
import com.itkhanz.utils.ScreenshotUtils;
import com.itkhanz.utils.TestUtils;
import com.itkhanz.utils.VideoUtils;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Hooks {

    TestUtils utils = new TestUtils();
    static GlobalParamsUtils params = new GlobalParamsUtils();
    static ServerManager serverManager = new ServerManager();
    DriverManager driverManager = new DriverManager();
    VideoUtils videoUtils = new VideoUtils();
    ScreenshotUtils screenshotUtils = new ScreenshotUtils();

    @BeforeAll
    public static void before_all() {
        params.initializeGlobalParams();
        serverManager.startServer();
    }

    @AfterAll
    public static void after_all() {
        serverManager.stopServer();
    }

    @Before
    public void setup() throws IOException {
        //params.initializeGlobalParams();

        //serverManager.startServer();

        setRoutingForApplicationLogs();

        driverManager.initializeDriver();

        videoUtils.startRecording();
    }

    @After
    public void teardown(Scenario scenario) throws IOException {

        if (scenario.isFailed()) {
            screenshotUtils.attachScreenshotToReport(scenario);
            screenshotUtils.saveScreenshotLocally(scenario);
        }

        videoUtils.stopRecording(scenario);

        driverManager.quitDriver();
    }

    private void setRoutingForApplicationLogs() {
        String logsFolderName = "logs" + File.separator + params.getPlatformName() + "_" + params.getDeviceName();
        File logFolder = new File(logsFolderName);
        if (!logFolder.exists()) {
            logFolder.mkdirs();
        }
        //route logs to separate file for each thread
        ThreadContext.put("ROUTINGKEY", logsFolderName); //LOG4J2
    }
}
