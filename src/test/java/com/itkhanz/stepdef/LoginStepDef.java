package com.itkhanz.stepdef;

import com.itkhanz.pages.LoginPage;
import com.itkhanz.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginStepDef {
    @When("I enter username as {string}")
    public void iEnterUsernameAs(String username) {
        new LoginPage().enterUserName(username);
    }
    @When("I enter password as {string}")
    public void iEnterPasswordAs(String password) {
        new LoginPage().enterPassword(password);
    }
    @When("I login")
    public void iLogin() {
        new LoginPage().pressLoginBtn();
    }
    @Then("login should fail with an error {string}")
    public void loginShouldFailWithAnError(String err) {
        Assert.assertEquals(new LoginPage().getErrTxt(), err);
    }

    @Then("I should see Products page with title {string}")
    public void iShouldSeeProductsPageWithTitle(String title) {
        Assert.assertEquals(new ProductsPage().getTitle(), title);
    }

}
