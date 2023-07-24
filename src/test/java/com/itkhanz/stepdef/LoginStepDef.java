package com.itkhanz.stepdef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDef {
    @When("I enter username as {string}")
    public void iEnterUsernameAs(String username) {

    }
    @When("I enter password as {string}")
    public void iEnterPasswordAs(String password) {

    }
    @When("I login")
    public void iLogin() {

    }
    @Then("login should fail with an error {string}")
    public void loginShouldFailWithAnError(String error) {

    }

    @Then("I should see Products page with title {string}")
    public void iShouldSeeProductsPageWithTitle(String title) {

    }

}
