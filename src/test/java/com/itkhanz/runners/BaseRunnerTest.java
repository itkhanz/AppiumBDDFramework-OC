/*package com.itkhanz.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"}
        ,glue = {"com.itkhanz.stepdef"}
        ,snippets = CAMELCASE
        ,dryRun=false
        ,monochrome=true
        ,plugin = {
                "pretty",
                "summary",
                "html:target/cucumber/cucumber-report.html",
//                "json:target/cucumber/cucumber-report.json"
        }
        ,tags = "@test"

)
public class BaseRunnerTest {
}*/
