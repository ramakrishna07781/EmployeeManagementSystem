package com.empmgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empmgmt.dto.EmpUpdateDto;
import com.empmgmt.dto.UpdateResponseDto;
import com.empmgmt.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PutMapping("/update-due-date")
    public ResponseEntity<UpdateResponseDto> updateDueDate(@RequestBody EmpUpdateDto empUpdateDto) {
        UpdateResponseDto updatedTask = employeeService.updateDueDate(empUpdateDto.getTaskId(), empUpdateDto.getDueDate());
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/update-task-status")
    public ResponseEntity<UpdateResponseDto> updateTaskStatus(@RequestBody EmpUpdateDto empUpdateDto) {
        UpdateResponseDto updatedTask = employeeService.updateTaskStatus(empUpdateDto.getTaskId(), empUpdateDto.getTaskStatus());
        return ResponseEntity.ok(updatedTask);
    }


}