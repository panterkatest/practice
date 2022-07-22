package com.otus.practice.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AboutMyself {
    private WebDriver webDriver;

    @FindBy(xpath = "//input[@name='fname']")
    private WebElement name;
    @FindBy(xpath = "//input[@name='lname']")
    private WebElement surname ;


    public AboutMyself(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getName(){
        return name.getAttribute("value");
    }

    public String getSurname(){
        return surname.getAttribute("value");
    }

}
