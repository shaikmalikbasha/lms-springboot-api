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
        validateLeave(leave);
        if (leave.getFromDate() != null) {
            oldLeave.setFromDate(leave.getFromDate());
        }
        if (leave.getToDate() != null) {
            oldLeave.setToDate(leave.getToDate());
        }
        if (leave.getReason() != null) {
            oldLeave.setReason(leave.getReason());
        }
        if (leave.getLeavesAvailable() != null) {
            oldLeave.setLeavesAvailable(leave.getLeavesAvailable());
        }
        if (leave.getLeavesUsed() != null) {
            System.out.println("Leaves Used: " + leave.getLeavesUsed());
            oldLeave.setLeavesUsed(leave.getLeavesUsed());
        }
        return leaveRepository.save(oldLeave);
    }

    public Leave getLeaveByLeaveId(Long leaveId) throws LeaveException {
        return leaveRepository.findById(leaveId).orElseThrow(() -> new LeaveException(leaveId));
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
        if (!leave.getLeaveStatus().equals("Pending")) {
            leave.setLeavesUsed((int) (leavesUsed + diffDays));
            leave.setLeavesAvailable((int) (leavesAvailable - diffDays));
        } else {
            leave.setLeavesUsed(leavesUsed - leave.getLeavesUsed());
            leave.setLeavesAvailable(leavesAvailable);
        }
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

    public Leave updateLeaveStatusById(Long id, Leave leave) {
        Leave l = this.getLeaveByLeaveId(id);
        if (leave.getLeaveStatus() != null) {
            if (leave.getLeaveStatus().equals("Approved")) {
                l.setLeaveStatus("Approved");
            } else {
                l.setLeaveStatus("Cancelled");
            }
        }
        return leaveRepository.save(l);
    }
}
