package com.example.lmsapi.service;

import com.example.lmsapi.exception.EmployeeNotFoundException;
import com.example.lmsapi.model.Employee;
import com.example.lmsapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long empId) throws EmployeeNotFoundException {
        return employeeRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException(empId));
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long empId, Employee employee) throws EmployeeNotFoundException {
        Employee e = this.getEmployeeById(empId);
        e.setEmail(employee.getEmail());
        e.setName(employee.getName());
        e.setUsername(employee.getUsername());
        e.setPassword(employee.getPassword());
        return employeeRepository.save(e);
    }

    public boolean deleteEmployeeById(Long empId) {
        employeeRepository.deleteById(empId);
        return true;
    }

}
