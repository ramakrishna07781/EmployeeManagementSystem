package com.empmgmt.model;

import java.util.UUID;

import com.empmgmt.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="empDetails")
public class Employee {
	
	@Id
	private UUID empId;
	
	@Column(nullable=true)
	private UUID taskId;
	
	@Column(nullable=false)
	private String empName;
	
	private boolean assignedStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private TaskStatus taskStatus;

	@PrePersist
	public void generateId() {
		if (this.empId == null) {
			this.empId = UUID.randomUUID();
		}
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
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * @return the assignedStatus
	 */
	public boolean isAssignedStatus() {
		return assignedStatus;
	}

	/**
	 * @param assignedStatus the assignedStatus to set
	 */
	public void setAssignedStatus(boolean assignedStatus) {
		this.assignedStatus = assignedStatus;
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
	
	
	
}
