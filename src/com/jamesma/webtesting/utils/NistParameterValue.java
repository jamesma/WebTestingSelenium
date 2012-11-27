package com.jamesma.webtesting.utils;

public class NistParameterValue {
    
    private int number;
    private String value;
    
    public NistParameterValue(int number, String value) {
        super();
        this.number = number;
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
