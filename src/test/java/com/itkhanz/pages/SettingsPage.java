package com.itkhanz.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class SettingsPage extends BasePage {
    @AndroidFindBy(accessibility="test-LOGOUT")
    @iOSXCUITFindBy(id = "test-LOGOUT")
    private WebElement logoutBtn;

    public LoginPage pressLogoutBtn() {
        click(logoutBtn, "press logout button");
        return new LoginPage();
    }
}
