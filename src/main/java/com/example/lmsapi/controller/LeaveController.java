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

    @GetMapping(value = "/leave/{id}")
    public List<Leave> getLeaveByEmployeeId(@PathVariable(name = "id") Long empId) {
        return leaveService.getLeaveHistoryByEmployeeId(empId);
    }

    @PostMapping(value = "/leave")
    public Leave createLeave(@RequestBody Leave leave) {
        return leaveService.createLeaveByEmployee(leave);
    }
}
