package com.itkhanz.stepdef;

import com.itkhanz.core.DriverManager;
import com.itkhanz.core.ServerManager;
import com.itkhanz.utils.GlobalParamsUtils;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.apache.logging.log4j.ThreadContext;

import java.io.File;
import java.io.IOException;

public class Hooks {

    GlobalParamsUtils params = new GlobalParamsUtils();
    ServerManager serverManager = new ServerManager();
    DriverManager driverManager = new DriverManager();

    @BeforeAll
    public void beforeAll() {
        params.initializeGlobalParams();
        serverManager.startServer();
    }

    @AfterAll
    public void afterAll() {
        serverManager.stopServer();
    }

    @Before
    public void setup() throws IOException {
        //params.initializeGlobalParams();

        //serverManager.startServer();

        String appLogsFilePath = "logs" + File.separator + params.getPlatformName() + "_" + params.getDeviceName();
        ThreadContext.put("ROUTINGKEY", appLogsFilePath);

        driverManager.initializeDriver();
    }

    @After
    public void teardown() {
        driverManager.quitDriver();
    }
}
