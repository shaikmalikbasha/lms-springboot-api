package com.example.lmsapi.repository;

import com.example.lmsapi.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    @Query(value = "SELECT l FROM leaves l WHERE l.emp_id = :empId ")
    List<Leave> findLeaveByEmpId(Long empId);
}
