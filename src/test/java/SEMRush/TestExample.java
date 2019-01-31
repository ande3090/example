package SEMRush;

import Framework.BaseGUI;
import Framework.UsersData;
import Pages.FilterPage;
import Pages.IndexPage;
import Pages.ProjectsPage;
import Sections.KWTWizard;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Map;


public class TestExample {

    private BaseGUI driver;
    private FilterPage filterPage;

    @BeforeSuite
    public void setUp() {
        this.driver = new BaseGUI();
    }

    @Test()
    public void testLogin() {
        IndexPage index = new IndexPage(driver);

        ProjectsPage projectsPage = new ProjectsPage(driver);
        Map<String, String> user = new UsersData().getUser();

        index.open()
                .loginSection()
                .clickLoginButton()
                .fillForm(user);

        projectsPage = index.loginSection().clickSubmit();

        Assert.assertEquals(driver.getTitle(), "Dashboard");
        Assert.assertTrue(projectsPage.isAuthorized());
    }

    @Test(dependsOnMethods = "testLogin")
    public void testSelectKeywords() {
        ProjectsPage projectsPage = new ProjectsPage(driver);

        projectsPage.clickProjectsTab().clickNewProject().createNewProject("test", "test.com");
        KWTWizard wizard = projectsPage.clickSetupWidget();

        String testValues[] = {"test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9", "test10"};

        wizard.chooseManual().addKeywords(testValues);
        Assert.assertEquals(wizard.getKeywordsCount(), 10);

        wizard.chooseLocationTab().chooseCountry();

        wizard.setOverlayScreenCookie();
        this.filterPage = wizard.clickSubmit();

        wizard.clickSubmit();
        this.filterPage.closeTour();

    }

    @Test(dependsOnMethods = "testSelectKeywords")
    public void testDefaultFilter() {
        //default
        Assert.assertFalse(filterPage.isFilterClearButtonVisible());
        Assert.assertNull(filterPage.getFilterText());
        Assert.assertEquals(filterPage.getCellsCount(), 10);
        Assert.assertEquals(filterPage.getResultCounter(), 10);
        Assert.assertTrue(filterPage.isResultsFound());

    }

    @Test(dependsOnMethods = "testDefaultFilter")
    public void testCustomFilter() {
        //something found
        filterPage.setFilter("test1");

        Assert.assertTrue(filterPage.isFilterClearButtonVisible());
        Assert.assertEquals(filterPage.getFilterText(), "test1");
        Assert.assertEquals(filterPage.getCellsCount(), 2);
        Assert.assertEquals(filterPage.getResultCounter(), 2);
        Assert.assertTrue(filterPage.isResultsFound());
    }

    @Test(dependsOnMethods = "testCustomFilter")
    public void testClearFilterButton() {
        //clear button
        filterPage.clickClearButton();

        Assert.assertFalse(filterPage.isFilterClearButtonVisible());
        Assert.assertNull(filterPage.getFilterText());
        Assert.assertEquals(filterPage.getCellsCount(), 10);
        Assert.assertEquals(filterPage.getResultCounter(), 10);
        Assert.assertTrue(filterPage.isResultsFound());
    }

    @Test(dependsOnMethods = "testClearFilterButton")
    public void testIncorrectValue() {
        //nothing found
        filterPage.setFilter("testtesttesttesttest");

        Assert.assertTrue(filterPage.isFilterClearButtonVisible());
        Assert.assertEquals(filterPage.getFilterText(), "testtesttesttesttest");
        Assert.assertEquals(filterPage.getCellsCount(), 0);
        Assert.assertEquals(filterPage.getResultCounter(), 0);
        Assert.assertFalse(filterPage.isResultsFound());
    }


    @AfterSuite
    public void tearDown() {
        driver.close();
        driver.quit();
    }


}
