package com.itkhanz.stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductStepDef {
    @Given("I'm logged in")
    public void iMLoggedIn() {

    }
    @Then("the product is listed with title {string} and price {string}")
    public void theProductIsListedWithTitleAndPrice(String title, String price) {

    }

    @When("I click product title {string}")
    public void iClickProductTitle(String string) {

    }
    @Then("I should be on product details page with title {string}, price {string} and description {string}")
    public void iShouldBeOnProductDetailsPageWithTitlePriceAndDescription(String title, String price, String description) {

    }
}
