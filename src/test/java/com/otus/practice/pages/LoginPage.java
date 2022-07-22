package com.otus.practice.pages;

import com.otus.practice.helpers.JavaExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(xpath = "//input[@placeholder='Электронная почта' and @type='text']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@placeholder='Введите пароль' and @type='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit' and contains(text(), 'Войти')]")
    private WebElement logIn;

    private WebDriver driver;


    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public MenuComponent authorize(String mail, String password) {
        Actions actions = new Actions(driver);
        JavaExecutor.highlightElement(driver, emailField);
        emailField.sendKeys(mail);
        JavaExecutor.highlightElement(driver, passwordField);
        passwordField.sendKeys(password);
        JavaExecutor.highlightElement(driver, logIn);
        actions.click(logIn).release().build().perform();
        return new MenuComponent(driver);
    }
}
