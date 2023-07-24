package com.itkhanz.core;

import com.itkhanz.utils.GlobalParamsUtils;
import com.itkhanz.utils.PropertyUtils;
import com.itkhanz.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;

public class DriverManager {
    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<String> driverSessionID = new ThreadLocal<>();
    TestUtils utils = new TestUtils();

    public AppiumDriver getDriver(){
        return driver.get();
    }

    private void setDriver(AppiumDriver driver2){
        driver.set(driver2);
    }

    public String getDriverSessionID() {
        return driverSessionID.get();
    }

    private void setDriverSessionID(String sessionID) {
        driverSessionID.set(sessionID);
    }

    public void initializeDriver() throws IOException {
        AppiumDriver driver = null;
        GlobalParamsUtils params = new GlobalParamsUtils();
        PropertyUtils props = new PropertyUtils();

        try{
            utils.log().info("Initializing Appium driver");
            switch (params.getPlatformName()) {
                case "Android" ->
                        driver = new AndroidDriver(new ServerManager().getServer().getUrl(), new DriverOptionsManager().getAndroidOptions());
                case "iOS" ->
                        driver = new IOSDriver(new ServerManager().getServer().getUrl(), new DriverOptionsManager().getIOSOptions());
            }
            if(driver == null){
                throw new RuntimeException("driver is null. ABORT!!!");
            }

            setDriver(driver);
            setDriverSessionID(getDriver().getSessionId().toString());
            utils.log().info("******** Driver is initialized with session ID: {} ***********", getDriverSessionID());
        } catch (IOException e) {
            e.printStackTrace();
            utils.log().fatal("******** Driver initialization failure. ABORT !!!!" + e.toString() + " ********");
            throw e;
        }
    }

    public void quitDriver() {
        if (getDriver()!= null)  {
            getDriver().quit();
            driver.remove();
            utils.log().info("******** Driver closed with session ID: {} ***********", getDriverSessionID());
            driverSessionID.remove();
        }
    }
}
