package Pages;

import Framework.BaseGUI;
import Sections.KWTWizard;
import Sections.LoginSection;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProjectsPage extends BasePage {

    private static String BASE_URL = "https://www.semrush.com/projects/#no-projects/";

    public ProjectsPage(BaseGUI driver) {
        super(driver);
    }

    public ProjectsPage open() {
        driver.addStep("Opening Projects page");
        driver.open(BASE_URL);
        driver.waitForElementDisappeared(".s-dashboard__spinner", 15);

        return this;
    }

    public ProjectsPage clickProjectsTab() {
        driver.addStep("Clicking project tab in left menu");
        driver.clickByDataTest("tools");
        driver.waitForElementAppeared(".js-projects-menu:not([style='display: none'])", 10);

        return this;
    }

    public ProjectsPage clickNewProject() {
        driver.addStep("Clicking New Project button in left menu");
        driver.clickByCss(".lm-menu__project-button-section a");
        driver.waitForElementAppeared(".pr-page .js-add-project", 10);
        return this;
    }

    public ProjectsPage createNewProject(String title, String url) {
        driver.addStep("Creating new project with modal form");
        driver.clickByCss(".pr-page .js-add-project");
        driver.waitForElementAppeared(".pr-dialog-form", 10);
        driver.typeByCss(".js-input-domain", url);
        driver.typeByCss(".js-input-name", title);
        driver.clickByCss(".js-save-pr");
        driver.waitForElementDisappeared(".pr-page .js-add-project", 20);
        driver.waitForElementAppeared(".js-kwt .js-ssa-setup", 20);

        return this;

    }

    public ProjectsPage clickTestProject() {
        driver.addStep("Clicking existing test project in left menu");
        driver.clickByCss(".js-goto-project");
        driver.waitForElementAppeared(".pr-page__header", 10);

        return this;
    }

    public KWTWizard clickSetupWidget() {
        driver.addStep("Clicking setup widget button");
        driver.clickByCss(".js-kwt .js-ssa-setup");
        driver.waitForElementAppeared(".kwt-wizard", 10);

        return new KWTWizard(driver);
    }

    public boolean isAuthorized() {
        driver.addStep("Checking is user authorized");
        List<WebElement> elements = driver.findElementsByCss("[data-test='header-menu__user']");

        if (!elements.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public LoginSection loginSection() {
        return new LoginSection(driver);
    }


}
