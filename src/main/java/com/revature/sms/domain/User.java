package com.revature.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "userSeq", sequenceName = "USER_SEQ")
	@GeneratedValue(generator = "userSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	@Column(name = "EMAIL", unique=true, nullable=false)
	private String email;
	
	@Column(name = "FIRST_NAME", nullable=false)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable=false)
	private String lastName;
	
	@ManyToOne
	@JoinColumn(name="user_role", nullable=false)
	private UserRole userRole;
	
	@Column(name = "HASHED_PASSWORD", nullable=false)
	private String hashedPassword;
	
	@Column(name = "BATCH_TYPE", nullable=false)
	private String batchType;

	public User() {
		super();
	}

	public User(String email, String firstName, String lastName, UserRole userRole, String hashedPassword) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
		this.hashedPassword = hashedPassword;
	}

	public User(String email, String firstName, String lastName, UserRole userRole, String hashedPassword,
			String batchType) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
		this.hashedPassword = hashedPassword;
		this.batchType = batchType;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	@Override
	public String toString() {
		return "User [ID=" + ID + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userRole=" + userRole + ", hashedPassword=" + hashedPassword + ", batchType=" + batchType + "]";
	}
}
