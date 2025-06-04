package com.empmgmt.service;

import java.sql.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.empmgmt.dao.TaskRepository;
import com.empmgmt.dto.UpdateResponseDto;
import com.empmgmt.enums.TaskStatus;
import com.empmgmt.exception.TaskNotFoundException;
import com.empmgmt.model.Task;
import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private TaskRepository taskRepository;


    @Transactional
    public UpdateResponseDto updateDueDate(UUID taskId, String newDueDate) {
        try {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new TaskNotFoundException("Task not found"));

            task.setDueDate(Date.valueOf(newDueDate));
            taskRepository.save(task);

            return new UpdateResponseDto(task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getDueDate(), task.getTaskPriority(), task.getTaskStatus());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid due date format! Expected format: YYYY-MM-DD");
        }
    }

    @Transactional
    public UpdateResponseDto updateTaskStatus(UUID taskId, TaskStatus newStatus) {
        try {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new TaskNotFoundException("Task not found"));

            task.setTaskStatus(newStatus);
            taskRepository.save(task);

            return new UpdateResponseDto(task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getDueDate(), task.getTaskPriority(), task.getTaskStatus());
        } catch (Exception e) {
            throw new RuntimeException("Failed to update task status: " + e.getMessage());
        }
    }
}
