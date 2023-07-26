/*
**********************************************************************
* This Runner Class is a legacy TestNG Runner class that was used to run tests sequentially.
* Now we are using the separate runner classes inheriting from the base runner class to pass separately to each test in testng.xml
**********************************************************************
 */

/*
package com.itkhanz.runners;

//import com.itkhanz.core.ServerManager;
//import com.itkhanz.utils.GlobalParamsUtils;
//import io.cucumber.testng.*;
//import org.testng.ITestContext;
//import org.testng.annotations.*;
//import org.testng.xml.XmlTest;

import static io.cucumber.testng.CucumberOptions.SnippetType.CAMELCASE;

@CucumberOptions(
        features = {"src/test/resources/features/login.feature"}
        ,glue = {"com.itkhanz.stepdef"}
        ,snippets = CAMELCASE
        ,dryRun=false
        ,monochrome=true
        ,plugin = {
        "pretty",
        "summary",
        "html:target/cucumber/cucumber-report.html",
        }
        ,tags = "@test"
)
public class MyTestNGRunnerTest {
    private TestNGCucumberRunner testNGCucumberRunner;

    static ServerManager serverManager = new ServerManager();

    @Parameters({"platformName", "deviceName", "udid",
                "systemPort", "chromeDriverPort",
                "wdaLocalPort"})
    @BeforeClass(alwaysRun = true)
    public void setUpClass(ITestContext context,
                           String platformName, String deviceName, String udid,
                           @Optional("10000")String systemPort,
                           @Optional("11000")String chromeDriverPort,
                           @Optional("8100")String wdaLocalPort) {

        GlobalParamsUtils params = new GlobalParamsUtils();
        params.setPlatformName(platformName);
        params.setUDID(udid);
        params.setDeviceName(deviceName);

        switch (platformName) {
            case "Android" -> {
                params.setSystemPort(systemPort);
                params.setChromeDriverPort(chromeDriverPort);
            }
            case "iOS" -> {
                params.setWdaLocalPort(wdaLocalPort);
            }
        }

        serverManager.startServer();

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
        if(testNGCucumberRunner != null) testNGCucumberRunner.finish();

        serverManager.stopServer();
    }
}
*/
