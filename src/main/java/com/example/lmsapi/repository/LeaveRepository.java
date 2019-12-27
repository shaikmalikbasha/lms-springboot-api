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
}
