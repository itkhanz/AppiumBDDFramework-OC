package com.itkhanz.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;

public class ProductDetailsPage extends BasePage{
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]\n" +
            "")
    @iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[1]")
    private WebElement title;

    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]"
            + "")
    @iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[2]")
    private WebElement desc;

    @AndroidFindBy (accessibility = "test-BACK TO PRODUCTS")
    @iOSXCUITFindBy (id = "test-BACK TO PRODUCTS")
    private WebElement backToProductsBtn;

    @iOSXCUITFindBy(accessibility = "test-Price") private WebElement iOSProductPrice;

    @iOSXCUITFindBy (id = "test-ADD TO CART") private WebElement addToCartBtn;

    public String getTitle() {
        String productTitle = getLabelText(title, "getting Product Title attribute label/test");
        utils.log().info("Product title on DetailsPage is: " + productTitle);
        return productTitle;
    }

    public String getDesc() {
        String productTxt = getLabelText(desc, "getting Product Text attribute label/test");
        utils.log().info("Product text on DetailsPage is: " + productTxt);
        return productTxt;
    }

    public String getPrice() throws Exception {
        switch(globalParams.getPlatformName()){
            case "Android":
                return getLabelText(andScrollToElementUsingUiScrollable("description", "test-Price"), "price is: ");
            case "iOS":
                return getLabelText(iOSScrollToElementUsingMobileScroll(iOSProductPrice), "price is: ");
            default:
                throw new Exception("Invalid platform name");
        }
    }

    public ProductsPage pressBackToProductsBtn() {
        click(backToProductsBtn, "Navigating back to products page");
        return new ProductsPage();
    }

    public Boolean isAddToCartBtnDisplayed() {
        return addToCartBtn.isDisplayed();
    }
}
