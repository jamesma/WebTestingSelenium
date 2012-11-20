package com.jamesma.webtesting;

public class RequirementViolationException extends Exception {
    
    private static final long serialVersionUID = -5176083791785380245L;
    private String message;
    
    public RequirementViolationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
