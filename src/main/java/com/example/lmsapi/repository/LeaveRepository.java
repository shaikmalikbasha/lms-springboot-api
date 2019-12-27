package com.example.lmsapi.repository;

import com.example.lmsapi.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long>, CrudRepository<Leave, Long> {

    @Query(value = "SELECT l FROM Leave l WHERE l.empId = :empId")
    List<Leave> getLeaveHistoryByEmpId(Long empId);

    @Query(value = "SELECT MIN(l.leavesAvailable) FROM Leave l WHERE l.empId = :empId")
    int getAvailableLeavesByEmpId(Long empId);

    @Query(value = "SELECT l.leavesAvailable - (SELECT SUM(l.leavesUsed) FROM Leave l WHERE l.empId = :empId) FROM Leave l WHERE l.empId = :empId")
    int getAvailableLeavesofEmployee(Long empId);

//    @Query(value = "SELECT l.leavesAvailable FROM Leave l WHERE l.empId = :empId ORDER BY l.leavesAvailable ASC LIMIT 1")
//    int getAvailableLeavesofEmployee(Long empId);

//    @Query(value = "SELECT MIN(l.leavesAvailable) FROM Leave l WHERE l.empId = :empId")
//    int getAvailableLeavesofEmployee(Long empId);

    @Query(value = "SELECT SUM(l.leavesUsed) FROM Leave l WHERE l.empId = :empId")
    int getUsedLeavesofEmployee(Long empId);
}
