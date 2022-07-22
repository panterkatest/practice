package com.otus.practice.helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;

public class WebDriverFactory {
    static WebDriver webDriver = null;

    public static WebDriver createDriver(String browser){

    if (BrowserType.CHROME.equalsIgnoreCase(browser)){
            webDriver = WebDriverManager.chromedriver().create();
        } else if (BrowserType.FIREFOX.equalsIgnoreCase(browser)) {
            webDriver = WebDriverManager.firefoxdriver().create();
        } else if (BrowserType.OPERA_BLINK.equalsIgnoreCase(browser)) {
            webDriver = WebDriverManager.operadriver().create();
        }else {
            throw new RuntimeException("Unsupported browser: " + browser);
        }
        return webDriver;
    }
}
