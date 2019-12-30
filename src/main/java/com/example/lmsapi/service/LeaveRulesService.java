package com.example.lmsapi.service;

import com.example.lmsapi.exception.LeaveException;
import com.example.lmsapi.model.LeaveRules;
import com.example.lmsapi.repository.LeaveRulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRulesService {

    @Autowired
    LeaveRulesRepository leaveRulesRepository;

    public List<LeaveRules> getLeaveRules() {
        return leaveRulesRepository.findAll();
    }

    public LeaveRules addNewRule(LeaveRules leaveRules) {
        return leaveRulesRepository.save(leaveRules);
    }

    public LeaveRules getLeaveRuleById(Long id) throws LeaveException {
        return leaveRulesRepository.findById(id).orElseThrow(() -> new LeaveException(id));
    }

    public boolean deleteLeaveRule(Long id) {
        leaveRulesRepository.deleteById(id);
        return true;
    }

    public LeaveRules updateLeaveRule(Long id, LeaveRules leaveRules) {
        LeaveRules leaveRules1 = this.getLeaveRuleById(id);
        leaveRules1.setType(leaveRules.getType());
        leaveRules1.setMaxLeaves(leaveRules.getMaxLeaves());
        return leaveRulesRepository.save(leaveRules1);
    }
}
