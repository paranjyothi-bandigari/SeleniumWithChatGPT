package tests;

import Utils.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

import static Utils.TestDataUtil.getTestDataFromJson;

public class RegisterUserWithExcel {

    WebDriver driver;

    @BeforeClass
    public void setUpBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-practice.netlify.app/bugs-form.html");
    }

    @Test(description = "Verify that the Bug Report Form page URL opens successfully")
    public void shouldLoadBugReportFormUrlSuccessfully() {
        String pageTitle = driver.getTitle();
        System.out.println("Page Title: " + pageTitle);
        Assert.assertTrue(pageTitle.toLowerCase().contains("qa"), "Page title does not contain 'QA'");
    }

    @Test(dataProvider = "excelData", description = "Register a user by filling the bug form with different test data from Excel")
    public void RegisterUser(String firstName, String lastName, String phone, String country, String email, String password) {
        Assert.assertEquals(driver.getCurrentUrl(), "https://qa-practice.netlify.app/bugs-form.html", "URLs do not match");

        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys(firstName);

        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys(lastName);

        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys(phone);

        Select countryDropdown = new Select(driver.findElement(By.id("countries_dropdown_menu")));
        countryDropdown.selectByVisibleText(country);

        driver.findElement(By.id("emailAddress")).clear();
        driver.findElement(By.id("emailAddress")).sendKeys(email);

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);

        WebElement checkbox = driver.findElement(By.id("exampleCheck1"));
        if (!checkbox.isEnabled()) {
            ((JavascriptExecutor) driver).executeScript("document.getElementById('exampleCheck1').disabled = false;");
        }
        checkbox.click();

        driver.findElement(By.id("registerBtn")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement resultSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("results-section")));
        Assert.assertTrue(resultSection.isDisplayed(), "Result section was not displayed after submission!");

        System.out.println("Result Section Text:\n" + resultSection.getText());
    }

    @DataProvider(name = "excelData")
    public Object[][] excelDataProvider() {
        return ExcelUtil.getTestData("testdata.xlsx", "Sheet1");
    }

    @AfterClass
    public void tearDownBrowser() {
        if (driver != null) {
            //driver.quit();
        }
    }
}
