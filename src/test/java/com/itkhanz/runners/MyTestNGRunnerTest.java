package com.itkhanz.runners;

import io.cucumber.testng.*;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

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
        "html:target/cucumber/cucumber-report.html",
//                "json:target/cucumber/cucumber-report.json"
}
        ,tags = "@test"
)
public class MyTestNGRunnerTest {
    private TestNGCucumberRunner testNGCucumberRunner;

    @Parameters({"platformName", "deviceName", "udid",
                "systemPort", "chromeDriverPort",
                "wdaLocalPort"})
    @BeforeClass(alwaysRun = true)
    public void setUpClass(ITestContext context,
                           String platformName, String udid, String deviceName,
                           @Optional("10000")String systemPort,
                           @Optional("11000")String chromeDriverPort,
                           @Optional("8100")String wdaLocalPort) {
        XmlTest currentXmlTest = context.getCurrentXmlTest();
        CucumberPropertiesProvider properties = currentXmlTest::getParameter;
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass(), properties);
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runScenario(pickle.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();
    }
}
