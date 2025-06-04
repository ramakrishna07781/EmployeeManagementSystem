package com.empmgmt.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.empmgmt.dao.TaskRepository;
import com.empmgmt.dao.UserRepository;
import com.empmgmt.dto.TaskRequestResponseDto;
import com.empmgmt.dto.UnassignedEmployeeDto;
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
    public Task createTask(Task task) {
        task.setTaskId(UUID.randomUUID());
        taskRepository.save(task);
        return task;
    }

//    public List<Users> getUnassignedEmployees() {
//        return userRepository.findUnassignedEmployees(UserRole.EMPLOYEE);
//    }
    public List<UnassignedEmployeeDto> getUnassignedEmployees() {
        List<Users> unassignedEmployees = userRepository.findUnassignedEmployees(UserRole.EMPLOYEE);

        return unassignedEmployees.stream().map(user -> 
            new UnassignedEmployeeDto(
                user.getEmpId(),
                user.getName(),
                user.getEmailId()
            )
        ).toList();
    }


    @Transactional
    public TaskRequestResponseDto assignTaskToEmployee(UUID taskId, UUID empId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        Users employee = userRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!employee.getAssignedTasks().isEmpty()) {
            throw new RuntimeException("Employee already has an assigned task!");
        }

        task.setAssignedEmployee(employee);
        taskRepository.save(task);

        return new TaskRequestResponseDto(
            task.getTaskId(), task.getTaskName(), task.getTaskDescription(),
            task.getDueDate(), task.getTaskPriority(), task.getTaskStatus(), empId
        );
    }

//    public TaskRequestResponseDto getTaskById(UUID taskId) {
//        Task task = taskRepository.findById(taskId)
//                .orElseThrow(() -> new RuntimeException("Task not found"));
//
//        return new TaskRequestResponseDto(
//            task.getTaskId(), task.getTaskName(), task.getTaskDescription(),
//            task.getDueDate(), task.getTaskPriority(), task.getTaskStatus(),
//            task.getAssignedEmployee() != null ? task.getAssignedEmployee().getEmpId() : null
//        );
//    }

}
