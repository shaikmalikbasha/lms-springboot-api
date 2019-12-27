package com.example.lmsapi.service;

import com.example.lmsapi.exception.CustomException;
import com.example.lmsapi.exception.LeaveException;
import com.example.lmsapi.model.Leave;
import com.example.lmsapi.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Calendar;
import java.util.List;

@Service
public class LeaveService {

    private static final int MAX_DAYS = 3;
    @Autowired
    LeaveRepository leaveRepository;

    public List<Leave> getLeaveHistoryByEmployeeId(Long empId) {
        return leaveRepository.getLeaveHistoryByEmpId(empId);
    }

    public Leave createLeaveByEmployee(Leave leave) {
        leave.setFromDate(leave.getFromDate());
        leave.setToDate(leave.getToDate());
        long diff = leave.getToDate().getTime() - leave.getFromDate().getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        int leavesAvailable, leavesUsed;

        leavesAvailable = leaveRepository.getAvailableLeavesofEmployee(leave.getEmpId());
        leavesUsed = leaveRepository.getUsedLeavesofEmployee(leave.getEmpId());

        if (diffDays > leavesAvailable) {
            throw new LeaveException("You don't have these many ( " + diffDays + " ) available leaves.\n You have only ( " + leavesAvailable + ") leaves.");
        }
        if (leave.getFromDate().getTime() > leave.getToDate().getTime()) {
            throw new LeaveException("Please Select proper leave dates..");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(leave.getFromDate());

        if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
            throw new LeaveException("Leaves cannot applied on weekends");
        }
        if (diffDays >= MAX_DAYS) {
            throw new LeaveException("You cannot applied these many (" + diffDays + ") days at a time");
        }

        leave.setLeavesAvailable((int) (leavesAvailable - diffDays));
        leave.setLeavesUsed((int) (leavesUsed + diffDays));
        return leaveRepository.save(leave);
    }

    public int getAvailableLeavesByEmpId(Long empId) {
        return leaveRepository.getAvailableLeavesByEmpId(empId);
    }
}
