package com.revature.sms.domain.dto;

public class LoginAttempt {
	
	private String username;
	private String inputPass;
	
	public LoginAttempt(String username, String inputPass) {
		super();
		this.username = username;
		this.inputPass = inputPass;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInputPass() {
		return inputPass;
	}

	public void setInputPass(String inputPass) {
		this.inputPass = inputPass;
	}
}
