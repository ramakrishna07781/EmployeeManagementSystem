package com.empmgmt.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empmgmt.dao.EmployeeDao;
import com.empmgmt.enums.TaskStatus;
import com.empmgmt.model.Employee;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    public Employee saveEmployee(Employee employee) {
        return employeeDao.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    public Optional<Employee> getEmployeeById(UUID empId) {
        return employeeDao.findById(empId);
    }

    public void deleteEmployee(UUID empId) {
        employeeDao.deleteById(empId);
    }

    @Transactional
    public Employee updateEmployee(UUID empId, Employee updatedEmployee) {
        Optional<Employee> existingEmployeeOptional = employeeDao.findById(empId);
        if (existingEmployeeOptional.isPresent()) {
            Employee existingEmployee = existingEmployeeOptional.get();
            existingEmployee.setEmpName(updatedEmployee.getEmpName());
            existingEmployee.setAssignedStatus(updatedEmployee.isAssignedStatus());
            existingEmployee.setTaskStatus(updatedEmployee.getTaskStatus());
            existingEmployee.setTaskId(updatedEmployee.getTaskId());
            return employeeDao.save(existingEmployee);
        } else {
            return null;
        }
    }

    public List<Employee> getUnassignedEmployees() {
        return employeeDao.findByAssignedStatusFalse();
    }

    @Transactional
    public void unassignTaskFromEmployee(UUID employeeId, UUID taskIdToUnassign) {
        Optional<Employee> employeeOptional = employeeDao.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (employee.getTaskId() != null && employee.getTaskId().equals(taskIdToUnassign)) {
                employee.setAssignedStatus(false);
                employee.setTaskStatus(TaskStatus.ASSIGNED);
                employee.setTaskId(null);
                employeeDao.save(employee);
            }
        }
    }
}