package com.empmgmt.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.empmgmt.dao.TaskRepository;
import com.empmgmt.dao.UserRepository;
import com.empmgmt.enums.UserRole;
import com.empmgmt.model.Task;
import com.empmgmt.model.Users;
import jakarta.transaction.Transactional;

@Service
public class ManagerService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createTask(Task task) {
        task.setTaskId(UUID.randomUUID()); 
        taskRepository.save(task);
    }

    public List<Users> getUnassignedEmployees() {
        return userRepository.findUnassignedEmployees(UserRole.EMPLOYEE);
    }

    @Transactional
    public void assignTaskToEmployee(UUID taskId, UUID empId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        Users employee = userRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!employee.getAssignedTasks().isEmpty()) {
            throw new RuntimeException("Employee already has an assigned task!");
        }

        task.setAssignedEmployee(employee);
        taskRepository.save(task);
    }
}
