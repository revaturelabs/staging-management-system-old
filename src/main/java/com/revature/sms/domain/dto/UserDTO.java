package com.revature.sms.domain.dto;


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
	private boolean verified;
	
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
			BatchType batchType) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword=password;
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
	
	public boolean getVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	

}
