package com.empmgmt.model;

import java.util.Date;
import java.util.UUID;

import com.empmgmt.enums.TaskPriority;
import com.empmgmt.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="Task")
public class Task {

	@Id
	private UUID taskId;
	
	@Column(nullable=false)
	private String taskName;
	
	@Column(nullable=false)
	private String taskDescription;
	
	@Column(nullable=false)
	private Date dueDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private TaskPriority taskPriority;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private TaskStatus taskStatus;
	
	@Column(nullable=true)
	private UUID assignedTo;

	@PrePersist
	public void generateId() {
		if (this.taskId == null) {
			this.taskId = UUID.randomUUID();
		}
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
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the taskDescription
	 */
	public String getTaskDescription() {
		return taskDescription;
	}

	/**
	 * @param taskDescription the taskDescription to set
	 */
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the taskPriority
	 */
	public TaskPriority getTaskPriority() {
		return taskPriority;
	}

	/**
	 * @param taskPriority the taskPriority to set
	 */
	public void setTaskPriority(TaskPriority taskPriority) {
		this.taskPriority = taskPriority;
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
	 * @return the assignedTo
	 */
	public UUID getAssignedTo() {
		return assignedTo;
	}

	/**
	 * @param assignedTo the assignedTo to set
	 */
	public void setAssignedTo(UUID assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * @return the assignedTo
	 */	

}
