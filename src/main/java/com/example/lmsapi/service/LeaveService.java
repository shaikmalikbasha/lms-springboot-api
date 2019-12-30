package com.example.lmsapi.service;


import com.example.lmsapi.exception.LeaveException;
import com.example.lmsapi.model.Leave;
import com.example.lmsapi.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
        leave = validateLeave(leave);
        return leaveRepository.save(leave);
    }

    public boolean deleteLeaveById(Long leaveId) {
        leaveRepository.deleteById(leaveId);
        return true;
    }

    public Leave updateLeaveByLeaveId(Long leaveId, Leave leave) throws LeaveException {
        Leave oldLeave = leaveRepository.findById(leaveId).orElseThrow(() -> new LeaveException("Leave not found"));
        Leave newLeave = validateLeave(leave);
        if (oldLeave.getFromDate() != null) {
            oldLeave.setFromDate(newLeave.getFromDate());
        }
        if (oldLeave.getToDate() != null) {
            oldLeave.setToDate(newLeave.getToDate());
        }
        if (oldLeave.getReason() != null) {
            oldLeave.setReason(newLeave.getReason());
        }
        oldLeave.setLeavesAvailable(newLeave.getLeavesAvailable());
        oldLeave.setLeavesUsed(newLeave.getLeavesUsed());
        return leaveRepository.save(oldLeave);
    }


    public Leave validateLeave(Leave leave) {
        leave.setFromDate(leave.getFromDate());
        leave.setToDate(leave.getToDate());
        long diff = leave.getToDate().getTime() - leave.getFromDate().getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        Integer leavesAvailable, leavesUsed;
        leavesAvailable = leaveRepository.getAvailableLeavesByEmpId(leave.getEmpId());
        if ((leavesAvailable == null) || (leavesAvailable == 0)) {
            leavesAvailable = 15;
        }
        leavesUsed = leaveRepository.getUsedLeavesofEmployee(leave.getEmpId());
        if (leavesUsed == null) {
            leavesUsed = 0;
        }
        if (diffDays > leavesAvailable) {
            throw new LeaveException("You don't have these many ( " + diffDays + " ) available leaves.\n" +
                    " You have only ( " + leavesAvailable + ") leaves.");
        }
        if (leave.getFromDate().getTime() > leave.getToDate().getTime()) {
            throw new LeaveException("Please Select proper leave dates..");
        }
        if (isWeekend(leave.getFromDate())) {
            throw new LeaveException("Leaves cannot applied on weekends.");
        }
        if (diffDays >= MAX_DAYS) {
            throw new LeaveException("You cannot applied these many (" + diffDays + ") days at a time.");
        }
 /*
        if (leave.getLeavesUsed() == null) {
            leave.setLeavesUsed((int) (leavesUsed + diffDays));
        } else {
            System.out.println("Before : leave.getLeavesUsed() : " + leave.getLeavesUsed());
            leave.setLeavesUsed(leave.getLeavesUsed());
            System.out.println("After : leave.getLeavesUsed() : " + leave.getLeavesUsed());
        }
        if (leave.getLeavesAvailable() == null) {
            leave.setLeavesAvailable((int) (leavesAvailable - diffDays));
        } else {
            System.out.println("Before : leave.getLeavesAvailable() : " + leave.getLeavesAvailable());
            leave.setLeavesAvailable(leave.getLeavesAvailable());
            System.out.println("After : leave.getLeavesAvailable() : " + leave.getLeavesAvailable());
        }

  */
        leave.setLeavesUsed((int) (leavesUsed + diffDays));
        leave.setLeavesAvailable((int) (leavesAvailable - diffDays));
        return leave;
    }

    public Integer getAvailableLeavesByEmpId(Long empId) {
        return leaveRepository.getAvailableLeavesByEmpId(empId);
    }

    public static boolean isWeekend(Date fromdate) {
        boolean response = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromdate);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            response = true;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            response = true;
        }
        return response;
    }

}
