package com.empmgmt.dto;

import java.util.UUID;
import com.empmgmt.enums.TaskStatus;

public class EmpUpdateDto {

    private UUID taskId;
    private String dueDate;
    private TaskStatus taskStatus;
    
    
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
	 * @return the dueDate
	 */
	public String getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	/**
	 * @return the taskStatus
	 */
	public TaskStatus getTaskStatus() {
		return taskStatus;
	}
	/**
	 * @param taskStatus the taskStatus to set
	 */
	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

    
}
