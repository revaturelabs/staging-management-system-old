package com.revature.sms.domain.dto;

import com.revature.sms.domain.Token;
import com.revature.sms.domain.User;
/**
 * Data Transfer Object for UserToken
 *
 */
public class UserTokenDTO {
	/**
	 * User object that represents the user matching the UserToken being transfered.
	 */
    private User user;
    /**
     * String that contains the authToken of this UserTokenDTO object.
     */
    private String authToken;
/**
 * Method that retrieves the authToken of this UserTokenDTO object
 * @return authToken String value that contains the actual Token of the object.
 */
    public String getToken() {
        return authToken;
    }
/**
 * Method to set the value of the authToken instance variable.
 * @param authToken String value that contains the actual token of this UserTokenDTO object.
 */
    public void setToken(String authToken) {
        this.authToken = authToken;
    }
/**
 * Method to retrieve the User object associated with the token.
 * @return User object related to this UserToken.
 */
    public User getUser() {

        return user;
    }
/**
 * Method to set the user of this UserTokenDTO object.
 * @param user User object to be set with this token.
 */
    public void setUser(User user) {
        this.user = user;
    }
}
