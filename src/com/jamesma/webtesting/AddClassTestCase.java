package com.jamesma.webtesting;

//["cn1", "t1", "s1", "11", "1234", "1", "name1", "M"]

public class AddClassTestCase {
    
    private String className;
    private String teacher;
    private String semester;
    private String sectionNumber;
    private String roomNumber;
    private String periodNumber;
    private String substitute;
    private String dayOfTheWeek;
    
    public AddClassTestCase(String className, String teacher, String semester,
            String sectionNumber, String roomNumber, String periodNumber, 
            String substitute, String dayOfTheWeek) {
        this.className = className;
        this.teacher = teacher;
        this.semester = semester;
        this.sectionNumber = sectionNumber;
        this.roomNumber = roomNumber;
        this.periodNumber = periodNumber;
        this.dayOfTheWeek = dayOfTheWeek;
        this.substitute = substitute;
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

    @Override
    public String toString() {
        return "AddClassTestCase [className=" + className + ", teacher="
                + teacher + ", semester=" + semester + ", sectionNumber="
                + sectionNumber + ", roomNumber=" + roomNumber
                + ", periodNumber=" + periodNumber + ", dayOfTheWeek="
                + dayOfTheWeek + ", substitute=" + substitute + "]";
    }

}
