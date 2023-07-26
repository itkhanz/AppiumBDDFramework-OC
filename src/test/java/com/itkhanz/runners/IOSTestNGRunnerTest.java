package com.itkhanz.runners;

import io.cucumber.testng.CucumberOptions;

import static io.cucumber.testng.CucumberOptions.SnippetType.CAMELCASE;

@CucumberOptions(
        features = {"src/test/resources/features"}
        ,glue = {"com.itkhanz.stepdef"}
        ,snippets = CAMELCASE
        ,dryRun=false
        ,monochrome=true
        ,plugin = {
        "pretty",
        "summary",
        "html:target/cucumber/ios-cucumber-report.html",
        "json:target/cucumber/ios-cucumber-report.json"
}
//        ,tags = "@test"
)
public class IOSTestNGRunnerTest extends BaseRunner{
}
