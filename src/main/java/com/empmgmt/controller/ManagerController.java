package com.empmgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empmgmt.dto.AssignTaskRequestDto;
import com.empmgmt.dto.TaskRequestResponseDto;
import com.empmgmt.dto.UnassignedEmployeeDto;
import com.empmgmt.model.Task;
import com.empmgmt.service.ManagerService;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping("/create-task")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = managerService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

//    @GetMapping("/employees/unassigned")
//    public ResponseEntity<List<Users>> getUnassignedEmployees() {
//        return ResponseEntity.ok(managerService.getUnassignedEmployees());
//    }
    
    @GetMapping("/employees/unassigned")
    public ResponseEntity<List<UnassignedEmployeeDto>> getUnassignedEmployees() {
        return ResponseEntity.ok(managerService.getUnassignedEmployees());
    }


    @PutMapping("/assign-task")
    public ResponseEntity<TaskRequestResponseDto> assignTask(@RequestBody AssignTaskRequestDto requestDto) {
        TaskRequestResponseDto updatedTask = managerService.assignTaskToEmployee(requestDto.getTaskId(), requestDto.getEmpId());
        return ResponseEntity.ok(updatedTask);
    }


//    @GetMapping("/task")
//    public ResponseEntity<TaskRequestResponseDto> getTaskByParams(@RequestParam UUID taskId) {
//        TaskRequestResponseDto taskDto = managerService.getTaskById(taskId);
//        return ResponseEntity.ok(taskDto);
//    }

}
