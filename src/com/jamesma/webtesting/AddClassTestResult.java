package com.jamesma.webtesting;

// P ["$CName$", "$TCH$", "$SEM$", "$SecNum$", "$RmNum$", "$PeriodNum$", "$SUB$", "$Days$"]
// F ["$CName$", "$TCH$", "$SEM$", "$SecNum$", "$RmNum$", "$PeriodNum$", "$SUB$", "$Days$"] : Problem description

public class AddClassTestResult {
    
    private boolean result;
    private String className;
    private String teacher;
    private String semester;
    private String sectionNumber;
    private String roomNumber;
    private String periodNumber;
    private String dayOfTheWeek;
    private String substitute;
    private String problemDescription;
    
    // Constructor with problem description (Fail)
    public AddClassTestResult(boolean result, String className, String teacher,
            String semester, String sectionNumber, String roomNumber,
            String periodNumber, String dayOfTheWeek, String substitute,
            String problemDescription) {
        this.result = result;
        this.className = className;
        this.teacher = teacher;
        this.semester = semester;
        this.sectionNumber = sectionNumber;
        this.roomNumber = roomNumber;
        this.periodNumber = periodNumber;
        this.dayOfTheWeek = dayOfTheWeek;
        this.substitute = substitute;
        this.problemDescription = problemDescription;
    }
    
    // Constructor with no problem description (Pass)
    public AddClassTestResult(boolean result, String className, String teacher,
            String semester, String sectionNumber, String roomNumber,
            String periodNumber, String dayOfTheWeek, String substitute) {
        this.result = result;
        this.className = className;
        this.teacher = teacher;
        this.semester = semester;
        this.sectionNumber = sectionNumber;
        this.roomNumber = roomNumber;
        this.periodNumber = periodNumber;
        this.dayOfTheWeek = dayOfTheWeek;
        this.substitute = substitute;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(String sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(String periodNumber) {
        this.periodNumber = periodNumber;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getSubstitute() {
        return substitute;
    }

    public void setSubstitute(String substitute) {
        this.substitute = substitute;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        
        if (result) sb.append("P ");
        else sb.append("F ");
        
        sb.append("[" + "\"" + 
                className        + "\", \"" + 
                teacher          + "\", \"" +
                semester         + "\", \"" +
                sectionNumber    + "\", \"" +
                roomNumber       + "\", \"" +
                periodNumber     + "\", \"" +
                dayOfTheWeek     + "\", \"" +
                substitute       + "\"]");
        
        if (!result) sb.append(" : " + problemDescription);
        
        return sb.toString();
    }

}
