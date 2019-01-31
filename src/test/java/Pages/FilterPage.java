package Pages;

import Framework.BaseGUI;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FilterPage extends BasePage {

    private static String BASE_URL = "https://www.semrush.com/keyword_tool/741706";

    public FilterPage(BaseGUI driver) {
        super(driver);
        driver.waitForElementAppeared(".intro-dialog-content", 10);
    }

    public FilterPage open() {
        driver.addStep("Opening filter page with url " + BASE_URL);
        driver.open(BASE_URL);
        driver.waitForElementDisappeared(".kwt-loading-indicator-fullsize", 10);
        driver.waitForElementAppeared("[data-test-id=\"keyword-cell\"]", 10);
        driver.waitForElementAppeared(".kwt-table__count", 10);

        return this;
    }

    public FilterPage setFilter(String text) {
        driver.addStep("Setting filter as " + text);
        String currentText = driver.getTextByCss(".kwt-table__count");
        driver.typeByCss(".kwt-filters__short input", text);
        driver.waitForElementDisappeared(".kwt-loading-indicator-fullsize", 10);
        driver.waitForTextChanged(currentText, ".kwt-table__count");
        return this;
    }

    public String getFilterText() {
        driver.addStep("Getting filter text");
        String s = null;
        try {
            s = driver.getAttributeByCss(".kwt-filters__short input", "value");
        } catch (Exception ex) {

        }

        driver.addStep("Current s = [" + s + "] ");
        if (s.equals("")) {
            s = null;
        }
        return s;
    }

    public boolean isResultsFound() {
        driver.addStep("Checking if results found after filtration");
        List<WebElement> elements = driver.findElementsByCss(".kwt-nothing-found");

        return elements.isEmpty();
    }

    public int getResultCounter() {
        driver.addStep("Getting result counter value");
        String counter = driver.getTextByCss(".kwt-table__count");
        counter = counter.replaceAll("[^\\d.]", "");
        return Integer.parseInt(counter);
    }

    public int getCellsCount() {
        driver.addStep("Getting found cells count");
        List<WebElement> elements = driver.findElementsByCss("[data-test-id='keyword-cell']");

        return elements.size();
    }

    public FilterPage clickClearButton() {
        driver.addStep("Clicking Clear Filter button");
        driver.clickByCss(".kwt-filters__short .s-input__clear");
        driver.waitForElementDisappeared(".kwt-filters__short .s-input__clear.-visible", 10);
        driver.waitForElementDisappeared(".kwt-loading-indicator-fullsize", 10);

        return this;
    }

    public boolean isFilterClearButtonVisible() {
        driver.addStep("Checking is Filter Clear Button visible");
        List<WebElement> elements = driver.findElementsByCss(".kwt-filters__short .s-input__clear.-visible");

        if (!elements.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public FilterPage closeTour() {
        driver.addStep("Clicking Close Tour button");
        driver.waitForElementAppeared(".close-tour", 10);
        driver.executeScript("$('.close-tour').click();");

        driver.waitForElementDisappeared(".product-tour-overlay", 10);

        return this;
    }

}
