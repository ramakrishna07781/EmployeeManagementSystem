package com.empmgmt.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empmgmt.dao.ManagerDao;
import com.empmgmt.dto.TaskDto;
import com.empmgmt.dto.TaskResponseDto;
import com.empmgmt.enums.TaskStatus; // Import TaskStatus
import com.empmgmt.model.Employee; // Import Employee
import com.empmgmt.model.Task;

import jakarta.transaction.Transactional;

@Service
public class ManagerService {

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private EmployeeService employeeService; // Inject EmployeeService

    private TaskResponseDto convertToResponseDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setTaskId(task.getTaskId());
        dto.setTaskName(task.getTaskName());
        dto.setTaskDescription(task.getTaskDescription());
        dto.setDueDate(task.getDueDate());
        dto.setTaskPriority(task.getTaskPriority());
        dto.setTaskStatus(task.getTaskStatus());
        dto.setAssignedTo(task.getAssignedTo());
        return dto;
    }

    public TaskResponseDto createNewTask(TaskDto taskDto) {
        Task task = new Task();

        task.setTaskName(taskDto.getTaskName());
        task.setTaskDescription(taskDto.getTaskDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setTaskPriority(taskDto.getTaskPriority());
        task.setTaskStatus(taskDto.getTaskStatus());
        // For new tasks, assignedTo is null by default. No need to set it from taskDto unless explicitly provided in the create request.
        // If you decide to allow assignedTo in createTask:
        // task.setAssignedTo(taskDto.getAssignedTo());

        Task savedTaskEntity = managerDao.save(task);

        return convertToResponseDto(savedTaskEntity);
    }

    public TaskResponseDto getTaskDetailsById(UUID taskId) {
        Optional<Task> taskOptional = managerDao.findById(taskId);
        if (taskOptional.isPresent()) {
            return convertToResponseDto(taskOptional.get());
        }
        return null;
    }

    @Transactional
    public TaskResponseDto updateTask(UUID taskId, TaskDto taskDto) {
        Optional<Task> taskOptional = managerDao.findById(taskId);

        if (taskOptional.isPresent()) {
            Task existingTask = taskOptional.get();

            // Store the old assignedTo UUID for comparison
            UUID oldAssignedTo = existingTask.getAssignedTo();
            UUID newAssignedTo = taskDto.getAssignedTo();

            // Update Task fields from the DTO
            existingTask.setTaskName(taskDto.getTaskName());
            existingTask.setTaskDescription(taskDto.getTaskDescription());
            existingTask.setDueDate(taskDto.getDueDate());
            existingTask.setTaskPriority(taskDto.getTaskPriority());
            existingTask.setTaskStatus(taskDto.getTaskStatus()); // This might also change, consider its impact

            // Handle assignedTo status change
            if (newAssignedTo != null && !newAssignedTo.equals(oldAssignedTo)) {
                // Case 1: Task is being newly assigned OR assigned to a different employee

                // 1. Unassign old employee if any
                if (oldAssignedTo != null) {
                    employeeService.unassignTaskFromEmployee(oldAssignedTo, taskId); // New method needed in EmployeeService
                }

                // 2. Assign to new employee
                Optional<Employee> employeeOptional = employeeService.getEmployeeById(newAssignedTo);
                if (employeeOptional.isPresent()) {
                    Employee employee = employeeOptional.get();
                    employee.setAssignedStatus(true); // Assigned status true
                    employee.setTaskStatus(TaskStatus.ASSIGNED); // Task status assigned
                    employee.setTaskId(taskId); // Assign task ID to employee
                    employeeService.saveEmployee(employee); // Save updated employee
                } else {
                    // Handle case where newAssignedTo UUID is invalid/employee not found
                    // You might throw an exception or log a warning.
                    // For now, let's just log and proceed without assigning.
                    System.err.println("Attempted to assign task to non-existent employee: " + newAssignedTo);
                    // Optionally, you might want to prevent the task update if the employee isn't found
                    // throw new IllegalArgumentException("Employee with ID " + newAssignedTo + " not found.");
                }
                existingTask.setAssignedTo(newAssignedTo); // Update task's assignedTo
                existingTask.setTaskStatus(TaskStatus.ASSIGNED); // Set task status to ASSIGNED if it's newly assigned
            } else if (newAssignedTo == null && oldAssignedTo != null) {
                // Case 2: Task is being unassigned from an employee
                employeeService.unassignTaskFromEmployee(oldAssignedTo, taskId); // New method needed in EmployeeService
                existingTask.setAssignedTo(null); // Set task's assignedTo to null
                // You might also want to change the taskStatus here, e.g., to NEW or PENDING
                // existingTask.setTaskStatus(TaskStatus.NEW);
            }
            // If newAssignedTo is null and oldAssignedTo is null, nothing changes.
            // If newAssignedTo is same as oldAssignedTo, nothing changes regarding assignment.


            Task updatedTaskEntity = managerDao.save(existingTask);

            return convertToResponseDto(updatedTaskEntity);
        }
        return null; // Task not found
    }

    @Transactional
    public boolean deleteTask(UUID taskId) {
        // When a task is deleted, also unassign it from any employee it might be assigned to.
        Optional<Task> taskOptional = managerDao.findById(taskId);
        if (taskOptional.isPresent()) {
            Task taskToDelete = taskOptional.get();
            if (taskToDelete.getAssignedTo() != null) {
                employeeService.unassignTaskFromEmployee(taskToDelete.getAssignedTo(), taskId); // New method needed
            }
            managerDao.deleteById(taskId);
            return true;
        }
        return false;
    }

    public List<TaskResponseDto> getAllTasks() {
        List<Task> tasks = managerDao.findAll();
        return tasks.stream()
                     .map(this::convertToResponseDto)
                     .collect(Collectors.toList());
    }
}