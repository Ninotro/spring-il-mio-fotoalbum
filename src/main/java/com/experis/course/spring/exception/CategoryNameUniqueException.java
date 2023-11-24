package com.experis.course.spring.exception;

public class CategoryNameUniqueException  extends  RuntimeException{

    public CategoryNameUniqueException (String message) {
        super(message);
    }
}
