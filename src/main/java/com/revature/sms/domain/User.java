package com.revature.sms.domain;

import org.hibernate.annotations.Cascade;

import java.util.List;

import javax.persistence.*;

//This object tracks users on the application

@Entity
@Table(name="USERS")
public class User {
	
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "userSeq", sequenceName = "USER_SEQ")
	@GeneratedValue(generator = "userSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	@Column(name = "USERNAME", unique=true, nullable=false)
	private String username;
	
	@Column(name = "FIRST_NAME", nullable=false)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable=false)
	private String lastName;
	
	@Column(name = "HASHED_PASSWORD")
	private String hashedPassword;
	
	@Column(name = "BATCH_TYPE")
	private String batchType;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="ID")
	private List<AssociateAttendance> attendance;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="ID")
	private List<AssociateTask> tasks;

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

	@ManyToOne
	@JoinColumn(name="user_role")
	private UserRole userRole;

	public User() {
		super();
	}
	
	public User(String username, String firstName, String lastName, UserRole userRole, String hashedPassword) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
		this.hashedPassword = hashedPassword;
	}

	public User(String username, String firstName, String lastName, UserRole userRole, String hashedPassword,
			String batchType, List<AssociateAttendance> attendance, List<AssociateTask> tasks) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
		this.hashedPassword = hashedPassword;
		this.batchType = batchType;
		this.attendance = attendance;
		this.tasks = tasks;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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

	public void setHashedPassword(String hashPassword) {
		this.hashedPassword = hashPassword;
	}

	public void blankPassword(){
		this.hashedPassword="";
	}
	
	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	@Override
	public String toString() {
		return "User [ID=" + ID + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userRole=" + userRole + ", batchType=" + batchType + "]";
	}
	
}
