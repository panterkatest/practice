package com.otus.practice.tests;

import com.otus.practice.helpers.WebDriverFactory;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;


public class BaseTest {

   protected WebDriver driver;

   String browser = System.getProperty("browser");



    @Before
    public void startUp(){
        driver = WebDriverFactory.createDriver(browser);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @After
    public void cleanUp(){
        driver.quit();
    }

}
