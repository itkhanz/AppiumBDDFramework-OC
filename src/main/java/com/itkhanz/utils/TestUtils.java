package com.itkhanz.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestUtils {
    GlobalParamsUtils params = new GlobalParamsUtils();

    public static final long WAIT = 10;

    public static String getFormattedDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
    }

    public Logger log() {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }

    public void setRoutingForServerLogs() {
        String logsFolderName = "logs" + File.separator + "server" + File.separator + params.getDateTime();
        setRouting(logsFolderName);
    }

    public void setRoutingForApplicationLogs() {
        String logsFolderName = "logs" + File.separator + params.getPlatformName() + "_" + params.getDeviceName();
        setRouting(logsFolderName);
    }

    private void setRouting(String logsFolderName) {
        File logFolder = new File(logsFolderName);
        if (!logFolder.exists()) {
            logFolder.mkdirs();
        }
        //route logs to separate file for each thread
        ThreadContext.put("ROUTINGKEY", logsFolderName); //LOG4J2
    }
}
