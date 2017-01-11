package com.revature.sms.domain.dto;

/**
 * 
 * Takes in input username and password in attempt to login to the system.
 *
 */
public class LoginAttempt {
	
	/**
	 * Input username
	 */
	private String username; 
	
	/**
	 * Input password
	 */
	private String inputPass;
	
	/**
	 * Default constructor for LoginAttempt.
	 */
	public LoginAttempt() {
		super();
	}
	
	/**
	 * Parameterized constructor that takes in a username and password and attempts to login.
	 * @param username Input username
	 * @param inputPass Input password
	 */
	public LoginAttempt(String username, String inputPass) {
		super();
		this.username = username;
		this.inputPass = inputPass;
	}
	
	/**
	 * Get method for username.
	 * @return username String value for the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set method for the username.
	 * @param username Input username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get method for the input password
	 * @return password String value for the password
	 */
	public String getInputPass() {
		return inputPass;
	}

	/**
	 * Set method for the input password
	 * @param inputPass Input password
	 */
	public void setInputPass(String inputPass) {
		this.inputPass = inputPass;
	}
}
