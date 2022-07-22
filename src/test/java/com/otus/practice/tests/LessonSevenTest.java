package com.otus.practice.tests;

import com.otus.practice.pages.*;
import com.otus.practice.tests.BaseTest;
import org.junit.Test;

public class LessonSevenTest extends BaseTest {

    @Test
    public void SelectCourse() throws Exception {

        MainPage mainPage = new MainPage(driver);
        mainPage.openSite();
        mainPage.isMainPageOpened();
        mainPage.getCoursesListSortedByName();
        mainPage.getNearestCours();
    }
}
