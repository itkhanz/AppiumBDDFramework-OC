package com.itkhanz.listeners;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.support.Status;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class SuiteListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        ISuiteListener.super.onStart(suite);
    }

    @Override
    public void onFinish(ISuite suite) {
        //ISuiteListener.super.onFinish(suite);

        System.out.println("********* Report generation started ****************");

        File reportOutputDirectory = new File("target");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber/android-cucumber-report.json");
        jsonFiles.add("target/cucumber/ios-cucumber-report.json");

        String buildNumber = "1";
        String projectName = "Appium-BDD-Framework";

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        // optional configuration - check javadoc for details
        configuration.setBuildNumber(buildNumber);
        configuration.addPresentationModes(PresentationMode.PARALLEL_TESTING);
        configuration.setSortingMethod(SortingMethod.NATURAL);

        // do not make scenario failed when step has status SKIPPED
        //configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));

        // addidtional metadata presented on main page
        configuration.addClassifications("Server IP", System.getProperty("APPIUM_SERVER_IP_ADDRESS"));

        // optionally add metadata presented on main page via properties file
        List<String> classificationFiles = new ArrayList<>();
        classificationFiles.add("target//classes//project.properties");
        configuration.addClassificationFiles(classificationFiles);

        // optionally specify qualifiers for each of the report json files
        configuration.setQualifier("android-cucumber-report", System.getProperty("ANDROID_INFO"));
        configuration.setQualifier("ios-cucumber-report", System.getProperty("IOS_INFO"));

        ReportBuilder reportBuilder=new ReportBuilder(jsonFiles,configuration);
        Reportable result=reportBuilder.generateReports();
        // and here validate 'result' to decide what to do if report has failed

        System.out.println("********* Report generation ended ****************");

    }

}
