package com.example.blog_tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static com.codeborne.selenide.Selenide.*;

public class BlogTests {


    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.headless=true;
    }

    public static String randomEmail() {
        return "random" + UUID.randomUUID().toString().replace("-","").substring(0,10) + "@example.com";
    }

    public static String randomPass() {
        return UUID.randomUUID().toString().replace("-","").substring(0,10) + "@example.com";
    }


    @Test
    public void register() {
        open("http://13.234.122.79/register");

        $(By.id("name")).sendKeys("Selenium");
        $(By.id("email")).sendKeys(randomEmail());
        String pass = randomPass();
        $(By.id("password")).sendKeys(pass);
        $(By.id("password_confirmation")).sendKeys(pass);

        $(By.xpath("/html/body/div/div/div[2]/form/div[5]/button")).click();

        String actualURL = webdriver().driver().getCurrentFrameUrl();
        assertEquals("http://13.234.122.79/dashboard",actualURL);

        webdriver().driver().clearCookies();

    }

    @Test
    public void registerUserExists() {

        open("http://13.234.122.79/register");

        $(By.id("name")).sendKeys("Selenium");
        $(By.id("email")).sendKeys("mhzxbox360@gmail.com");
        $(By.id("password")).sendKeys("admin123");
        $(By.id("password_confirmation")).sendKeys("admin123");

        $(By.xpath("/html/body/div/div/div[2]/form/div[5]/button")).click();

        String error = $(By.xpath("/html/body/div/div/div[2]/div/div")).getText();

        String errorMsg = $(By.xpath("/html/body/div/div/div[2]/div/ul/li[1]")).getText();

        String actualURL = webdriver().driver().getCurrentFrameUrl();

        assertEquals("Whoops! Something went wrong.",error);
        assertEquals("The email has already been taken.",errorMsg);
        assertEquals("http://13.234.122.79/register",actualURL);

        webdriver().driver().clearCookies();


    }

    @Test
    public void registerPasswordMismatch() {

        open("http://13.234.122.79/register");

        $(By.id("name")).sendKeys("Selenium");
        $(By.id("email")).sendKeys(randomEmail());
        $(By.id("password")).sendKeys("admin123");
        $(By.id("password_confirmation")).sendKeys("admin1234");

        $(By.xpath("/html/body/div/div/div[2]/form/div[5]/button")).click();

        String error = $(By.xpath("/html/body/div/div/div[2]/div/div")).getText();

        String errorMsg = $(By.xpath("/html/body/div/div/div[2]/div/ul/li[1]")).getText();

        String actualURL = webdriver().driver().getCurrentFrameUrl();

        assertEquals("Whoops! Something went wrong.",error);
        assertEquals("The password confirmation does not match.",errorMsg);
        assertEquals("http://13.234.122.79/register",actualURL);

        webdriver().driver().clearCookies();


    }

    @Test
    public void login() {
        open("http://13.234.122.79/login");

        $(By.id("email")).sendKeys("mhzxbox360@gmail.com");
        $(By.id("password")).sendKeys("admin123");

        $(By.xpath("/html/body/div/div/div[2]/form/div[4]/button")).click();

        String actualURL = webdriver().driver().getCurrentFrameUrl();
        assertEquals("http://13.234.122.79/dashboard",actualURL);

        webdriver().driver().clearCookies();

    }

    @Test
    public void loginInvalid() {
        open("http://13.234.122.79/login");

        $(By.id("email")).sendKeys("admin@exam.com");
        $(By.id("password")).sendKeys("pass");

        $(By.xpath("/html/body/div/div/div[2]/form/div[4]/button")).click();

        String error = $(By.xpath("/html/body/div/div/div[2]/div/div")).getText();

        String errorMsg = $(By.xpath("/html/body/div/div/div[2]/div/ul/li[1]")).getText();
        String actualURL = webdriver().driver().getCurrentFrameUrl();

        assertEquals("Whoops! Something went wrong.",error);
        assertEquals("These credentials do not match our records.",errorMsg);
        assertEquals("http://13.234.122.79/login",actualURL);

        webdriver().driver().clearCookies();

    }


    @Test
    public void loginAdmin() {
        open("http://13.234.122.79/login");

        $(By.id("email")).sendKeys("admin@example.com");
        $(By.id("password")).sendKeys("password");

        $(By.xpath("/html/body/div/div/div[2]/form/div[4]/button")).click();

        String actualURL = webdriver().driver().getCurrentFrameUrl();
        assertEquals("http://13.234.122.79/admin",actualURL);

        webdriver().driver().clearCookies();

    }

}
