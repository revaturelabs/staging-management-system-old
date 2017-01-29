package com.revature.sms.domain.dto;

import java.util.List;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.Technical_Skills;

public class NewAssociateDTO {

	private String username;
	private String firstName;
	private String lastName;
	private String hashedPassword;
	private BatchType batchType;
	private List<AssociateAttendance> attendance;
	private List<AssociateTask> tasks;
	private List<Technical_Skills> skill;
	
	public NewAssociateDTO() {
		super();
	}

	public NewAssociateDTO(String username, String firstName, String lastName, String hashedPassword,
			BatchType batchType, List<AssociateAttendance> attendance, List<AssociateTask> tasks) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword = hashedPassword;
		this.batchType = batchType;
		this.attendance = attendance;
		this.tasks = tasks;
	}
	
	public NewAssociateDTO(String username, String firstName, String lastName, String hashedPassword,
			BatchType batchType, List<AssociateAttendance> attendance, List<AssociateTask> tasks,
			List<Technical_Skills> skill) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword = hashedPassword;
		this.batchType = batchType;
		this.attendance = attendance;
		this.tasks = tasks;
		this.skill = skill;
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

	public List<AssociateAttendance> getAttendance() {
		return attendance;
	}

	public void setAttendance(List<AssociateAttendance> attendance) {
		this.attendance = attendance;
	}

	public List<AssociateTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<AssociateTask> tasks) {
		this.tasks = tasks;
	}

	public List<Technical_Skills> getSkill() {
		return skill;
	}

	public void setSkill(List<Technical_Skills> skill) {
		this.skill = skill;
	}
	

	

}
