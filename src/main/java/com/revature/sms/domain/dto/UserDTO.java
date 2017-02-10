package com.revature.sms.domain.dto;


import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.JobEvent;
import com.revature.sms.domain.MarketingStatus;
import com.revature.sms.domain.ProjectUser;
import com.revature.sms.domain.TechnicalSkills;
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
	private Timestamp graduationDate;
	private List<AssociateTask> tasks;
	private List<JobEvent> events;
	private MarketingStatus marketingStatus;
	private Set<TechnicalSkills> skill;
	private List<ProjectUser> project;
	
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
			BatchType batchType, Timestamp graduationDate, List<AssociateTask> tasks, List<JobEvent> events) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword=password;
		this.userRole = userRole;
		this.batchType = batchType;
		this.graduationDate = graduationDate;
		this.tasks = tasks;
		this.events = events;
	}
	
	public UserDTO(String username, String firstName, String lastName, String hashedPassword, BatchType batchType,
			UserRole userRole, Timestamp graduationDate, List<AssociateTask> tasks, 
			Set<TechnicalSkills> skill, MarketingStatus marketingStatus) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword = hashedPassword;
		this.batchType = batchType;
		this.userRole = userRole;
		this.graduationDate = graduationDate;
		this.tasks = tasks;
		this.marketingStatus = marketingStatus;
		this.skill = skill;
	}
	
	public UserDTO(String username, String firstName, String lastName, String hashedPassword, BatchType batchType,
			UserRole userRole, Timestamp graduationDate, List<AssociateTask> tasks, 
			Set<TechnicalSkills> skill, MarketingStatus marketingStatus, List<ProjectUser> project) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword = hashedPassword;
		this.batchType = batchType;
		this.userRole = userRole;
		this.graduationDate = graduationDate;
		this.tasks = tasks;
		this.marketingStatus = marketingStatus;
		this.skill = skill;
		this.project = project;
	}

	public List<ProjectUser> getProject() {
		return project;
	}

	public void setProject(List<ProjectUser> project) {
		this.project = project;
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

	public MarketingStatus getMarketingStatus() {
		return marketingStatus;
	}

	public void setMarketingStatus(MarketingStatus marketingStatus) {
		this.marketingStatus = marketingStatus;
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
	
	public Timestamp getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(Timestamp graduationDate) {
		this.graduationDate = graduationDate;
	}
	public List<AssociateTask> getTasks() {
		return tasks;
	}
	
	public void setTasks(List<AssociateTask> tasks) {
		this.tasks = tasks;
	}
	
	public List<JobEvent> getEvents() {
		return events;
	}
	
	public void setEvents(List<JobEvent> events) {
		this.events = events;
	}

	public Set<TechnicalSkills> getSkill() {
		return skill;
	}

	public void setSkill(Set<TechnicalSkills> skill) {
		this.skill = skill;
	}

}