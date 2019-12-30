package com.example.lmsapi.controller;

import com.example.lmsapi.model.Leave;
import com.example.lmsapi.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Integer getAvailableLeaves(@PathVariable(name = "id") Long empId) {
        return leaveService.getAvailableLeavesByEmpId(empId);
    }

    @PostMapping(value = "/leave")
    public Leave createLeave(@Valid @RequestBody Leave leave) {
        return leaveService.createLeaveByEmployee(leave);
    }

    @PutMapping(value = "/leave/{id}")
    public Leave updateLeaveByLeaveId(@PathVariable(name = "id") Long leaveId,@Valid @RequestBody Leave leave) {
        return leaveService.updateLeaveByLeaveId(leaveId, leave);
    }

    @PutMapping(value = "/leave/status/{id}")
    public Leave updateLeaveStatusById(@PathVariable(name = "id") Long id,@RequestBody Leave leave) {
        return leaveService.updateLeaveStatusById(id, leave);
    }

    @DeleteMapping(value = "/leave/{id}")
    public boolean cancelLeave(@PathVariable(name = "id") Long leaveId){
        return leaveService.deleteLeaveById(leaveId);
    }

//    public Leave getLeaveStatus
}
