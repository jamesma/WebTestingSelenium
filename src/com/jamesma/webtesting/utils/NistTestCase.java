package com.jamesma.webtesting.utils;

import java.util.ArrayList;

/**
 * Format:
 * 1 = classname=CS123
 * 2 = sectionnumber=123
 * 3 = roomnumber=whitespace
 * 4 = periodnumber=<script>xss attacked</script>
 * 5 = teacher=Teacher 2
 * 6 = semester=Spring 2013
 * 7 = substitute=Substitute 1
 * 8 = dayofweek=F
 */
public class NistTestCase {
    
    private ArrayList<NistParameterValue> params;
    
    private String className;
    private String teacher;
    private String semester;
    private String sectionNumber;
    private String roomNumber;
    private String periodNumber;
    private String substitute;
    private String dayOfTheWeek;
    
    private static final String EMPTY                       = "empty";
    private static final String WHITESPACE                  = "whitespace";
    private static final String DONT_CARE                   = "(don't care)";
    private static final String DONT_CARE_CLASSNAME         = "CS";
    private static final String DONT_CARE_TEACHER           = "Teacher 1";
    private static final String DONT_CARE_SEMESTER          = "Fall 2012";
    private static final String DONT_CARE_SECTION_NUMBER    = "123";
    private static final String DONT_CARE_ROOM_NUMBER       = "123";
    private static final String DONT_CARE_PERIOD_NUMBER     = "123";
    private static final String DONT_CARE_SUBSTITUTE        = "Substitute 1";
    private static final String DONT_CARE_DAY_OF_WEEK       = "M";

    public NistTestCase() {
        super();
        params = new ArrayList<NistParameterValue>();
    }
    
    /**
     * Append param to ArrayList of NistParameterValue.
     * 
     * @param param
     */
    public void appendParam(NistParameterValue param) {
        params.add(param);
    }
    
    /**
     * Tranform params ArrayList into instance variables.
     * 
     * @return
     */
    public NistTestCase transform() {
        for (NistParameterValue param : params) {
            String value = param.getValue();
            if (value.equals(EMPTY))         value = "";
            if (value.equals(WHITESPACE))    value = "   ";
            
            switch (param.getNumber()) {
                case 1: className       = (value.equals(DONT_CARE)) ? DONT_CARE_CLASSNAME : value;
                break;
                case 2: sectionNumber   = (value.equals(DONT_CARE)) ? DONT_CARE_SECTION_NUMBER : value;
                break;
                case 3: roomNumber      = (value.equals(DONT_CARE)) ? DONT_CARE_ROOM_NUMBER : value;
                break;
                case 4: periodNumber    = (value.equals(DONT_CARE)) ? DONT_CARE_PERIOD_NUMBER : value;
                break;
                case 5: teacher         = (value.equals(DONT_CARE)) ? DONT_CARE_TEACHER : value;
                break;
                case 6: semester        = (value.equals(DONT_CARE)) ? DONT_CARE_SEMESTER : value;
                break;
                case 7: substitute      = (value.equals(DONT_CARE)) ? DONT_CARE_SUBSTITUTE : value;
                break;
                case 8: dayOfTheWeek    = (value.equals(DONT_CARE)) ? DONT_CARE_DAY_OF_WEEK : value;
                break;
                default:
                break;
            }
        }
        return this;
    }

    /**
     * Convert NistTestCase to proper AddClassTestCase input format.
     * ["", "Teacher 1", "Fall 2012", "1", "2", "3", "Substitute 1", "M"]
     */
    @Override
    public String toString() {
        return "[" + 
                "\"" + className        + "\", " +
                "\"" + teacher          + "\", " +
                "\"" + semester         + "\", " +
                "\"" + sectionNumber    + "\", " +
                "\"" + roomNumber       + "\", " +
                "\"" + periodNumber     + "\", " +
                "\"" + substitute       + "\", " +
                "\"" + dayOfTheWeek     + "\"" +
                "]";
    }

}
