package com.itkhanz.utils;

import com.itkhanz.core.DriverManager;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.cucumber.java.Scenario;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class VideoUtils {
    TestUtils utils = new TestUtils();
    GlobalParamsUtils params = new GlobalParamsUtils();
    DriverManager driverManager = new DriverManager();

    public void startRecording(){
        ((CanRecordScreen) driverManager.getDriver()).startRecordingScreen();
    }

    public void stopRecording(Scenario scenario) throws IOException {
        String featureName = FilenameUtils.getBaseName(scenario.getUri().toString());
        String scenarioName = scenario.getName();

        String media = ((CanRecordScreen) driverManager.getDriver()).stopRecordingScreen();

        String dirPath =  "media" + File.separator
                + params.getDateTime() + File.separator
                + params.getPlatformName() + "_" + params.getDeviceName() + File.separator
                + "videos"
                ;

        File videoDir = new File(dirPath);

        synchronized(videoDir){
            if(!videoDir.exists()) {
                videoDir.mkdirs();
            }
        }
        String videoName = featureName + "_" + scenarioName.replaceAll("\\s+", "-");
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(videoDir + File.separator + videoName + ".mp4");
            stream.write(Base64.decodeBase64(media));
            stream.close();
            utils.log().info("video path: " + videoDir + File.separator + videoName + ".mp4");
        } catch (Exception e) {
            utils.log().error("error during video capture" + e.toString());
        } finally {
            if(stream != null) {
                stream.close();
            }
        }
    }
}
