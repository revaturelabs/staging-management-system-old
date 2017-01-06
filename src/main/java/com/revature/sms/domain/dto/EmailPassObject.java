package com.revature.sms.domain.dto;

public class EmailPassObject {
	
	private String email;
	private String inputPass;
	
	public EmailPassObject(String email, String inputPass) {
		super();
		this.email = email;
		this.inputPass = inputPass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInputPass() {
		return inputPass;
	}

	public void setInputPass(String inputPass) {
		this.inputPass = inputPass;
	}
}
