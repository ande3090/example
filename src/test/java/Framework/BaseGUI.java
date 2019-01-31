package Framework;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.github.bonigarcia.wdm.*;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseGUI extends RemoteWebDriver {

    protected RemoteWebDriver driver;
    private SEMRushLogger logger;

    public BaseGUI() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        WebDriverManager.chromedriver().setup();

        this.driver = new ChromeDriver();
        this.logger = new SEMRushLogger();
        driver.manage().window().maximize();
    }

    public WebElement findElementByCss(String css) {
        logger.addStep("Trying to find element with css " + css);
        try {
            WebElement el = driver.findElement(By.cssSelector(css));
            logger.addInfo("Successfully found element with css " + css);
            return el;

        } catch (org.openqa.selenium.NoSuchElementException ex) {
            logger.addWarn("Problem during looking element with css " + css + ":\n" + ex.toString());
        }

        return null;
    }

    public List<WebElement> findElementsByCss(String css) {
        logger.addStep("Trying to find all elements by css " + css);
        List<WebElement> elements = driver.findElementsByCssSelector(css);

        if (elements.isEmpty()) {
            logger.addWarn("No elements with css " + css + " found");
        } else {
            logger.addInfo("At least 1 element with css " + css + " found!");
        }

        return elements;
    }

    public void clickByCss(String css) {
        logger.addStep("Trying to click element with css " + css);
        WebElement el = this.findElementByCss(css);
        try {
            el.click();
            logger.addInfo("Element with css " + css + " successfully clicked!");
        } catch (Exception ex) {
            logger.addWarn("Having a problem during clicking element with css" + css + ":\n" + ex.toString());
        }
    }

    public void clickByDataTest(String dataTest) {
        logger.addStep("Trying to click element with dataTest " + dataTest);
        WebElement el = this.findElementByCss("[data-test='" + dataTest + "']");
        try {
            el.click();
            logger.addInfo("Element with data test " + dataTest + " successfully found!");
        } catch (NoSuchElementException ex) {
            logger.addWarn("Having a problem during clicking element with css" + dataTest + ":\n" + ex.toString());
        }
    }

    public void typeByDataTest(String dataTest, String text) {
        logger.addStep("Trying to type text [" + text + "] in element with Data Test" + dataTest);

        WebElement el = this.findElementByCss("[data-test='" + dataTest + "']");

        try {
            el.clear();
            el.sendKeys(text);
            logger.addInfo("Successfully typed " + text + " in element with Data Test " + dataTest);

        } catch (NoSuchElementException ex) {
            logger.addWarn("Something went wrong during typing  " + text + " in element with Data Test " + dataTest);
        }
    }

    public void typeByCss(String css, String text) {
        logger.addStep("Trying to type text [" + text + "] in element with css " + css);

        WebElement el = this.findElementByCss(css);

        try {
            el.clear();
            el.sendKeys(text);
            logger.addInfo("Successfully typed " + text + " in element with css " + css);

        } catch (NoSuchElementException ex) {
            logger.addWarn("Something went wrong during typing  " + text + " in element with css " + css);
        }
    }

    public void waitForElementAppeared(String css, int tries) {
        logger.addStep("Waiting for element with css " + css + " appeared for " + tries + " seconds");
        for (int i = 0; i < tries; i++) {
            List<WebElement> elements = this.findElementsByCss(css);
            if (elements.isEmpty()) {
                logger.addWarn("Element not found on " + i + " iteration");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                logger.addInfo("Element with css " + css + " appeared at try " + i);
                break;
            }
        }
    }

    public void waitForTextChanged(String current, String css) {
        logger.addStep("Waiting for text in " + css + " will change");
        for (int i = 0; i < 10; i++) {
            WebElement el = this.findElementByCss(css);

            if (el.getText().equals(current)) {
                logger.addInfo("Text changed on" + i + " iteration");
                break;
            } else {
                try {
                    logger.addWarn("Text not changed " + i + " iteration");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void waitForElementDisappeared(String css, int tries) {
        logger.addStep("Waiting for element with css " + css + " disappeared for " + tries + " seconds");
        for (int i = 0; i < tries; i++) {
            List<WebElement> elements = this.findElementsByCss(css);
            if (!elements.isEmpty()) {
                logger.addWarn("Element found on " + i + " iteration");

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                logger.addInfo("Element with css " + css + " disappeared at try " + i);
                break;
            }
        }
    }

    public void setCookie(String name, String value) {
        logger.addStep("Setting cookie " + name + " with value " + value);
        driver.executeScript("document.cookie='" + name + "=" + value + ";domain=.semrush.com;path=/;';");
    }

    public void executeScript(String script) {
        logger.addStep("Executing script " + script);
        driver.executeScript(script);
    }

    public void open(String url) {
        logger.addInfo("Opening url [" + url + "]");
        driver.get(url);
    }

    public String getTitle() {
        logger.addInfo("Getting title of page " + driver.getCurrentUrl());
        return driver.getTitle();
    }

    public String getAttributeByCss(String css, String attr) {
        logger.addStep("Getting attribute " + attr + " of element " + css);
        WebElement el = this.findElementByCss(css);
        String text = null;

        try {
            text = el.getAttribute(attr);
        } catch (Exception ex) {
            logger.addWarn("Got some problems during getting attribute from " + css + " element");
        }

        return text;
    }

    public String getTextByCss(String css) {
        logger.addStep("Getting text of " + css);
        WebElement el = this.findElementByCss(css);
        String text = null;

        try {
            text = el.getText();
        } catch (Exception ex) {
            logger.addWarn("Got some problems during getting text from " + css + " element");
        }

        return text;
    }

    public void close() {
        logger.addInfo("Closing driver");
        this.driver.close();
    }

    public void quit() {
        logger.addInfo("Quiting driver");
        this.driver.quit();
    }

    public void addStep(String notice) {
        logger.addInfo(notice);
    }

}
