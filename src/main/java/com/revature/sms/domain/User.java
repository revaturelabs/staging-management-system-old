package com.revature.sms.domain;

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
	
	@ManyToOne
	@JoinColumn(name="BATCH_TYPE")
	private BatchType batchType;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="associate")
	private List<AssociateAttendance> attendance;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="associate")
	private List<AssociateTask> tasks;

	@ManyToOne
	@JoinColumn(name="user_role")
	private UserRole userRole;

	public User() {
		super();
	}
	
	//constructor for associate
	public User(String username, String firstName, String lastName, String hashedPassword, BatchType batchType,
			List<AssociateAttendance> attendance, List<AssociateTask> tasks, UserRole userRole) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword = hashedPassword;
		this.batchType = batchType;
		this.attendance = attendance;
		this.tasks = tasks;
		this.userRole = userRole;
	}
	
	//constructor for non-associate
	public User(String username, String firstName, String lastName, String hashedPassword, UserRole userRole) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword = hashedPassword;
		this.userRole = userRole;
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

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public void blankPassword(){
		String empty="";
		this.hashedPassword=empty;
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

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "User [ID=" + ID + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", hashedPassword=" + hashedPassword + ", batchType=" + batchType + ", attendance=" + attendance
				+ ", tasks=" + tasks + ", userRole=" + userRole + "]";
	}

	
	
}
