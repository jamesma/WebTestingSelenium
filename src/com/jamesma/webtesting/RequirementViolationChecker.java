package com.jamesma.webtesting;

import static com.jamesma.webtesting.Constants.CLASSNAME;
import static com.jamesma.webtesting.Constants.CLASS_RECORD_NOT_ADDED_SUCCESSFULLY_ERROR;
import static com.jamesma.webtesting.Constants.CLASS_RECORD_NOT_SAME_AS_INPUT_ERROR;
import static com.jamesma.webtesting.Constants.DAY_OF_THE_WEEK;
import static com.jamesma.webtesting.Constants.ONLY_NUMBERS_AND_ALPHABETS_ERROR;
import static com.jamesma.webtesting.Constants.ONLY_NUMBERS_ERROR;
import static com.jamesma.webtesting.Constants.PERIOD_NUMBER;
import static com.jamesma.webtesting.Constants.R1;
import static com.jamesma.webtesting.Constants.R2;
import static com.jamesma.webtesting.Constants.R3;
import static com.jamesma.webtesting.Constants.R4;
import static com.jamesma.webtesting.Constants.R5;
import static com.jamesma.webtesting.Constants.R6;
import static com.jamesma.webtesting.Constants.R7;
import static com.jamesma.webtesting.Constants.ROOM_NUMBER;
import static com.jamesma.webtesting.Constants.SECTION_NUMBER;
import static com.jamesma.webtesting.Constants.SEMESTER;
import static com.jamesma.webtesting.Constants.SUBSTITUTE;
import static com.jamesma.webtesting.Constants.TEACHER;
import static com.jamesma.webtesting.Constants.XSS_VULNERABILITY_EXISTS_ERROR;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RequirementViolationChecker {
    
    /**
     * Check if testCase has a requirement violation.
     * 
     * @code
     * if input is valid
     *     check class record is added successfully
     *     check class record is exactly same as input
     * else if class record added successfully
     *     check R-1 to R-5 for requirement violations
     * 
     * @param driver
     * @param testCase
     * @param beforeCount
     * @param afterCount
     * @throws RequirementViolationException if violation detected
     */
    public static void checkRequirementViolation(WebDriver driver, AddClassTestCase testCase, int beforeCount, int afterCount) throws RequirementViolationException {
        
        if (isInputValid(testCase)) {
            // R6 
            ClassRecord classRecord = ensureClassRecordAddedSuccessfully(driver, R6, beforeCount, afterCount);
            // R7
            ensureClassRecordSameAsInput(classRecord, R7, testCase);
        } else {
            if (isClassRecordAddedSuccessfully(beforeCount, afterCount)) {
                // R5
                ensureNoXssVulnerability(driver, R5);
                // R1
                ensureOnlyNumbersAndAlphabets(R1, testCase.getClassName());
                // R2
                ensureOnlyNumbers(R2, testCase.getSectionNumber());
                // R3
                ensureOnlyNumbers(R3, testCase.getRoomNumber());
                // R4
                ensureOnlyNumbers(R4, testCase.getPeriodNumber());
            }
        }
        
        // no requirement violation if code reaches here
    }
    
    /**
     * Check if Input (i.e. AddClassTestCase's values) is valid.
     * 
     * @param testCase
     * @return true if valid, false if not
     */
    public static boolean isInputValid(AddClassTestCase testCase) {
        try {
            // R1
            ensureOnlyNumbersAndAlphabets(R1, testCase.getClassName());
            // R2
            ensureOnlyNumbers(R2, testCase.getSectionNumber());
            // R3
            ensureOnlyNumbers(R3, testCase.getRoomNumber());
            // R4
            ensureOnlyNumbers(R4, testCase.getPeriodNumber());
        } catch (RequirementViolationException ex) {
            return false;
        }
        return true;
    }
    
    /**
     * Checks if String value is only numbers.
     * 
     * @param req
     * @param str
     * @throws RequirementViolationException if invalid
     */
    public static void ensureOnlyNumbers(String req, String str) throws RequirementViolationException {
        String numbersRegex = "[0-9&&[^\\s]]+";
        Pattern pattern = Pattern.compile(numbersRegex);
        Matcher matcher = pattern.matcher(str);
        
        if (!matcher.matches() || str.isEmpty())
            throw new RequirementViolationException(req + " - " + ONLY_NUMBERS_ERROR);
    }
    
    /**
     * Checks if String value is only numbers and alphabets.
     * 
     * @param req
     * @param str
     * @throws RequirementViolationException if invalid
     */
    public static void ensureOnlyNumbersAndAlphabets(String req, String str) throws RequirementViolationException {
        String numbersAndAlphabetsRegex = "[a-zA-Z0-9&&[^\\s]]+";
        Pattern pattern = Pattern.compile(numbersAndAlphabetsRegex);
        Matcher matcher = pattern.matcher(str);
        
        if(!matcher.matches() || str.isEmpty())
            throw new RequirementViolationException(req + " - " + ONLY_NUMBERS_AND_ALPHABETS_ERROR);
    }

    /**
     * Navigate to "Classes" and check if XSS vulnerability exists
     * by searching for script tags within all text fields.
     * 
     * @param driver
     * @param req
     * @throws RequirementViolationException if vulnerability exists
     */
    public static void ensureNoXssVulnerability(WebDriver driver, String req) throws RequirementViolationException {
        List<WebElement> textFields = driver.findElements(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr"));
        
        for (WebElement textField : textFields) {
            List<WebElement> tags = textField.findElements(By.tagName("script"));
            
            if (!tags.isEmpty())
                throw new RequirementViolationException(req + " - " + XSS_VULNERABILITY_EXISTS_ERROR);
        }
    }
    
    /**
     * Check if class is added successfully by comparing before and after counts.
     * 
     * @param beforeCount
     * @param afterCount
     * @return true if successful, false if not
     */
    public static boolean isClassRecordAddedSuccessfully(int beforeCount, int afterCount) {
        return beforeCount+1 == afterCount;
    }
    
    /**
     * Check if class record is added successfully, checking for requirement violation.
     * Return most recently added class record if no violation.
     * 
     * @param driver
     * @param req
     * @param beforeCount
     * @param afterCount
     * @return ClassRecord object if no requirement violation.
     * @throws RequirementViolationException
     */
    public static ClassRecord ensureClassRecordAddedSuccessfully(WebDriver driver, String req, int beforeCount, int afterCount) throws RequirementViolationException {
        if (!isClassRecordAddedSuccessfully(beforeCount, afterCount))
            throw new RequirementViolationException(req + " - " + CLASS_RECORD_NOT_ADDED_SUCCESSFULLY_ERROR);
        
        // return last record in table
        return getClassRecordByIndex(driver, afterCount-1);
    }
    
    /**
     * Get ClassRecord object from table by index.
     * 
     * @param driver
     * @param index
     * @return
     */
    public static ClassRecord getClassRecordByIndex(WebDriver driver, int index) {

        List<WebElement> classnames     = null;
        List<WebElement> teachers       = null;
        List<WebElement> semesters      = null;
        List<WebElement> sectionNumbers = null;
        List<WebElement> roomNumbers    = null;
        List<WebElement> periodNumbers  = null;
        List<WebElement> dayOfTheWeeks  = null;
        List<WebElement> substitutes    = null;

        classnames =     driver.findElements(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr//td[2]"));
        teachers =       driver.findElements(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr//td[3]"));
        semesters =      driver.findElements(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr//td[4]"));
        sectionNumbers = driver.findElements(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr//td[5]"));
        roomNumbers =    driver.findElements(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr//td[6]"));
        periodNumbers =  driver.findElements(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr//td[7]"));
        dayOfTheWeeks =  driver.findElements(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr//td[8]"));
        substitutes =    driver.findElements(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr//td[9]"));

        return new ClassRecord(
                classnames      .get(index).getText(), 
                teachers        .get(index).getText(), 
                semesters       .get(index).getText(), 
                sectionNumbers  .get(index).getText(), 
                roomNumbers     .get(index).getText(), 
                periodNumbers   .get(index).getText(), 
                substitutes     .get(index).getText(), 
                dayOfTheWeeks   .get(index).getText());
    }
    
    /**
     * Check if class record is same as input values from AddClassTestCase object.
     * 
     * @param classRecord
     * @param req
     * @param testCase
     * @throws RequirementViolationException if class record is not the same as input
     */
    public static void ensureClassRecordSameAsInput(ClassRecord classRecord, String req, AddClassTestCase testCase) throws RequirementViolationException {
        if (classRecord == null)
            return;
        
        ArrayList<String> mismatches = new ArrayList<String>();
        
        if (!classRecord.getClassName().equals(testCase.getClassName()))
            mismatches.add(CLASSNAME);
        if (!classRecord.getTeacher().equals(testCase.getTeacher()))
            mismatches.add(TEACHER);
        if (!classRecord.getSemester().equals(testCase.getSemester()))
            mismatches.add(SEMESTER);
        if (!classRecord.getSectionNumber().equals(testCase.getSectionNumber()))
            mismatches.add(SECTION_NUMBER);
        if (!classRecord.getRoomNumber().equals(testCase.getRoomNumber()))
            mismatches.add(ROOM_NUMBER);
        if (!classRecord.getPeriodNumber().equals(testCase.getPeriodNumber()))
            mismatches.add(PERIOD_NUMBER);
        if (!classRecord.getDayOfTheWeek().equals(testCase.getDayOfTheWeek()))
            mismatches.add(DAY_OF_THE_WEEK);
        if (!classRecord.getSubstitute().equals(testCase.getSubstitute()))
            mismatches.add(SUBSTITUTE);
        
        // build exception message with mismatches
        if (!mismatches.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(CLASS_RECORD_NOT_SAME_AS_INPUT_ERROR + " <<");
            for (String mismatch : mismatches)
                sb.append(" " + mismatch);
            throw new RequirementViolationException(req + " - " + sb.toString());
        }
        
        // no requirement violation if code reaches here
    }

}
