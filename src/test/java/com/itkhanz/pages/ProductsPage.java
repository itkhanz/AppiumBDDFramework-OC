package com.itkhanz.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductsPage extends BasePage {
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Cart drop zone']//android.widget.TextView")
    @iOSXCUITFindBy(xpath ="//XCUIElementTypeOther[@name='test-Toggle']/parent::*[1]/preceding-sibling::*[1]")
    private WebElement productTitleTxt;

    @iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-PRODUCTS\"]/XCUIElementTypeScrollView")
    private WebElement iOSSCrollView;

    public String getTitle() {
        //return getAttribute(productTitleTxt, "text");
        String title =  getLabelText(productTitleTxt, "getting SLB Text attribute label/test");
        utils.log().info("product page title is - " + title);
        return title;
    }

    public String getProductTitle(String title) throws Exception {
        return switch (globalParams.getPlatformName()) {
            case "Android" ->
                    getLabelText(andScrollToElementUsingUiScrollable("text", title), "product title is: " + title);
            case "iOS" ->
                    getLabelText(iOSScrollToElementUsingMobileScrollParent(iOSSCrollView, "label == '" + title + "'"),
                            "product title is: " + title);
            default -> throw new Exception("Invalid platform name");
        };
    }

    public By defProductPrice(String title) throws Exception {
        return switch (globalParams.getPlatformName()) {
            case "Android" ->
                    By.xpath("//*[@text=\"" + title + "\"]/following-sibling::*[@content-desc=\"test-Price\"]");
            case "iOS" ->
                    By.xpath("//XCUIElementTypeOther[@name=\"" + title + "\"]/following-sibling::*[1]/child::XCUIElementTypeStaticText[@name=\"test-Price\"]");
            default -> throw new Exception("Invalid platform name");
        };
    }

    public ProductDetailsPage pressProductTitle(String title) throws Exception {
        switch (globalParams.getPlatformName()) {
            case "Android" -> {
                click(andScrollToElementUsingUiScrollable("text", title), "press " + title + " link");
                return new ProductDetailsPage();
            }
            case "iOS" -> {
                click(iOSScrollToElementUsingMobileScrollParent(iOSSCrollView, "label == '" + title + "'"), "press " + title + " link");
                return new ProductDetailsPage();
            }
            default -> throw new Exception("Invalid platform name");
        }
    }
}
