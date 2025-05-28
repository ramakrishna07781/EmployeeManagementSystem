package com.empmgmt.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empmgmt.model.Employee;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, UUID>{
    List<Employee> findByAssignedStatusFalse();
}
