package com.jamesma.webtesting.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NistConvertor {
    
    /**
     * Read contents of file and return ArrayList of AddClassTestCases.
     * 
     * @param fileName
     * @return
     */
    public static ArrayList<NistTestCase> getNistAddClassTestCases(String fileName) {
        ArrayList<NistTestCase> testCases = new ArrayList<NistTestCase>();
        BufferedReader reader = null;
        try {
            try {
                reader = new BufferedReader(new FileReader(fileName));
                String line = null;
                boolean scanningInsideTestCase = false;
                NistParameterValue currParam = null;
                NistTestCase currTestCase = null;
                
                while ((line = reader.readLine()) != null) {
                    currParam = getNistParameterValue(line);
                    
                    if (scanningInsideTestCase) {
                        if (currParam != null) {
                            // valid param - append it
                            currTestCase.appendParam(currParam);
                        } else {
                            // end of test case - set flag and add NistTestCase to ArrayList
                            scanningInsideTestCase = false;
                            currTestCase.transform();
                            testCases.add(currTestCase);
                        }
                    } else {
                        if (currParam != null) {
                            // start of test case - set flag and construct new NistTestCase
                            currTestCase = new NistTestCase();
                            currTestCase.appendParam(currParam);
                            scanningInsideTestCase = true;
                        } else {
                            // do nothing
                        }
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
     * Read Nist file and convert all NistTestCases to input format.
     * 
     * @param nistFileName
     * @param outputFileName
     */
    public static void convertNistToAddClassTestCaseFormat(String nistFileName, String outputFileName) {
        System.out.println("Beginning Nist conversion, nist input file is: " + nistFileName);
        
        ArrayList<NistTestCase> testCases = getNistAddClassTestCases(nistFileName);
        
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFileName));
            try {
                for (NistTestCase testCase : testCases) {
                    writer.write(testCase.toString());
                    writer.newLine();
                    writer.newLine();
                }
            } finally {
                writer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Nist conversion complete, output file is: " + outputFileName + ".");
    }
    
    /**
     * Get NistParameterValue object from line. There are two regexes to match for.
     * 
     * @param line
     * @return
     */
    private static NistParameterValue getNistParameterValue(String line) {
        String nistRegex = "([1-8]) = [a-z]+=(.*)";
        Pattern pattern = Pattern.compile(nistRegex);
        Matcher matcher = pattern.matcher(line);
        
        if (matcher.matches()) {
            return new NistParameterValue(
                    Integer.valueOf(matcher.group(1)), 
                    matcher.group(2));
        }
        
        String dontcareRegex = "([1-8]) = (\\(don't care\\))";
        Pattern dontcarePattern = Pattern.compile(dontcareRegex);
        Matcher dontcareMatcher = dontcarePattern.matcher(line);
        
        if (dontcareMatcher.matches()) {
            return new NistParameterValue(
                    Integer.valueOf(dontcareMatcher.group(1)), 
                    dontcareMatcher.group(2));
        }
        
        return null;
    }

}
