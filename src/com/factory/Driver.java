package com.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class Driver {
    static WebDriver driver;

    public static WebDriver getDriver(String browserName, boolean isHeadless ) {

        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "/home/lanta/IdeaProjects/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(isHeadless);
            driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "/home/lanta/IdeaProjects/geckodriver");
            FirefoxOptions options = new FirefoxOptions();
            options.setHeadless(isHeadless);
            driver = new FirefoxDriver(options);
        }
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return driver;
    }
}
