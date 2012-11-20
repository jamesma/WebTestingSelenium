package com.jamesma.webtesting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TestResultWriter {
    
    public static void writeTestResultsToFile(String fileName, ArrayList<AddClassTestResult> testResults) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            try {
                for (AddClassTestResult testResult : testResults) {
                    writer.write(testResult.toString());
                    writer.newLine();
                    writer.newLine();
                }
            } finally {
                writer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
