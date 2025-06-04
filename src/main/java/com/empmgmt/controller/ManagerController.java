package com.empmgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.empmgmt.model.Task;
import com.empmgmt.model.Users;
import com.empmgmt.service.ManagerService;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping("/create-task")
    public ResponseEntity<String> createTask(@RequestBody Task task) {
        managerService.createTask(task);
        return ResponseEntity.ok("Task created successfully!");
    }

    @GetMapping("/employees/unassigned")
    public ResponseEntity<List<Users>> getUnassignedEmployees() {
        return ResponseEntity.ok(managerService.getUnassignedEmployees());
    }

    @PutMapping("/assign-task/{taskId}/{empId}")
    public ResponseEntity<String> assignTask(@PathVariable UUID taskId, @PathVariable UUID empId) {
        managerService.assignTaskToEmployee(taskId, empId);
        return ResponseEntity.ok("Task assigned successfully!");
    }
}
