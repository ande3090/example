package Pages;

import Framework.BaseGUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BasePage extends RemoteWebDriver {

    protected BaseGUI driver;

    public BasePage(BaseGUI driver) {
        this.driver = driver;
    }

    public BasePage open(String url) {
        driver.open(url);
        return this;
    }

    public BaseGUI getDriver() {
        return this.driver;
    }

}
