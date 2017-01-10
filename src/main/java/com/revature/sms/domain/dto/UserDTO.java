package com.revature.sms.domain.dto;

import com.revature.sms.domain.UserRole;

public class UserDTO {
	
	private String username;
	private String firstName;
	private String lastName;
	private String batchType;
	private UserRole userRole;
	private String token;

	public UserDTO() {
		super();
	}
	
	public UserDTO(String username, String firstName, String lastName, UserRole userRole) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
	}

	public UserDTO(String username, String firstName, String lastName, UserRole userRole,
			String batchType) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
		this.batchType = batchType;
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

	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
