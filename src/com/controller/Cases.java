package com.controller;

import com.factory.Driver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

public class Cases {
    private WebDriver driver;

    public Cases(String driver, boolean isHeadless) {
        this.driver = Driver.getDriver(driver, isHeadless);

    }

    public void GoToUrl(String url) {
        driver.navigate().to(url);
    }

    public void SetWindowSize(int x, int y) {
        driver.manage().window().setPosition(new Point(0, 0));
        Dimension d = new Dimension(1920, 1080);
        driver.manage().window().setSize(d);
    }

    public String getWindowTitle() {
        if (driver instanceof JavascriptExecutor) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (String) js.executeScript("return document.title;");
        } else throw new IllegalStateException("This driver does not support JavaScript!");
    }

    public void close() {
        driver.quit();
    }

}
