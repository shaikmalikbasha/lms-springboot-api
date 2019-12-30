package com.example.lmsapi.controller;

import com.example.lmsapi.model.LeaveRules;
import com.example.lmsapi.service.LeaveRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaveRulesController {

    @Autowired
    LeaveRulesService leaveRulesService;

    @GetMapping(value = "/rules")
    public List<LeaveRules> getLeaveRules() {
        return leaveRulesService.getLeaveRules();
    }

    @GetMapping(value = "/rules/{id}", produces = "application/json")
    public LeaveRules getLeaveRuleById(@PathVariable(name = "id") Long id) {
        return leaveRulesService.getLeaveRuleById(id);
    }

    @PostMapping(value = "/rules", produces = "application/json", consumes = "application/json")
    public LeaveRules addNewRule(@RequestBody LeaveRules leaveRules) {
        return leaveRulesService.addNewRule(leaveRules);
    }

    @DeleteMapping(value = "/rules/{id}")
    public boolean deleteLeaveRule(@PathVariable(name = "id") Long id) {
        return leaveRulesService.deleteLeaveRule(id);
    }

    @PutMapping(value = "/rules/{id}")
    public LeaveRules updateLeaveRule(@PathVariable(name = "id") Long id, @RequestBody LeaveRules leaveRules) {
        return leaveRulesService.updateLeaveRule(id, leaveRules);
    }
}
