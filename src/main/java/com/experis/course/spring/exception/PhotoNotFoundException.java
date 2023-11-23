package com.experis.course.spring.exception;

public class PhotoNotFoundException extends RuntimeException{

    public PhotoNotFoundException(String message) {
        super(message);
    }

}
