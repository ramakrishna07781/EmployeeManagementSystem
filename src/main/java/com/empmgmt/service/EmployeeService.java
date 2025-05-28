package com.empmgmt.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empmgmt.dao.EmployeeDao;
import com.empmgmt.enums.TaskStatus; // Import TaskStatus
import com.empmgmt.model.Employee;

import jakarta.transaction.Transactional; // Added for transactional consistency

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
        // Consider what happens to tasks assigned to this employee if deleted.
        // For now, assuming tasks are handled separately or by cascade delete.
        employeeDao.deleteById(empId);
    }

    @Transactional // Ensure this update is transactional
    public Employee updateEmployee(UUID empId, Employee updatedEmployee) {
        Optional<Employee> existingEmployeeOptional = employeeDao.findById(empId);
        if (existingEmployeeOptional.isPresent()) {
            Employee existingEmployee = existingEmployeeOptional.get();
            existingEmployee.setEmpName(updatedEmployee.getEmpName());
            existingEmployee.setAssignedStatus(updatedEmployee.isAssignedStatus());
            existingEmployee.setTaskStatus(updatedEmployee.getTaskStatus());
            existingEmployee.setTaskId(updatedEmployee.getTaskId()); // Ensure taskId can also be updated directly if needed
            return employeeDao.save(existingEmployee);
        } else {
            return null;
        }
    }

    public List<Employee> getUnassignedEmployees() {
        return employeeDao.findByAssignedStatusFalse();
    }

    // New method to handle unassigning a task from an employee
    @Transactional
    public void unassignTaskFromEmployee(UUID employeeId, UUID taskIdToUnassign) {
        Optional<Employee> employeeOptional = employeeDao.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            // Only unassign if the task being unassigned matches the one currently assigned
            // This prevents unintended unassignments if an employee has multiple tasks (though your model only allows one taskId per employee)
            if (employee.getTaskId() != null && employee.getTaskId().equals(taskIdToUnassign)) {
                employee.setAssignedStatus(false); // Set assigned status to false
                employee.setTaskStatus(TaskStatus.ASSIGNED); // Or some other appropriate status like UNASSIGNED
                employee.setTaskId(null); // Remove task ID from employee
                employeeDao.save(employee);
            }
        }
    }
}