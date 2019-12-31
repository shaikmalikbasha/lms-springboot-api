package com.example.lmsapi.repository;

import com.example.lmsapi.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long>, CrudRepository<Leave, Long> {

    @Query(value = "SELECT l FROM Leave l WHERE l.empId = :empId ORDER BY id DESC")
    List<Leave> getLeaveHistoryByEmpId(Long empId);

    @Query(value = "SELECT MIN(l.leavesAvailable) FROM Leave l WHERE l.empId = :empId")
    Integer getAvailableLeavesByEmpId(Long empId);

    @Query(value = "SELECT SUM(l.leavesUsed) FROM Leave l WHERE l.empId = :empId WHERE l.leaveStatus = 'Approved'")
    Integer getUsedLeavesofEmployee(Long empId);

//    @Query(value = "SELECT * FROM Leave WHERE id = :leaveId")
//    Leave findLeaveById(Leave leaveId);

}
