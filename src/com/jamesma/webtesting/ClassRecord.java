package com.jamesma.webtesting;


public class ClassRecord {
    
    private String className;
    private String teacher;
    private String semester;
    private String sectionNumber;
    private String roomNumber;
    private String periodNumber;
    private String substitute;
    private String dayOfTheWeek;
    
    public ClassRecord(String className, String teacher, String semester,
            String sectionNumber, String roomNumber, String periodNumber,
            String substitute, String dayOfTheWeek) {
        super();
        this.className = className;
        this.teacher = teacher;
        this.semester = semester;
        this.sectionNumber = sectionNumber;
        this.roomNumber = roomNumber;
        this.periodNumber = periodNumber;
        this.substitute = substitute;
        this.dayOfTheWeek = dayOfTheWeek;
    }
    public String getClassName() {
        return className;
    }
    public String getTeacher() {
        return teacher;
    }
    public String getSemester() {
        return semester;
    }
    public String getSectionNumber() {
        return sectionNumber;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public String getPeriodNumber() {
        return periodNumber;
    }
    public String getSubstitute() {
        return substitute;
    }
    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }
    @Override
    public String toString() {
        return "ClassRecord [className=" + className + ", teacher=" + teacher
                + ", semester=" + semester + ", sectionNumber=" + sectionNumber
                + ", roomNumber=" + roomNumber + ", periodNumber="
                + periodNumber + ", substitute=" + substitute
                + ", dayOfTheWeek=" + dayOfTheWeek + "]";
    }

}
