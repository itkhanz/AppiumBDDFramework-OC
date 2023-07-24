package com.itkhanz.core;

import com.itkhanz.utils.GlobalParamsUtils;
import com.itkhanz.utils.PropertyUtils;
import com.itkhanz.utils.TestUtils;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.Properties;

public class DriverOptionsManager {
    TestUtils utils = new TestUtils();
    GlobalParamsUtils params;
    Properties props;

    public DriverOptionsManager() throws IOException {
        utils = new TestUtils();
        params = new GlobalParamsUtils();
        props = new PropertyUtils().getProps();
    }

    public UiAutomator2Options getAndroidOptions() {
        utils.log().info("getting Android UiAutomator2Options");
        String appURL = Objects.requireNonNull(getClass().getResource(props.getProperty("androidAppLocation"))).getFile();
        return new UiAutomator2Options()
                .setAutomationName(props.getProperty("androidAutomationName"))
                .setPlatformName(params.getPlatformName())
                .setDeviceName(params.getDeviceName())   //AvdId (not needed with udid)
                .setUdid(params.getUDID()) //UDID is preferred over platform version and platform name to uniquely identify device
                .setAppPackage(props.getProperty("androidAppPackage"))
                .setAppActivity(props.getProperty("androidAppActivity"))
                //.setApp(appURL) //not needed when app is pre-installed
                .setAppWaitActivity(props.getProperty("androidAppWaitActivity"))    //wait for the main activity to start, must use it when using appurl instead of appPackage and appActivity
                .setAppWaitDuration(Duration.ofSeconds(30))
                .setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(60))
                .setSystemPort(Integer.parseInt(params.getSystemPort()))
                .setChromedriverPort(Integer.parseInt(params.getChromeDriverPort()))
                ;
    }

    public XCUITestOptions getIOSOptions() {
        utils.log().info("getting iOS XCUITestOptions");
        String appURL = Objects.requireNonNull(getClass().getResource(props.getProperty("iOSAppLocation"))).getFile();
        return new XCUITestOptions()
                .setAutomationName(props.getProperty("iOSAutomationName"))
                .setPlatformName(params.getPlatformName())
                .setDeviceName(params.getDeviceName())   // not needed with udid
                .setUdid(params.getUDID()) //not needed when device name and platform version are enough to locate the emulator running
                .setBundleId(props.getProperty("iOSBundleId")) //not needed with appUrl
                //.setApp(appURL) //not needed when app is pre-installed
                .setUsePrebuiltWda(true)    //speeds up the test execution if WDA is already on the device
                .setWdaLocalPort(Integer.parseInt(params.getWdaLocalPort()))
                ;
    }
}
