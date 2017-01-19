package com.revature.sms.domain.dto;


import java.sql.Timestamp;
import java.util.List;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.UserRole;

public class UserDTO {
	
	private String username;
	private String firstName;
	private String lastName;
	private String hashedPassword;
	private BatchType batchType;
	private UserRole userRole;
	private String oldPassword;
	private String newPassword;
	private List<AssociateAttendance> attendance;
	private List<AssociateTask> associateTask;
	private Timestamp graduationDate;
	
	
	public UserDTO() {
		super();
	}
	
	public UserDTO(String username, String firstName, String lastName,String password, UserRole userRole) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword=password;
		this.userRole = userRole;
	}

	public UserDTO(String username, String firstName, String lastName,String password, UserRole userRole,
			BatchType batchType, Timestamp graduationDate) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword=password;
		this.userRole = userRole;
		this.batchType = batchType;
		this.graduationDate = graduationDate;
		//this.jobEvent=jobEvent;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public BatchType getBatchType() {
		return batchType;
	}

	public void setBatchType(BatchType batchType) {
		this.batchType = batchType;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public List<AssociateAttendance> getAttendance() {
		return attendance;
	}

	public void setAttendance(List<AssociateAttendance> attendance) {
		this.attendance = attendance;
	}
	
	public List<AssociateTask> getAssociateTask() {
		return associateTask;
	}

	public void setAssociateTask(List<AssociateTask> associateTask) {
		this.associateTask = associateTask;
	}
	
	public Timestamp getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(Timestamp graduationDate) {
		this.graduationDate = graduationDate;
	}
	
}
