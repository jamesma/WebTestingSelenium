package com.jamesma.webtesting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {
    
    /**
     * Read contents of file and return ArrayList of AddClassTestCases.
     * 
     * @param fileName
     * @return
     */
    public static ArrayList<AddClassTestCase> getAddClassTestCases(String fileName) {
        ArrayList<AddClassTestCase> testCases = new ArrayList<AddClassTestCase>();
        BufferedReader reader = null;
        try {
            try {
                reader = new BufferedReader(new FileReader(fileName));
                
                String line = null;
                while ((line = reader.readLine()) != null) {
                    AddClassTestCase testCase = getTestCaseMatch(line);
                    if (testCase != null) {
                        testCases.add(testCase);
                    }
                }
            } finally {
                reader.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return testCases;
    }
    
    /**
     * Use Java regex to extract test case values.
     * 
     * @param str
     * @return
     */
    private static AddClassTestCase getTestCaseMatch(String str) {
        //["cn1", "t1", "s1", "11", "1234", "1", "name1", "M"]
        String testCaseRegex = 
                "\\[" + 
                "\"(\\p{ASCII}*)\",\\s*" + 
                "\"(\\p{ASCII}*)\",\\s*" +
                "\"(\\p{ASCII}*)\",\\s*" + 
                "\"(\\p{ASCII}*)\",\\s*" +
                "\"(\\p{ASCII}*)\",\\s*" + 
                "\"(\\p{ASCII}*)\",\\s*" +
                "\"(\\p{ASCII}*)\",\\s*" + 
                "\"(\\p{ASCII}*)\"\\s*" +
                "\\]";

        Pattern pattern = Pattern.compile(testCaseRegex);
        Matcher matcher = pattern.matcher(str);
        
        if (matcher.matches())
            return new AddClassTestCase(
                    matcher.group(1),       // class name
                    matcher.group(2),       // teacher
                    matcher.group(3),       // semester
                    matcher.group(4),       // section number
                    matcher.group(5),       // room number
                    matcher.group(6),       // period number
                    matcher.group(7),       // substitute
                    matcher.group(8));      // day of the week
        
        return null;
    }

}
