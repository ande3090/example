package Sections;

import Framework.BaseGUI;
import Pages.BasePage;
import Pages.FilterPage;

public class KWTWizard extends BasePage {

    public KWTWizard(BaseGUI driver) {
        super(driver);
    }

    public KWTWizard chooseManual() {
        driver.addStep("Choose Manual keywords adding");
        driver.clickByCss("[data-test-id='btn-manual']");
        driver.waitForElementAppeared(".kwt-manual__textarea", 10);
        return this;
    }

    public KWTWizard addKeywords(String[] arr) {
        driver.addStep("Adding keywords");
        for (int i = 0; i < arr.length; i++) {
            driver.typeByCss(".s-textarea", arr[i]);
            driver.clickByCss("[data-test-id='kwt-tbx-add']");
        }

        return this;
    }

    public int getKeywordsCount() {
        driver.addStep("Getting keywords count");
        return Integer.parseInt(driver.getTextByCss(".count"));
    }

    public KWTWizard chooseLocationTab() {
        driver.addStep("Choosing location tab");
        driver.clickByCss(".kwt-dialog-tab:last-child");
        driver.waitForElementAppeared(".kwt-submit-form__inputs", 10);
        return this;
    }

    public KWTWizard setOverlayScreenCookie() {
        driver.addStep("Setting overlay screen cookie");
        driver.setCookie("actionButtonIntroTooltipClosed", "1");

        return this;
    }

    public KWTWizard chooseCountry() {
        driver.addStep("Choosing first available country");
        driver.clickByCss(".kwt-flagged-select");
        driver.waitForElementAppeared(".flag-option", 10);
        driver.clickByCss(".flag-option");

        return this;
    }

    public FilterPage clickSubmit() {
        driver.addStep("Clicking submit button");
        driver.clickByCss(".kwt-dialog__ok-button");
        return new FilterPage(driver);
    }

}
