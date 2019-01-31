package Pages;

import Framework.BaseGUI;
import Sections.LoginSection;
import org.openqa.selenium.WebElement;

import java.util.List;


public class IndexPage extends BasePage {

    private static String BASE_URL = "https://www.semrush.com/";

    public IndexPage(BaseGUI driver) {
        super(driver);
    }

    public IndexPage open() {
        driver.addStep("Opening Index Page");
        driver.open(BASE_URL);

        return this;
    }

    public boolean isAuthorized() {
        driver.addStep("Checking if user authorized");
        List<WebElement> elements = driver.findElementsByCss("[data-test='header-menu__user']");

        return !elements.isEmpty();
    }

    public LoginSection loginSection() {
        return new LoginSection(driver);
    }


}
