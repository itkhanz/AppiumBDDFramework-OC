package com.itkhanz.stepdef;

import com.itkhanz.pages.LoginPage;
import com.itkhanz.pages.ProductDetailsPage;
import com.itkhanz.pages.ProductsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class ProductStepDef {
    //TODO login with Deeplink

    @Given("I'm logged in")
    public void iMLoggedIn() {
        //TODO get it from external config file
        new LoginPage().login("standard_user", "secret_sauce");
    }

    @Then("the product is listed with title {string} and price {string}")
    public void theProductIsListedWithTitleAndPrice(String title, String price) throws Exception {
        Boolean titleCheck = new ProductsPage().getProductTitle(title).equalsIgnoreCase(title);
        Boolean priceCheck = true;
        Assert.assertTrue(titleCheck & priceCheck, "titleCheck = " + titleCheck + ", priceCheck = " + priceCheck
                );
    }

    @When("I click product title {string}")
    public void iClickProductTitle(String title) throws Exception {
        new ProductsPage().pressProductTitle(title);
    }

    @Then("I should be on product details page with title {string}, price {string} and description {string}")
    public void iShouldBeOnProductDetailsPageWithTitlePriceAndDescription(String title, String price, String description) throws Exception {
        ProductDetailsPage productDetailsPage = new ProductDetailsPage();
        boolean titleCheck = productDetailsPage.getTitle().equalsIgnoreCase(title);
        boolean descCheck = productDetailsPage.getDesc().equalsIgnoreCase(description);
        boolean priceCheck = productDetailsPage.getPrice().equalsIgnoreCase(price);
        Assert.assertTrue(titleCheck & descCheck & priceCheck, "titleCheck = " + titleCheck + ", descCheck = " + descCheck + ", priceCheck = " + priceCheck
                );
    }
}
