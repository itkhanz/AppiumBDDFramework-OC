package com.itkhanz.pages;

import com.itkhanz.core.DriverManager;
import com.itkhanz.utils.GlobalParamsUtils;
import com.itkhanz.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;

import static com.itkhanz.utils.TestUtils.WAIT;

public class BasePage {
    protected AppiumDriver driver;
    TestUtils utils;

    GlobalParamsUtils globalParams;

    public BasePage(){
        this.driver = new DriverManager().getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
        utils = new TestUtils();
        globalParams = new GlobalParamsUtils();
    }

    public void closeApp() {
        switch (globalParams.getPlatformName()) {
            case "Android" -> ((InteractsWithApps) driver).terminateApp(driver.getCapabilities().getCapability("appPackage").toString());
            case "iOS" -> ((InteractsWithApps) driver).terminateApp(driver.getCapabilities().getCapability("bundleId").toString());
        }
    }

    public void launchApp() {
        switch (globalParams.getPlatformName()) {
            case "Android" -> ((InteractsWithApps) driver).activateApp(driver.getCapabilities().getCapability("appPackage").toString());
            case "iOS" -> ((InteractsWithApps) driver).activateApp(driver.getCapabilities().getCapability("bundleId").toString());
        }
    }

    public void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public void waitForVisibility(By e) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(e));
    }

    public void click(WebElement element) {
        waitForVisibility(element);
        element.click();
    }
    public void click(WebElement e, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        e.click();
    }
    public void click(By e, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        driver.findElement(e).click();
    }

    public void clear(WebElement element) {
        waitForVisibility(element);
        element.clear();
    }

    public void sendKeys(WebElement element, String text) {
        waitForVisibility(element);
        element.sendKeys(text);
    }
    public void sendKeys(WebElement e, String txt, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        //ExtentManager.getTest().log(Status.INFO, msg);
        e.sendKeys(txt);
    }

    public String getAttribute(WebElement element, String attribute) {
        waitForVisibility(element);
        String attr =  element.getAttribute(attribute);
        //ExtentManager.getTest().log(Status.INFO, attribute + " attribute value is: " + attr);
        return attr;
    }
    public String getAttribute(By e, String attribute) {
        waitForVisibility(e);
        return driver.findElement(e).getAttribute(attribute);
    }
    public String getAttribute(WebElement element, String attribute, String msg) {
        utils.log().info(msg);
        //ExtentManager.getTest().log(Status.INFO, msg);
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }

    public String getLabelText(WebElement e) {
        String txt = null;
        return switch (globalParams.getPlatformName()) {
            case "Android" -> getAttribute(e, "text");
            case "iOS" -> getAttribute(e, "label");
            default -> null;
        };
    }

    public String getLabelText(WebElement e, String msg) {
        utils.log().info(msg);
        //ExtentManager.getTest().log(Status.INFO, msg);
        String txt = null;

        switch (globalParams.getPlatformName()) {
            case "Android" -> {
                txt = getAttribute(e, "text");
            }
            case "iOS" -> {
                txt = getAttribute(e, "label");
            }
            default -> {
                return txt;
            }
        };
        return txt;
    }

    public String getLabelText(By e, String msg) {
        utils.log().info(msg);
        //ExtentManager.getTest().log(Status.INFO, msg);
        String txt = null;

        switch (globalParams.getPlatformName()) {
            case "Android" -> {
                txt = getAttribute(e, "text");
            }
            case "iOS" -> {
                txt = getAttribute(e, "label");
            }
            default -> {
                return txt;
            }
        };
        return txt;
    }

    public WebElement andScrollToElementUsingUiScrollable(String childLocAttr, String childLocValue) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                        + "new UiSelector()."+ childLocAttr +"(\"" + childLocValue + "\"));"));
    }

    public WebElement iOSScrollToElementUsingMobileScroll(WebElement e) {
        RemoteWebElement element = ((RemoteWebElement) e);
        String elementID = element.getId();
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("element", elementID);
        scrollObject.put("toVisible", "sdfnjksdnfkld");
        driver.executeScript("mobile:scroll", scrollObject);
        return e;
    }

    public By iOSScrollToElementUsingMobileScrollParent(WebElement parentE, String predicateString) {
        RemoteWebElement parent = (RemoteWebElement)parentE;
        String parentID = parent.getId();
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("element", parentID);
        scrollObject.put("predicateString", predicateString);
        driver.executeScript("mobile:scroll", scrollObject);
        return AppiumBy.iOSNsPredicateString(predicateString);

    }
}
