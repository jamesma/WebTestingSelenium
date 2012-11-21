package com.jamesma.webtesting;

import static com.jamesma.webtesting.Constants.ADMIN_PASSWORD;
import static com.jamesma.webtesting.Constants.ADMIN_USERNAME;
import static com.jamesma.webtesting.Constants.FIREFOX_BINARY_PATH;
import static com.jamesma.webtesting.Constants.FIREFOX_DRIVER_PROPERTY;
import static com.jamesma.webtesting.Constants.INPUT_FILE_NAME;
import static com.jamesma.webtesting.Constants.OUTPUT_FILE_NAME;
import static com.jamesma.webtesting.Constants.WEBSITE_ROOT;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.XPathLookupException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebTester {
    
    private String websiteRoot;
    private WebDriver driver;
    
    public WebTester(String websiteRoot) {
        this.websiteRoot = websiteRoot;
        System.setProperty(FIREFOX_DRIVER_PROPERTY, FIREFOX_BINARY_PATH);
        this.driver = new FirefoxDriver();
    }
    
    /**
     * Login as admin with credentials.
     */
    private void performAdminLogin() {
        // navigate to root URL
        driver.get(websiteRoot);
        
        // username text field
        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys(ADMIN_USERNAME);
        
        // password text field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(ADMIN_PASSWORD);
        
        // login button
        WebElement loginButton = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/table/tbody/tr[1]/td[2]/div[@class='messagebox']/form/table[@class='y']/tbody/tr[3]/td[2]/input"));
        loginButton.click();
        
        waitUntilTagAppears("h1", "Manage Classes", 5);
    }
    
    /**
     * From "Manage Classes", to "Add New Class".
     */
    private void navigateToAddClassPanel() {
        WebElement addClassButton = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/input[1]"));
        addClassButton.click();
        
        waitUntilTagAppears("h1", "Add New Class", 5);
    }
    
    /**
     * Delete class record by index of the classes table.
     * 
     * @param index
     */
    private void deleteClassByIndex(int index) {
        navigateToAllSemesters();
        
        List<WebElement> checkboxes = driver.findElements(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody//input"));

        checkboxes.get(index).click();
        
        // click delete button
        WebElement deleteButton = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/input[7]"));
        deleteButton.click();
        
        // hit OK in alert dialog
        Alert alertDialog = driver.switchTo().alert();
        alertDialog.accept();
        
        waitUntilTagAppears("h1", "Manage Classes", 5);
    }
    
    /**
     * Click on Add button
     */
    private void performAddClass() {
        // click on add button
        WebElement addClassButton = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[2]/tbody/tr/td/input[6]"));
        addClassButton.click();
    }
    
    /**
     * Select "All" from semester dropdown
     */
    private void navigateToAllSemesters() {
        String semesterDropdownXpath = "/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/select";
        
        // click on "All" in semester dropdown
        Select semesterDropdown = new Select(driver.findElement(By.xpath(semesterDropdownXpath)));
        semesterDropdown.selectByVisibleText("All");
        
        waitUntilDropdownSelected("All", semesterDropdownXpath, 5);
    }
    
    /**
     * Return AddClassTestResult after checking WebElements and validity of AddClassTestCase.
     * 
     * @param testCase
     * @param beforeCount
     * @param afterCount
     * @return
     */
    private AddClassTestResult generateTestResult(AddClassTestCase testCase, int beforeCount, int afterCount) {
        navigateToAllSemesters();
        
        AddClassTestResult testResult = null;
        
        try {
            RequirementViolationChecker.checkRequirementViolation(driver, testCase, beforeCount, afterCount);
            testResult = new AddClassTestResult(
                    true, 
                    testCase.getClassName(), 
                    testCase.getTeacher(),
                    testCase.getSemester(),
                    testCase.getSectionNumber(), 
                    testCase.getRoomNumber(), 
                    testCase.getPeriodNumber(), 
                    testCase.getDayOfTheWeek(), 
                    testCase.getSubstitute());

        } catch (RequirementViolationException ex) {
            testResult = new AddClassTestResult(
                    false, 
                    testCase.getClassName(), 
                    testCase.getTeacher(),
                    testCase.getSemester(),
                    testCase.getSectionNumber(), 
                    testCase.getRoomNumber(), 
                    testCase.getPeriodNumber(), 
                    testCase.getDayOfTheWeek(), 
                    testCase.getSubstitute(), 
                    ex.getMessage()); // RequirementViolationException's message
        }
        
        return testResult;
    }
    
    /**
     * Print AddClassTestResults to STDOUT.
     * 
     * @param testResults
     */
    @SuppressWarnings("unused")
    private void printTestResults(ArrayList<AddClassTestResult> testResults) {
        System.out.println("Printing AddClassTestResults...");
        for (AddClassTestResult result : testResults)
            System.out.println(result.toString());
    }
    
    /**
     * Fill in text fields, selections, radio buttons with AddClassTestCase's variables and click Add.
     * 
     * @param testCase
     */
    private void fillInTestCaseValues(AddClassTestCase testCase) {
        WebElement className =          driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[@class='dynamiclist']/tbody/tr[@class='even']/td[1]/input"));
        WebElement sectionNumber =      driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[@class='dynamiclist']/tbody/tr[@class='even']/td[4]/input"));
        WebElement roomNumber =         driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[@class='dynamiclist']/tbody/tr[@class='even']/td[5]/input"));
        WebElement periodNumber =       driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[@class='dynamiclist']/tbody/tr[@class='even']/td[6]/input"));
        Select substitute =             new Select(driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[@class='dynamiclist']/tbody/tr[@class='even']/td[7]/select")));
        Select teacher =                new Select(driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[@class='dynamiclist']/tbody/tr[@class='even']/td[2]/select")));
        Select semester =               new Select(driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[@class='dynamiclist']/tbody/tr[@class='even']/td[3]/select")));
        
        className.      sendKeys(testCase.getClassName());
        sectionNumber.  sendKeys(testCase.getSectionNumber());
        roomNumber.     sendKeys(testCase.getRoomNumber());
        periodNumber.   sendKeys(testCase.getPeriodNumber());
        substitute.     selectByVisibleText(testCase.getSubstitute());
        teacher.        selectByVisibleText(testCase.getTeacher());
        semester.       selectByVisibleText(testCase.getSemester());
        
        clickOnDayOfTheWeek(testCase.getDayOfTheWeek());
    }
    
    /**
     * Helper method for selecting radio button for day of week.
     * 
     * @param str
     */
    private void clickOnDayOfTheWeek(String str) {
        WebElement radioButton = null;
        String xpathSelector = null;
        
        if (str.equals("M")) {
            xpathSelector = "/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[2]/tbody/tr/td/input[1]";
        } else if (str.equals("T")) {
            xpathSelector = "/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[2]/tbody/tr/td/input[2]";
        } else if (str.equals("W")) {
            xpathSelector = "/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[2]/tbody/tr/td/input[3]";
        } else if (str.equals("H")) {
            xpathSelector = "/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[2]/tbody/tr/td/input[4]";
        } else if (str.equals("F")) {
            xpathSelector = "/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[2]/tbody/tr/td/input[5]";
        } else {
            throw new XPathLookupException("testCase.getDayOfTheWeek is wrong: " + str);
        }
        
        radioButton = driver.findElement(By.xpath(xpathSelector));
        radioButton.click();
    }
    
    /**
     * Get number of class records in table and return the count.
     * @return
     */
    private int getClassRecordCount() {
        navigateToAllSemesters();
        
        List<WebElement> classnames = driver.findElements(By.xpath("/html/body/table[2]/tbody/tr[2]/td[@class='w']/table/tbody/tr/td/form/table[1]/tbody/tr/td/table[@class='dynamiclist']/tbody/tr//td[2]"));
        return classnames.size();
    }
    
    /**
     * Helper method for doing an explicit wait (seconds) until a tag with text appears.
     * 
     * @param tagName
     * @param headerText
     */
    private void waitUntilTagAppears(final String tagName, final String text, final int seconds) {
        (new WebDriverWait(driver, seconds)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(By.tagName(tagName));
                for (WebElement elem : elements) {
                    if (elem.getText().equals(text))
                        return true;
                }
                return false;
            }
        });
    }
    
    /**
     * Helper method for doing an explicit wait (seconds) until an alert dialog appears.
     */
    @SuppressWarnings("unused")
    private void waitUntilAlertAppears(final int seconds) {
        (new WebDriverWait(driver, seconds)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try {
                    driver.switchTo().alert();
                    return true;
                } catch (NoAlertPresentException ex) {
                    // Ignore this exception as there is no alert present.. yet
                    return null;
                }
            }
        });
    }
    
    /**
     * Helper method for doing an explicit wait (seconds) until an option is selected in a dropdown.
     * 
     * @param optionString the select element text
     * @param xpath - Xpath string for locating the dropdown WebElement
     * @param seconds
     */
    private void waitUntilDropdownSelected(final String optionString, final String xpath, final int seconds) {
        (new WebDriverWait(driver, seconds)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                Select dropdown = new Select(driver.findElement(By.xpath(xpath)));
                WebElement selectedOption = dropdown.getFirstSelectedOption();
                if (selectedOption.getText().equals(optionString))
                    return true;
                return false;
            }
        });
    }

    /**
     * Begin test suite for Selenium.
     */
    private void beginTest() {
        // initialize testCases and testResults
        ArrayList<AddClassTestCase> testCases = InputParser.getAddClassTestCases(INPUT_FILE_NAME);
        ArrayList<AddClassTestResult> testResults = new ArrayList<AddClassTestResult>();
        
        // login as admin
        performAdminLogin();
        
        // iterate over each test case
        for (AddClassTestCase testCase : testCases) {
            // get number of classes BEFORE add
            int beforeCount = getClassRecordCount();
            
            // navigate to "Add Class"
            navigateToAddClassPanel();
            
            // fill in input values defined in test case
            fillInTestCaseValues(testCase);
            
            // add class
            performAddClass();
            
            // get number of classes AFTER add
            int afterCount = getClassRecordCount();
            
            // generate test result and add to ArrayList of test results
            testResults.add(generateTestResult(testCase, beforeCount, afterCount));
            
            // delete class for this test case if it was added successfully
            if (RequirementViolationChecker.isClassRecordAddedSuccessfully(beforeCount, afterCount))
                deleteClassByIndex(afterCount-1);
        }
        
        // write test results to file
        TestResultWriter.writeTestResultsToFile(OUTPUT_FILE_NAME, testResults);
        
        // quit WebDriver
        driver.quit();
    }
    
    public static void main(String[] args) {
        WebTester webTester = new WebTester(WEBSITE_ROOT);
        webTester.beginTest();
    }

}
