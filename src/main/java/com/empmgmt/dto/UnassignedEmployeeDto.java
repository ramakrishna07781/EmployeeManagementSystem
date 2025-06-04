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

	/**
	 * @return the empId
	 */
	public UUID getEmpId() {
		return empId;
	}

	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(UUID empId) {
		this.empId = empId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

    
}
