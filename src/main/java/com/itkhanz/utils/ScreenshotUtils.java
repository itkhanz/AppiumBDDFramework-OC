package com.itkhanz.utils;

import com.itkhanz.core.DriverManager;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ScreenshotUtils {
    TestUtils utils = new TestUtils();
    GlobalParamsUtils params = new GlobalParamsUtils();
    DriverManager driverManager = new DriverManager();

    public void attachScreenshotToReport(Scenario scenario) {
        String scenarioName = scenario.getName();
        byte[] screenshot = driverManager.getDriver().getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", scenarioName);
    }

    public void saveScreenshotLocally(Scenario scenario) {
        //String fullFeatureName = FilenameUtils.getName(scenario.getUri().toString());
        String featureName = FilenameUtils.getBaseName(scenario.getUri().toString());
        String scenarioName = scenario.getName();
        String imageName = featureName + "_" + scenarioName.replaceAll("\\s+", "-");

        String imagePath =  "media" + File.separator
                + params.getDateTime() + File.separator
                + params.getPlatformName() + "_" + params.getDeviceName() + File.separator
                + "screenshots" + File.separator
                + imageName + ".png"
                ;

        String completeImagePath = Paths.get(imagePath).toAbsolutePath().toString();
        File file = driverManager.getDriver().getScreenshotAs(OutputType.FILE);

        //save the file to disk
        try {
            FileUtils.copyFile(file, new File(imagePath));
            utils.log().info("screenshot saved at : " + imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            utils.log().info("Failed to save the screenshot");
        }
    }
}
