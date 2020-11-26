package packages.tester;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class Tester extends Thread {
    private final String name;
    private final int index;

    public Tester(String name, int index) {
        System.setProperty("webdriver.chrome.driver", "/home/lanta/IdeaProjects/chromedriver");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        this.name = name;
        this.index = index;
    }

    public static void main(String[] args) {
        int threadCount = 1;
//        if(args[0] != null ) {
//            try {
//                threadCount = Integer.parseInt(args[0]);
//            }catch(Exception e) {
//                System.out.println(e.toString());
//            }
//        }
        for (int i = 1; i <= threadCount; i++) {
            Thread t = new Tester("Thread " + i, i);
            t.start();
        }

    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            test();
        } catch (InterruptedException e) {
            System.out.println("sleep interrupted");
        }
    }

    private void test() throws InterruptedException {
        WebDriver driver = null;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("enable-automation");
//        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-extensions");
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--disable-gpu");
        options.addArguments("disable-features=NetworkService");
        try {
            driver = new ChromeDriver(options);
        } catch (org.openqa.selenium.WebDriverException e) {
            System.out.println("==== " + this.name + " Failed to startup ChromeDriver" + " ====");
        }

        if (driver != null) {
            try {
                driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

                driver.get("https://www.templater.us/");

                if (this.index % 5 == 1) {   //The 1st User
                    loginScreen(driver, "giottester@gmail.com", "rN3EPwcw4Xm956s");
                } else if (this.index % 5 == 2) {   //The 2nd User
                    loginScreen(driver, "gkmagellan@gmail.com", "rN3EPwcw4Xm956s");
                }

                //Select Service Provider
                Thread.sleep(5000);
                selectByIndexAndSubmitByClass(driver, "npi", 1, "actionBtn");

                //Select a claim template
                Thread.sleep(1000);
                selectByValueAndSubmit(driver, "selTemplate", "SCREEN_DA_CLAIM", "btnContinue");

                //Fill in a claim and submit
                Thread.sleep(1000);


            } catch (java.lang.RuntimeException re) {
                System.out.println("==== " + this.name + " Got Error in WCS Test " + "====");
            } finally {
                System.out.println("**** " + this.name + " Finised WCS Test " + "****");
                driver.close();
                driver.quit();
            }
        }
    }

    private void loginScreen(WebDriver driver, String usernameStr, String passwordStr) throws InterruptedException {
        //Enter username
        Thread.sleep(1000);
        while (true) {
            try {
                WebElement username = driver.findElement(By.name("username"));
                username.sendKeys(usernameStr);
                driver.findElement(By.id("idp-discovery-submit")).click();
                break;
            } catch (Exception e) {
                Thread.sleep(1000);
            }
        }

        //Enter password
        Thread.sleep(1000);
        while (true) {
            try {
                WebElement password = driver.findElement(By.name("password"));
                password.sendKeys(passwordStr);
                driver.findElement(By.id("okta-signin-submit")).click();
                break;
            } catch (Exception e) {
                Thread.sleep(1000);
            }
        }
    }

    private void enterText(WebDriver driver, String textName, String textValue) throws InterruptedException {
        try {
            WebElement element = driver.findElement(By.name(textName));
            element.sendKeys(textValue);
        } catch (Exception e) {
            Thread.sleep(1000);
        }

    }

    private void selectByIndexAndSubmitByClass(WebDriver driver, String selectId, int index, String className) throws InterruptedException {
        while (true) {
            try {
                Select element = new Select(driver.findElement(By.id(selectId)));
                element.selectByIndex(index);
                driver.findElement(By.className(className)).click();
                break;
            } catch (Exception e) {
                Thread.sleep(1000);
            }
        }
    }

    private void selectByIndex(WebDriver driver, String selectId, int index) throws InterruptedException {
        while (true) {
            try {
                Select element = new Select(driver.findElement(By.id(selectId)));
                element.selectByIndex(index);
                break;
            } catch (Exception e) {
                Thread.sleep(1000);
            }
        }
    }

    private void selectByValue(WebDriver driver, String selectId, String selectValue) throws InterruptedException {
        while (true) {
            try {
                Select element = new Select(driver.findElement(By.id(selectId)));
                element.selectByValue(selectValue);
                break;
            } catch (Exception e) {
                Thread.sleep(1000);
            }
        }
    }

    private void selectByValueAndSubmit(WebDriver driver, String selectId, String selectValue, String buttonId) throws InterruptedException {
        while (true) {
            try {
                Select element = new Select(driver.findElement(By.id(selectId)));
                element.selectByValue(selectValue);
                driver.findElement(By.id(buttonId)).click();
                break;
            } catch (Exception e) {
                Thread.sleep(1000);
            }
        }
    }
}
