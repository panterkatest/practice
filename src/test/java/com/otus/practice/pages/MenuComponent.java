package com.otus.practice.pages;

import com.otus.practice.helpers.JavaExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MenuComponent {

    private WebDriver webDriver;

    public MenuComponent(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(xpath = "/html/body/div[1]/div/header[2]/div/div[1]/a")
    private WebElement mainPageEl;
    @FindBy(xpath = "//button[contains(text(), 'Вход')]")
    private WebElement auth;
    @FindBy(xpath = "//div[@class='header2-menu__icon-img ic-blog-default-avatar']")
    private WebElement avatar;
    @FindBy(xpath = "//a[@title='Личный кабинет']")
    private WebElement lk;
    @FindBy(xpath = "//a[@title='О себе']")
    private WebElement personal;

    public LoginPage getLoginPage(){
        JavaExecutor.highlightElement(webDriver, auth);
        auth.click();
        return new LoginPage(webDriver);
    }

    public AboutMyself goToProfile(){
        JavaExecutor.highlightElement(webDriver, avatar);
        avatar.click();
        JavaExecutor.highlightElement(webDriver, lk);
        lk.click();
        JavaExecutor.highlightElement(webDriver, personal);
        personal.click();
        JavaExecutor.highlightElement(webDriver, mainPageEl);
        mainPageEl.click();
        return new AboutMyself(webDriver);
    }
}
