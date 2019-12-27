package com.example.lmsapi.controller;

import com.example.lmsapi.model.Leave;
import com.example.lmsapi.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaveController {
    @Autowired
    LeaveService leaveService;

    @GetMapping(value = "/leave/emp/{id}")
    public List<Leave> getLeaveByEmployeeId(@PathVariable(name = "id") Long empId) {
        return leaveService.getLeaveHistoryByEmployeeId(empId); // Return Leaves History of Employee by using ID
    }

    @GetMapping(value = "/leave/checkleaves/{id}")
    public int getAvailableLeaves(@PathVariable(name = "id") Long empId) {
        return leaveService.getAvailableLeavesByEmpId(empId);
    }

    @PostMapping(value = "/leave")
    public Leave createLeave(@RequestBody Leave leave) {
        return leaveService.createLeaveByEmployee(leave);
    }

//    public Leave getLeaveStatus
}
