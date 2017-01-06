package com.revature.sms.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	
	@Column(name = "HASHED_PASSWORD", nullable=false)
	private String hashedPassword;
	
	@Column(name = "BATCH_TYPE")
	private String batchType;
	
	@ManyToOne
	@JoinColumn(name="user_role")
	private UserRole userRole;

	public User(String username, String firstName, String lastName, UserRole userRole, String inputPassword) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
		this.hashedPassword = hashPassword(inputPassword);
	}

	public User(String username, String firstName, String lastName, UserRole userRole, String inputPassword,
			String batchType) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
		this.hashedPassword = hashPassword(inputPassword);
		this.batchType = batchType;
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

	public void setHashedPassword(String inputPassword) {
		this.hashedPassword = hashPassword(inputPassword);
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

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [ID=" + ID + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userRole=" + userRole + ", hashedPassword=" + hashedPassword + ", batchType=" + batchType + "]";
	}
	
	public static String hashPassword(String inputPassword){
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA"); 
			md.update(inputPassword.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
