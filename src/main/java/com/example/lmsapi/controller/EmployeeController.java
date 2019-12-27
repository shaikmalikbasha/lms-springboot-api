package com.example.lmsapi.controller;

import com.example.lmsapi.exception.EmployeeNotFoundException;
import com.example.lmsapi.model.Employee;
import com.example.lmsapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping(value = "/employees", produces = "application/json")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(value = "/employees/{id}")
    public Employee getEmployeeById(@PathVariable(name = "id") Long empId) throws EmployeeNotFoundException {
        return employeeService.getEmployeeById(empId);
    }
    @PostMapping(value = "/employees")
    public Employee addEmployee(@RequestBody @Valid Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @DeleteMapping(value = "/employees/{id}")
    public boolean deleteEmployeeById(@PathVariable(name = "id") Long empId) {
        return employeeService.deleteEmployeeById(empId);
    }

    @PutMapping(value = "/employees/{id}")
    public Employee updateEmployeeById(@PathVariable(name = "id") Long empId, @RequestBody @Valid Employee employee) throws EmployeeNotFoundException {
        return employeeService.updateEmployee(empId, employee);
    }
}
