package com.hrms.leaveservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.leaveservice.entity.Leave;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long>{

}
