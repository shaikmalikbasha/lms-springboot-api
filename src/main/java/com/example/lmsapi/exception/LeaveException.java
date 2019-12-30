package com.example.lmsapi.exception;

public class LeaveException extends RuntimeException {
    private String message;
    private Long leaveId;
    public LeaveException(String message) {
        super(message);
    }

    public LeaveException(Long leaveId) {
        super(String.format("Leave is not found with id : '%s'", leaveId));
    }
}
