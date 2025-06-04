package com.empmgmt.model;

import java.util.List;
import java.util.UUID;
import com.empmgmt.enums.UserRole;
import jakarta.persistence.*;

@Entity
public class Users {
    
    @Id
    private UUID empId;
    
    @Column(nullable=false)
    private String name;
    
    @Column(nullable=false, unique=true)
    private String emailId;

    @Column(nullable=false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private UserRole role;

    @OneToMany(mappedBy = "assignedEmployee", cascade = CascadeType.ALL)
    private List<Task> assignedTasks;

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

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the role
	 */
	public UserRole getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	/**
	 * @return the assignedTasks
	 */
	public List<Task> getAssignedTasks() {
		return assignedTasks;
	}

	/**
	 * @param assignedTasks the assignedTasks to set
	 */
	public void setAssignedTasks(List<Task> assignedTasks) {
		this.assignedTasks = assignedTasks;
	}

    
}
