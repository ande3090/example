package Sections;

import Framework.BaseGUI;
import Pages.BasePage;
import Pages.ProjectsPage;

import java.util.Map;

public class LoginSection extends BasePage {

    public LoginSection(BaseGUI driver) {
        super(driver);
    }

    public LoginSection clickLoginButton() {
        driver.addStep("Clicking logging button to open Login popup");
        driver.clickByCss(".js-authentication-login");
        driver.waitForElementAppeared("[data-test='auth-popup__wrap-login']", 10);
        return this;
    }

    public LoginSection fillForm(Map<String, String> user) {
        driver.addStep("Filling form with " + user.toString());
        driver.typeByDataTest("auth-popup__email", user.get("login"));
        driver.typeByDataTest("auth-popup__password", user.get("password"));

        return this;
    }

    public ProjectsPage clickSubmit() {
        driver.addStep("Clicking submit button for authentication");
        driver.clickByDataTest("auth-popup__submit");
        driver.waitForElementAppeared("[data-test='header-menu__user']", 20);
        driver.waitForElementDisappeared(".auth-popup__wrap-login", 10);
        driver.waitForElementDisappeared(".s-dashboard__spinner", 15);

        return new ProjectsPage(driver);
    }

}
