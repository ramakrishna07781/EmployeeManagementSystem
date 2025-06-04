package com.empmgmt.dto;

import java.util.UUID;

public class AssignTaskRequestDto {

    private UUID taskId;
    private UUID empId;

    public AssignTaskRequestDto() {}

    public AssignTaskRequestDto(UUID taskId, UUID empId) {
        this.taskId = taskId;
        this.empId = empId;
    }

	/**
	 * @return the taskId
	 */
	public UUID getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(UUID taskId) {
		this.taskId = taskId;
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

    
}
