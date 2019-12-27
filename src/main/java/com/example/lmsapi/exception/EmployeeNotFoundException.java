package com.example.lmsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EmployeeNotFoundException extends Exception {
    private Long empId;

    public EmployeeNotFoundException(Long empId) {
        super(String.format("Employee is not found with id : '%s'", empId));
    }

}
