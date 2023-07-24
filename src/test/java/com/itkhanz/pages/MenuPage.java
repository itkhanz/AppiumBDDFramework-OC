package com.itkhanz.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class MenuPage extends BasePage{
    @AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc='test-Menu']/android.view.ViewGroup/android.widget.ImageView")
    @iOSXCUITFindBy(xpath="//XCUIElementTypeOther[@name='test-Menu']/XCUIElementTypeOther")
    private WebElement settingsBtn;

    public SettingsPage pressSettingsBtn() {
        click(settingsBtn, "press Settings button");
        return new SettingsPage();
    }
}
