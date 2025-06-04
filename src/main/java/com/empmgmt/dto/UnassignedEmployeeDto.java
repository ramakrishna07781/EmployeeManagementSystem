package com.empmgmt.dto;

import java.util.UUID;

public class UnassignedEmployeeDto {

    private UUID empId;
    private String name;
    private String emailId;

    public UnassignedEmployeeDto() {}

    public UnassignedEmployeeDto(UUID empId, String name, String emailId) {
        this.empId = empId;
        this.name = name;
        this.emailId = emailId;
    }

    public UUID getEmpId() {
        return empId;
    }

    public void setEmpId(UUID empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
