package com.example.lmsapi.exception;

public class CustomException extends RuntimeException {
    private String message;

//    public CustomException(String message) {
//        this.message = message;
//    }
    public CustomException(String message) {
        super(message);
    }
}
