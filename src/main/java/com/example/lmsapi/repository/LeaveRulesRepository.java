package com.example.lmsapi.repository;

import com.example.lmsapi.model.LeaveRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRulesRepository extends JpaRepository<LeaveRules, Long>, CrudRepository<LeaveRules, Long> {

}
