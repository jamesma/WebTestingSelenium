package com.jamesma.webtesting;

import static com.jamesma.webtesting.Constants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
     * @throws RequirementViolationException if violation detected
     */
    public static void checkRequirementViolation(WebDriver driver, AddClassTestCase testCase) throws RequirementViolationException {
        
        if (isInputValid(testCase)) {
            // R6 
            ClassRecord classRecord = ensureClassRecordAddedSuccessfully(driver, R6);
            // R7
            ensureClassRecordSameAsInput(classRecord, R7, testCase);
        } else {
            if (isClassRecordAddedSuccessfully(driver) != null) {
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
     * by searching for script tags within text fields.
     * 
     * @param driver
     * @param req
     * @throws RequirementViolationException if vulnerability exists
     */
    public static void ensureNoXssVulnerability(WebDriver driver, String req) throws RequirementViolationException {
        List<WebElement> textFields = driver.findElements(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr[@class='odd']"));
        
        for (WebElement textField : textFields) {
            List<WebElement> tags = textField.findElements(By.tagName("script"));
            
            if (!tags.isEmpty())
                throw new RequirementViolationException(req + " - " + XSS_VULNERABILITY_EXISTS_ERROR);
        }
    }
    
    /**
     * Check if class record is added successfully under "Manage Classes".
     * 
     * @param driver
     * @return ClassRecord object if added successfully, null if not
     */
    public static ClassRecord isClassRecordAddedSuccessfully(WebDriver driver) {
        WebElement classname = null;
        WebElement teacher = null;
        WebElement semester = null;
        WebElement sectionNumber = null;
        WebElement roomNumber = null;
        WebElement periodNumber = null;
        WebElement dayOfTheWeek = null;
        WebElement substitute = null;
        try {
            classname =     driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr[@class='odd']/td[2]"));
            teacher =       driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr[@class='odd']/td[3]"));
            semester =      driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr[@class='odd']/td[4]"));
            sectionNumber = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr[@class='odd']/td[5]"));
            roomNumber =    driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr[@class='odd']/td[6]"));
            periodNumber =  driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr[@class='odd']/td[7]"));
            dayOfTheWeek =  driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr[@class='odd']/td[8]"));
            substitute =    driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr[@class='odd']/td[9]"));
        } catch (NoSuchElementException ex) {
            return null;
        }
        
        return new ClassRecord(
                classname.getText(), 
                teacher.getText(), 
                semester.getText(), 
                sectionNumber.getText(), 
                roomNumber.getText(), 
                periodNumber.getText(), 
                substitute.getText(), 
                dayOfTheWeek.getText());
    }
    
    /**
     * Wrapper method around isClassRecordAddedSuccessfully.
     * Check if class record is added successfully, checking for requirement violation.
     * 
     * @param driver
     * @param req
     * @return ClassRecord object if no requirement violation.
     * @throws RequirementViolationException
     */
    public static ClassRecord ensureClassRecordAddedSuccessfully(WebDriver driver, String req) throws RequirementViolationException {
        ClassRecord classRecord = isClassRecordAddedSuccessfully(driver);
        if (classRecord == null) {
            throw new RequirementViolationException(req + " - " + CLASS_RECORD_NOT_ADDED_SUCCESSFULLY_ERROR);
        }
        return classRecord;
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
