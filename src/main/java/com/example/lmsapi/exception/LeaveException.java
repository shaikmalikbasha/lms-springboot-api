package com.example.lmsapi.exception;

public class LeaveException extends RuntimeException {
    private String message;
    public LeaveException(String message) {
        super(message);
    }
}
