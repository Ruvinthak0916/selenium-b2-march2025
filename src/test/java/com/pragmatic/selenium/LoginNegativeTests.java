package com.pragmatic.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginNegativeTests {

    WebDriver driver;
    WebElement txtUsername;
    WebElement txtPassword;
    WebElement btnLogin;

    @BeforeTest
    public void setUp(){

        //open a browser
        driver = new ChromeDriver();

        //navigate to the url
        driver.get("https://www.saucedemo.com/");

        //locate username field, password field and submit button
        txtUsername = driver.findElement(By.id("user-name"));
        txtPassword = driver.findElement(By.id("password"));
        btnLogin = driver.findElement(By.id("login-button"));

    }

    @AfterTest
    public void tearDown(){

        //close the browser
        driver.quit();

    }

    private void login(String username, String password){

        txtUsername.clear();
        txtPassword.clear();
        txtUsername.sendKeys(username);
        txtPassword.sendKeys(password);
        btnLogin.click();

    }

    @Test(priority = 1)
    public void test_correctUsername_correctPassword(){

        login("standard_user", "secret_sauce");

        //verify correctly login to the dashboard(check product label is available)
        Assert.assertEquals(driver.findElement(By.cssSelector(".title")).getText(),"Products");

    }

    @Test(priority = 2)
    public void test_correctUsername_invalidPassword(){

        login("standard_user", "secret_sauce_invalid");

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test(priority = 3)
    public void test_invalidUsername_correctPassword(){

        login("standard_user_invalid", "secret_sauce");

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test(priority = 4)
    public void test_invalidUsername_invalidPassword(){

        login("standard_user_invalid", "secret_sauce_invalid");

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test(priority = 5)
    public void test_blankUsername_correctPassword(){

        login("", "secret_sauce");

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username is required");
    }

    @Test(priority = 6)
    public void test_correctUsername_blankPassword(){

        login("standard_user", "");

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Password is required");
    }

    @Test(priority = 7)
    public void test_blankUsername_blankPassword(){

        login("", "");

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username is required");
    }

    @Test(priority = 8)
    public void test_veryLongUsername_correctPassword(){

        login("This is the very long user name for testing purpose", "secret_sauce");

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test(priority = 9)
    public void test_correctUsername_veryLongPassword(){

        login("standard_user", "This is the very long user name for testing purpose");

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test(priority = 10)
    public void test_specialCharacters_usernamePassword(){

        login("standard_user~`!@#$%^&*()_+", "' OR '1'='1'; --  ");

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
    }

}
