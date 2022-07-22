package com.otus.practice.pages;

import com.otus.practice.helpers.JavaExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainPage {

    private WebDriver driver;
    private final String SITE = "https://otus.ru";

    @FindBy(xpath = "//a[@href='tel:+74999389202']")
    public WebElement phone;

    @FindBy(xpath = "//div[@class='lessons__new-item-container']/div[contains (@class,'lessons__new-item-title')]")
    public WebElement course;


    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openSite(){
        driver.get(SITE);
    }

    public boolean isMainPageOpened() {
        if (phone.isEnabled()){
            return true;
        }else {
            return false;
        }
    }

    public String convertListOfDatesForCoursesAndReturnNearestDate (ArrayList listOfCoursesByDateFull) throws Exception{
        ArrayList<String> normalized = new ArrayList<>();
        ArrayList<String> listRU = new ArrayList<>(12);
        ArrayList<Date> dateList = new ArrayList<>();
        listRU.add("января");
        listRU.add("февраля");
        listRU.add("марта");
        listRU.add("апреля");
        listRU.add("мая");
        listRU.add("июня");
        listRU.add("июля");
        listRU.add("августа");
        listRU.add("сентября");
        listRU.add("октября");
        listRU.add("ноября");
        listRU.add("декабря");

        normalized.add(0, "Jan");
        normalized.add(1, "Feb");
        normalized.add(2, "Mar");
        normalized.add(3, "Apr");
        normalized.add(4, "May");
        normalized.add(5, "Jun");
        normalized.add(6, "Jul");
        normalized.add(7, "Aug");
        normalized.add(8, "Sep");
        normalized.add(9, "Oct");
        normalized.add(10, "Nov");
        normalized.add(11, "Dec");

        for(int i = 0; i < listOfCoursesByDateFull.size(); i++){
            String fullDate = listOfCoursesByDateFull.get(i).toString();
            for(int a = 0; a < 12; a++){
                String month = listRU.get(a);
                if(fullDate.contains(month)){
                    fullDate = fullDate.replaceAll(" "+month, "/");
                    fullDate = fullDate.concat(normalized.get(a));
                    fullDate = fullDate.replaceAll(" ", "");
                }
                listOfCoursesByDateFull.set(i, fullDate);

            }
            //Convert to Date format
            Date date =  new SimpleDateFormat("dd/MMM").parse(listOfCoursesByDateFull.get(i).toString());
            dateList.add(date);
        }

        Date min = dateList.stream().reduce(dateList.get(0), BinaryOperator.minBy(Comparator.nullsLast(Comparator.naturalOrder())));
//        Date max= dateList.stream().reduce(dateList.get(0), BinaryOperator.maxBy(Comparator.nullsLast(Comparator.naturalOrder())));

        //Get date equivalent from site
        String finalDate = min.toString().substring(4,10);
        String date = finalDate.substring(4);
        String month = finalDate.substring(0,3);
        int indexEn = normalized.indexOf(month);
        month = listRU.get(indexEn);
        finalDate = date.concat(" ").concat(month);

        return finalDate;
    }

    public ArrayList<String> finalListOfCoursesAfterModification (int amountOfPopularCourses, int amountOfCourses, String NeededXpath, boolean blockSpecialization){
        ArrayList<String> listOfCoursesByDate = new ArrayList<>();
        if(blockSpecialization) {
            amountOfCourses = driver.findElements(By.xpath(NeededXpath)).size() - amountOfPopularCourses;
        }else{
            amountOfCourses = driver.findElements(By.xpath(NeededXpath)).size();
        }
        String courseDate;
        for(int i = 0; i < amountOfCourses; i++){
            if(blockSpecialization){
                courseDate = driver.findElements(By.xpath(NeededXpath)).get(i+amountOfPopularCourses).getText();

            }else{
                courseDate = driver.findElements(By.xpath(NeededXpath)).get(i).getText();
            }

            if(!courseDate.contains("О дате старта будет объявлено позже")){
                courseDate = courseDate.replaceFirst("[С]","");
                courseDate = courseDate.trim();
                if(courseDate.contains("месяц") || courseDate.contains("месяца") || courseDate.contains("месяцев")){
                    courseDate = courseDate.replaceAll("\\d{1,2}\\s" + "(\\bмесяц).*","");
                }
                listOfCoursesByDate.add(courseDate);
            }

        }
        return listOfCoursesByDate;
    }


    public List getCoursesListSortedByName (){
        List<String> listOfCoursesByName = new ArrayList<>();

        int amountOfCourses = driver.findElements(By.xpath("//div[@class='lessons__new-item-container']/div[contains (@class,'lessons__new-item-title')]")).size();
        for(int i = 0; i < amountOfCourses; i++){
            String courseName = driver.findElements(By.xpath("//div[@class='lessons__new-item-container']/div[contains (@class,'lessons__new-item-title')]")).get(i).getText();
            listOfCoursesByName.add(courseName);
        }

        List<String> filteredListOfCourses = listOfCoursesByName.stream().sorted().collect(Collectors.toList());

        return filteredListOfCourses;
    }

    public WebElement getNearestCours () throws Exception {
        ArrayList<String> listOfCoursesByDate = new ArrayList<>();
        ArrayList<String> listOfCoursesByDateSpecialization = new ArrayList<>();
        ArrayList<String> listOfCoursesByDateFull = new ArrayList<>();

        //Get a list of popular courses
        int amountOfCourses = driver.findElements(By.xpath("//div[@class='lessons__new-item-container']//div[@class='lessons__new-item-start']")).size();
        listOfCoursesByDate = finalListOfCoursesAfterModification(0,amountOfCourses,"//div[@class='lessons__new-item-container']//div[@class='lessons__new-item-start']", false);

        listOfCoursesByDate.stream().sorted().collect(Collectors.toList());

        //Get a list of courses from Specialization
        int amountOfCoursesSecondGroup = driver.findElements(By.xpath("//div[@class='lessons__new-item-container']/div[contains (@class,'lessons__new-item-title')]")).size() - amountOfCourses;
        listOfCoursesByDateSpecialization = finalListOfCoursesAfterModification(amountOfCourses, amountOfCoursesSecondGroup,"//div[@class='lessons__new-item-container']//div[@class='lessons__new-item-time']", true);

        //Merge all courses
        listOfCoursesByDateFull = (ArrayList<String>) Stream.concat(listOfCoursesByDate.stream(), listOfCoursesByDateSpecialization.stream()).collect(Collectors.toList());

        String neededDate = convertListOfDatesForCoursesAndReturnNearestDate(listOfCoursesByDateFull);
        WebElement course = driver.findElement(By.xpath("//div[@class='lessons__new-item-container']//*[contains(string(),'" + neededDate + "')]"));

        Actions actions = new Actions(driver);
        JavaExecutor.highlightElement(driver, course);
        actions.click(course).release().build().perform();

        return course;
    }

}
