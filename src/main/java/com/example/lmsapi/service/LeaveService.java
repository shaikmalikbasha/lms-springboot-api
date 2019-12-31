package com.example.lmsapi.service;

import com.example.lmsapi.exception.LeaveException;
import com.example.lmsapi.model.Leave;
import com.example.lmsapi.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
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
        Leave oldLeave = leaveRepository.findById(leaveId).orElseThrow(() -> new LeaveException(leaveId));
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
//        if (leave.getLeavesAvailable() != null) {
//            oldLeave.setLeavesAvailable(leave.getLeavesAvailable());
//        }
//        if (leave.getLeavesUsed() != null) {
//            oldLeave.setLeavesUsed(leave.getLeavesUsed());
//        }
        oldLeave.setLeavesAvailable(oldLeave.getLeavesAvailable());
        oldLeave.setLeavesUsed(oldLeave.getLeavesUsed());
        return leaveRepository.save(oldLeave);
    }

    public Leave getLeaveByLeaveId(Long leaveId) throws LeaveException {
        return leaveRepository.findById(leaveId).orElseThrow(() -> new LeaveException(leaveId));
    }

    public Leave validateLeave(Leave leave) {
        leave.setFromDate(leave.getFromDate());
        leave.setToDate(leave.getToDate());
        int numberOfDays = getNumberOfDaysApplied(leave.getFromDate(), leave.getToDate());
        Integer leavesAvailable = getTotalAvailableLeavesOfEmployee(leave.getEmpId());
        if (numberOfDays > leavesAvailable) {
            throw new LeaveException("You don't have these many ( " + numberOfDays + " ) available leaves.\n" +
                    " You have only ( " + leavesAvailable + ") leaves.");
        }
        if (leave.getFromDate().getTime() > leave.getToDate().getTime()) {
            throw new LeaveException("Please Select proper leave dates..");
        }
        if (isWeekend(leave.getFromDate())) {
            throw new LeaveException("Leaves cannot applied on weekends.");
        }
        if (numberOfDays >= MAX_DAYS) {
            throw new LeaveException("You cannot applied these many (" + numberOfDays + ") days at a time.");
        }
        return leave;
    }

    public Integer getTotalUsedLeavesOfEmployee(Long empId) {
        Integer leavesUsed = leaveRepository.getUsedLeavesofEmployee(empId);
        if (leavesUsed == null) {
            leavesUsed = 0;
        }
        return leavesUsed;
    }

    public Integer getTotalAvailableLeavesOfEmployee(Long empId) {
        Integer leavesAvailable = leaveRepository.getAvailableLeavesByEmpId(empId);
        if ((leavesAvailable == null) || (leavesAvailable == 0)) {
            leavesAvailable = 15;
        }
        return leavesAvailable;
    }

    public Integer getAvailableLeavesByEmpId(Long empId) {
        return leaveRepository.getAvailableLeavesByEmpId(empId);
    }

    public static boolean isWeekend(Date fromDate) {
        boolean response = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            response = true;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            response = true;
        }
        return response;
    }

    public int getNumberOfDaysApplied(Date fromDate, Date toDate) {
        int numberOfDays = 0;
        LocalDate start = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            Date targetDate = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            if (!isWeekend(targetDate)) {
                numberOfDays++;
            }
        }
        return numberOfDays;
    }

    public Leave updateLeaveStatusById(Long leaveId, Leave leave) {
        Leave l = this.getLeaveByLeaveId(leaveId);
        if (!l.getLeaveStatus().equals("Pending")) {
            throw new LeaveException("The action (Approved/Cancelled) on this leave was already taken.");
        }
        int numberOfDays = getNumberOfDaysApplied(l.getFromDate(), l.getToDate());
        Integer leavesAvailable = getTotalAvailableLeavesOfEmployee(leave.getEmpId());
        Integer leavesUsed = getTotalUsedLeavesOfEmployee(leave.getEmpId());
        if (leave.getLeaveStatus() != null) {
            if (leave.getLeaveStatus().equals("Approved")) {
                l.setLeavesUsed(leavesUsed + numberOfDays);
                l.setLeavesAvailable(leavesAvailable - numberOfDays);
                l.setLeaveStatus("Approved");
            } else {
                l.setLeavesUsed(l.getLeavesUsed());
                l.setLeavesAvailable(l.getLeavesAvailable());
                l.setLeaveStatus("Cancelled");
            }
        }
        return leaveRepository.save(l);
    }
}
