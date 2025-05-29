package com.empmgmt.dto;

import java.util.Date;
import java.util.UUID;

import com.empmgmt.enums.TaskPriority;
import com.empmgmt.enums.TaskStatus;

public class TaskDto {

    private String taskName;
    private String taskDescription;
    private Date dueDate;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;
    private UUID assignedTo;

    public TaskDto() {
    }

    public TaskDto(String taskName, String taskDescription, Date dueDate,
                   TaskPriority taskPriority, TaskStatus taskStatus, UUID assignedTo) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.dueDate = dueDate;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
        this.assignedTo = assignedTo;
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
}