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
import com.empmgmt.enums.TaskStatus;
import com.empmgmt.model.Employee;
import com.empmgmt.model.Task;

import jakarta.transaction.Transactional;

@Service
public class ManagerService {

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private EmployeeService employeeService;

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

            UUID oldAssignedTo = existingTask.getAssignedTo();
            UUID newAssignedTo = taskDto.getAssignedTo();

            existingTask.setTaskName(taskDto.getTaskName());
            existingTask.setTaskDescription(taskDto.getTaskDescription());
            existingTask.setDueDate(taskDto.getDueDate());
            existingTask.setTaskPriority(taskDto.getTaskPriority());
            existingTask.setTaskStatus(taskDto.getTaskStatus()); 

            if (newAssignedTo != null && !newAssignedTo.equals(oldAssignedTo)) {

                if (oldAssignedTo != null) {
                    employeeService.unassignTaskFromEmployee(oldAssignedTo, taskId);
                }

                Optional<Employee> employeeOptional = employeeService.getEmployeeById(newAssignedTo);
                if (employeeOptional.isPresent()) {
                    Employee employee = employeeOptional.get();
                    employee.setAssignedStatus(true);
                    employee.setTaskStatus(TaskStatus.ASSIGNED);
                    employee.setTaskId(taskId);
                    employeeService.saveEmployee(employee);
                } else {
                    System.err.println("Attempted to assign task to non-existent employee: " + newAssignedTo);                    // throw new IllegalArgumentException("Employee with ID " + newAssignedTo + " not found.");
                }
                existingTask.setAssignedTo(newAssignedTo);
                existingTask.setTaskStatus(TaskStatus.ASSIGNED);
            } else if (newAssignedTo == null && oldAssignedTo != null) {
                employeeService.unassignTaskFromEmployee(oldAssignedTo, taskId);
                existingTask.setAssignedTo(null);
            }


            Task updatedTaskEntity = managerDao.save(existingTask);

            return convertToResponseDto(updatedTaskEntity);
        }
        return null;
    }

    @Transactional
    public boolean deleteTask(UUID taskId) {
        Optional<Task> taskOptional = managerDao.findById(taskId);
        if (taskOptional.isPresent()) {
            Task taskToDelete = taskOptional.get();
            if (taskToDelete.getAssignedTo() != null) {
                employeeService.unassignTaskFromEmployee(taskToDelete.getAssignedTo(), taskId);
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