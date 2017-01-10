package com.revature.sms.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//The token object will be used to track a user's session with an authentication token

@Entity
@Table(name="TOKEN")
public class Token {
	
	@Id
	@Column(name="AUTH_TOKEN")
	private String authToken;
	
	@ManyToOne
	@JoinColumn(name="AUTH_USER", nullable=false)
	private User user;
	
	@Column(name="LAST_ACCESS")
	private Timestamp mostRecentAccess;

	public Token() {
		super();
	}
	
	public Token(User user) {
		super();
		
		this.user = user;
		this.mostRecentAccess = new Timestamp(new Date().getTime());
	
		this.authToken = getRandomAuthToken();
	}
	
	private static String getRandomAuthToken(){
		StringBuilder s = new StringBuilder("");
		for(int i=0; i<40; i++){
			s.append((char)(Math.random()*70+48));
		}
		return s.toString();
	}

	public Timestamp getMostRecentAccess() {
		return mostRecentAccess;
	}

	public void setMostRecentAccess(Timestamp mostRecentAccess) {
		this.mostRecentAccess = mostRecentAccess;
	}

	public String getAuthToken() {
		return authToken;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Token [authToken=" + authToken + ", user=" + user + ", mostRecentAccess=" + mostRecentAccess + "]";
	}
	
	
	
}
