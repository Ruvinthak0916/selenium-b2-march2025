package com.pragmatic.selenium;

import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutProcessTest {

    WebDriver driver;
    //BrowserManager webBrowser;
    WebElement txtUsername;
    WebElement txtPassword;
    WebElement btnClick;
    WebElement cart;
    WebElement firstName;
    WebElement lastName;
    WebElement postalCode;



    @BeforeTest
    public void setUp(){

        //open a browser
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //navigate to the url
        driver.get("https://www.saucedemo.com/");

        //locate username field, password field and submit button
        txtUsername = driver.findElement(By.id("user-name"));
        txtPassword = driver.findElement(By.id("password"));

    }

//    @AfterTest
//    public void tearDown(){
//
//        //close the browser
//        if(driver != null) {
//            driver.quit();
//        }
//    }

    @Test
    public void testAddToCart(){
        //login to the system

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        login("standard_user", "secret_sauce");

        //Check correctly navigate to the product page
        assertionCheck("//span[text()='Products']", "Products");

        //Add products to the cart
        clickButton("add-to-cart-sauce-labs-backpack");
        clickButton("add-to-cart-sauce-labs-bike-light");

        //Go to the cart
        goToTheCartPage(".shopping_cart_link");

        //Verify that the selected products are listed
        assertionCheck("//div[text()='Sauce Labs Backpack']", "Sauce Labs Backpack");
        assertionCheck("//div[text()='Sauce Labs Bike Light']", "Sauce Labs Bike Light");

        //Click checkout button
        clickButton("checkout");

        //Fill in information
        fillInformation();

        //Click continue button
        clickButton("continue");

        //Verify that the data is correctly populated on the checkout overview page
        assertionCheck("//div[text() = 'Sauce Labs Backpack']", "Sauce Labs Backpack");
        assertionCheck("//div[text()='Sauce Labs Bike Light']", "Sauce Labs Bike Light");
        assertionCheck("//div[text()='Payment Information:']", "Payment Information:");

        //click finish button
        clickButton("finish");

        //Verify the order confirmation message
        assertionCheck("//h2[text()='Thank you for your order!']", "Thank you for your order!");

        //click back to home
        clickButton("back-to-products");

    }

    private void login(String username, String password){

        txtUsername.clear();
        txtPassword.clear();
        txtUsername.sendKeys(username);
        txtPassword.sendKeys(password);
        clickButton("login-button");
    }

    private void assertionCheck(String visibleText, String text){

        Assert.assertEquals(driver.findElement(By.xpath(visibleText)).getText(),text);
    }

    private void clickButton(String location){

        btnClick = driver.findElement(By.id(location));
        btnClick.click();
    }

    private void goToTheCartPage(String location){

        cart = driver.findElement(By.cssSelector(location));
        cart.click();
    }

    private void fillInformation(){


        Faker faker = new Faker();
        firstName = driver.findElement(By.id("first-name"));
        firstName.sendKeys(faker.name().firstName());
        lastName = driver.findElement(By.id("last-name"));
        lastName.sendKeys(faker.name().lastName());
        postalCode = driver.findElement(By.id("postal-code"));
        postalCode.sendKeys(faker.address().postcode());

    }
}
