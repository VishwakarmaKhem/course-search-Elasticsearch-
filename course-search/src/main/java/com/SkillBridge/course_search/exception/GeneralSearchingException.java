package com.SkillBridge.course_search.exception;

public class GeneralSearchingException extends RuntimeException{
    private String message;

    public GeneralSearchingException(String message){
        super(message);
        this.message = message;
    }
}
