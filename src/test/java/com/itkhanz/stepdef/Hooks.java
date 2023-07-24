package com.itkhanz.stepdef;

import com.itkhanz.core.DriverManager;
import com.itkhanz.core.ServerManager;
import com.itkhanz.pages.BasePage;
import com.itkhanz.utils.GlobalParamsUtils;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.apache.logging.log4j.ThreadContext;

import java.io.File;
import java.io.IOException;

public class Hooks {

    static GlobalParamsUtils params = new GlobalParamsUtils();
    static ServerManager serverManager = new ServerManager();
    DriverManager driverManager = new DriverManager();

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
    }

    @After
    public void teardown() {
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
