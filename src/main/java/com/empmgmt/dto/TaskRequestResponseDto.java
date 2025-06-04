package com.empmgmt.dto;

import java.util.UUID;
import java.util.Date;
import com.empmgmt.enums.TaskPriority;
import com.empmgmt.enums.TaskStatus;

public class TaskRequestResponseDto {

    private UUID taskId;
    private String taskName;
    private String taskDescription;
    private Date dueDate;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;
    private UUID empId;

    public TaskRequestResponseDto(UUID taskId, String taskName, String taskDescription, Date dueDate, TaskPriority taskPriority, TaskStatus taskStatus, UUID empId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.dueDate = dueDate;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
        this.empId = empId;
    }

    public TaskRequestResponseDto(String taskName, String taskDescription, Date dueDate, TaskPriority taskPriority, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.dueDate = dueDate;
        this.taskPriority = taskPriority;
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

	public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public UUID getEmpId() {
        return empId;
    }

    public void setEmpId(UUID empId) {
        this.empId = empId;
    }
}
