package com.itkhanz.stepdef;

import com.itkhanz.core.DriverManager;
import com.itkhanz.core.ServerManager;
import com.itkhanz.utils.*;
import io.cucumber.java.*;

import java.io.File;
import java.io.IOException;

public class Hooks {

    //TODO Add dependency injection with Guice or Pico Container

    //static GlobalParamsUtils params = new GlobalParamsUtils();
    //static ServerManager serverManager = new ServerManager();
    TestUtils utils = new TestUtils();
    DriverManager driverManager = new DriverManager();
    VideoUtils videoUtils = new VideoUtils();
    ScreenshotUtils screenshotUtils = new ScreenshotUtils();

    /*@BeforeAll
    public static void before_all() {
        params.initializeGlobalParams();
        serverManager.startServer();
    }

    @AfterAll
    public static void after_all() {
        serverManager.stopServer();
    }*/

    @Before
    public void setup() throws IOException {

        utils.setRoutingForApplicationLogs();

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


}
