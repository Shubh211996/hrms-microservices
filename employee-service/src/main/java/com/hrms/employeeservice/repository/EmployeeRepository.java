package com.hrms.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.employeeservice.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
