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
    public void beforeTest(){

        //open a browser
        driver = new ChromeDriver();

        //navigate to the url
        driver.get("https://www.saucedemo.com/");

        //locate username field, password field and submit button
        txtUsername = driver.findElement(By.id("user-name"));
        txtPassword = driver.findElement(By.id("password"));
        btnLogin = driver.findElement(By.id("login-button"));

    }

    @Test
    public void test_correctUsername_correctPassword(){

        txtUsername.sendKeys("standard_user");
        txtPassword.sendKeys("secret_sauce");
        btnLogin.click();

        //verify correctly login to the dashboard(check product label is available)
        Assert.assertEquals(driver.findElement(By.cssSelector(".title")).getText(),"Products");

    }

    @Test
    public void test_correctUsername_invalidPassword(){

        txtUsername.sendKeys("standard_user");
        txtPassword.sendKeys("secret_sauce_invalid");
        btnLogin.click();

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void test_invalidUsername_correctPassword(){

        txtUsername.sendKeys("standard_user_invalid");
        txtPassword.sendKeys("secret_sauce");
        btnLogin.click();

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void test_invalidUsername_invalidPassword(){

        txtUsername.sendKeys("standard_user_invalid");
        txtPassword.sendKeys("secret_sauce_invalid");
        btnLogin.click();

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void test_blankUsername_correctPassword(){

        txtUsername.sendKeys("");
        txtPassword.sendKeys("secret_sauce");
        btnLogin.click();

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username is required");
    }

    @Test
    public void test_correctUsername_blankPassword(){

        txtUsername.sendKeys("standard_user");
        txtPassword.sendKeys("");
        btnLogin.click();

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Password is required");
    }

    @Test
    public void test_blankUsername_blankPassword(){

        txtUsername.sendKeys("");
        txtPassword.sendKeys("");
        btnLogin.click();

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username is required");
    }

    @Test
    public void test_veryLongUsername_correctPassword(){

        txtUsername.sendKeys("This is the very long user name for testing purpose");
        txtPassword.sendKeys("secret_sauce");
        btnLogin.click();

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void test_correctUsername_veryLongPassword(){

        txtUsername.sendKeys("standard_user");
        txtPassword.sendKeys("This is the very long user name for testing purpose");
        btnLogin.click();

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void test_specialCharacters_usernamePassword(){

        txtUsername.sendKeys("standard_user~`!@#$%^&*()_+");
        txtPassword.sendKeys("' OR '1'='1'; --  ");
        btnLogin.click();

        //verify error message
        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
    }



    @AfterTest
    public void afterTest(){

        //close the browser
        driver.quit();

    }





}
