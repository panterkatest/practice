package com.otus.practice.tests;

import com.otus.practice.pages.*;
import com.otus.practice.tests.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class FirstTest extends BaseTest {


    @Test
    public void FirstTest() {

        MainPage mainPage = new MainPage(driver);
        mainPage.openSite();

        String mail = System.getProperty("mail");
        String password = System.getProperty("pass");


        AboutMyself myself = new MenuComponent(driver)
                .getLoginPage()
                .authorize(mail, password)
                .goToProfile();

        Assert.assertEquals(myself.getName(), "Владимир");
        Assert.assertEquals(myself.getSurname(), "Владимиров");
    }

}
