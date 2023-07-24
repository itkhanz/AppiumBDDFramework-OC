package com.itkhanz.stepdef;

import com.itkhanz.factory.ServerManager;
import com.itkhanz.utils.GlobalParams;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.apache.logging.log4j.ThreadContext;

import java.io.File;

public class Hooks {

    ServerManager serverManager = new ServerManager();
    GlobalParams params = new GlobalParams();

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
    public void setup() {
        String appLogsFilePath = "logs" + File.separator + params.getPlatformName() + "_" + params.getDeviceName();
        ThreadContext.put("ROUTINGKEY", appLogsFilePath);
    }

    @After
    public void teardown() {

    }
}
