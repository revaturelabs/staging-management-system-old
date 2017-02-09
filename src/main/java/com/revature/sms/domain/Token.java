package com.revature.sms.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 
 * Object that represents the authorization Token. The token object will be used to track a 
 * user's session with an authentication token
 *
 */
@Entity
@Table(name="TOKEN")
public class Token {
	/**
	 * String value that represents the primary key of the table, authToken is used to 
	 * identify a specific Token object by it's unique value.
	 */
	@Id
	@Column(name="AUTH_TOKEN")
	private String authToken;
	/**
	 * User value that represents the User object that's attached to this Token object.
	 * A single User can have multiple Token objects attached to it. 
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="AUTH_USER", nullable=false)
	private User user;
	/**
	 * Timestamp object that represents the date that the token was last updated. This
	 * happens every time the user does something in the current session.
	 */
	@Column(name="LAST_ACCESS")
	private Timestamp mostRecentAccess;
/**
 * Null args constructor for a token, doesn't initialize any of the values.
 */
	public Token() {
		super();
	}
	/**
	 * Constructor that creates the Token object and initializes the User Instance variable
	 * based on the supplied user in the constructor. Also initializes the mostRecentAccess
	 * instance variable to the value of the current date and time that the object is created.
	 * @param user User object that's attached to this token.
	 */
	public Token(User user) {
		super();
		
		this.user = user;
		this.mostRecentAccess = new Timestamp(new Date().getTime());
	
		this.authToken = getRandomAuthToken();
	}
	/**
	 * Method that creates a random string for use as the authToken. Used in the
	 * constructor to dynamically create the randomAuthToken.
	 * @return s.toString() String value of the StringBuilder object that was used to generate
	 * the random token.
	 */
	private static String getRandomAuthToken(){
		StringBuilder s = new StringBuilder("");
		for(int i=0; i<40; i++){
			s.append((char)(Math.random()*70+48));
		}
		return s.toString();
	}
/**
 * Method to get the value of the mostRecentAccess variable for the Token object.
 * @return mostRecentAccess String value that represents the last time the token was updated.
 */
	public Timestamp getMostRecentAccess() {
		return mostRecentAccess;
	}
/**
 * Method to set the mostRecentAccess variable of this Token object based on the supplied
 * TimeStamp object.
 * @param mostRecentAccess TimeStamp object that represents the time the Token is updated.
 */
	public void setMostRecentAccess(Timestamp mostRecentAccess) {
		this.mostRecentAccess = mostRecentAccess;
	}
/**
 * Method that returns the authToken variable of the Token object.
 * @return authToken String value that represents the token String of this Token object.
 */
	public String getAuthToken() {
		return authToken;
	}
/**
 * Method that returns the User variable of the Token object.
 * @return user User variable that is attatched to this Token.
 */
	public User getUser() {
		return user;
	}
/**
 * Method that returns the string representation of the Token object.
 */
	@Override
	public String toString() {
		return "Token [authToken=" + authToken + ", user=" + user + ", mostRecentAccess=" + mostRecentAccess + "]";
	}
	
	
	
}
