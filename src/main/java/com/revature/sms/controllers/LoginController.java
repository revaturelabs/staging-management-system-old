package com.revature.sms.controllers;

import java.sql.Timestamp;
import java.util.List;

import com.revature.sms.domain.dto.bc;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.Token;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.domain.dao.TokenRepo;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dto.LoginAttemptDTO;
import com.revature.sms.domain.dto.ResponseErrorEntity;
import com.revature.sms.domain.dto.UserDTO;
import com.revature.sms.domain.dto.bc;

/**
 * Server-side controller to handle logging into the application.
 *
 */
@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

	/**
	 * Autowired UserRepo object. Spring handles setting this up for actual use.
	 */
	@Autowired
	UserRepo ur;

	/**
	 * Autowired TokenRepo object. Spring handles setting this up for actual
	 * use.
	 */
	@Autowired
	TokenRepo tr;

	/**
	 * Autowired AssociateAttendenceRepo object. Spring handles setting this up
	 * for actual use.
	 */
	@Autowired
	AssociateAttendanceRepo aar;

	/**
	 * Method that's called via Http Post method. Used for submitting a login
	 * attempt when trying to login.
	 * 
	 * @param in
	 *            LoginAttempt object that contains the user name and password
	 *            of the user trying to login.
	 * @return ResponseEntity object containing user information if the login is
	 *         successful, otherwise it returns ResponseEntity with an error
	 *         message if login fails.
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object login(@RequestBody LoginAttemptDTO in) {
		User u = ur.findByUsername(in.getUsername());
		try {
			if (u.getHashedPassword().equals(in.getInputPass())) {
				// Successful login

				/*if ("associate".equals(u.getUserRole().getName())) {
					// if associate mark attendance as present
					markPresent(u.getUsername());
				}*/


				Token token = new Token(u);
				tr.save(token);
				u.blankPassword();

				/*
				 * UserTokenDTO userToken = new UserTokenDTO();
				 * userToken.setUser(u);
				 * userToken.setToken(token.getAuthToken());
				 */
				return new ResponseEntity<Token>(token, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Invalid password."),
						HttpStatus.NOT_FOUND);
			}
		} catch (NullPointerException e) {
			Logger.getRootLogger().debug("Bad username", e);
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Username does not exist."),
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Method to determine if password update is required, based on if the hashed username is equal to the hashed password.
	 * @param token String value of authorization token.
	 * @param username String value of logged in user's username.
	 * @return ResponseEntity object containing a Boolean object with value of true if a password change is required, false if it is not.
	 */
	@RequestMapping(value="/checkpass" ,method = RequestMethod.POST)
	public @ResponseBody Object needUpdatePassword(@RequestHeader(value = "Authorization") String token, @RequestBody String username) {
		//check authorization token
		User user = ur.findByUsername(username);
		if (user != null) {
			if(isValid(token, username)){
				// hash username
				String usernameHash = User.hashPassword(username);
				// compare hashed username to hashed password
				return new ResponseEntity<bc>(new bc(usernameHash.equals(user.getHashedPassword())), HttpStatus.OK);
			}
			else{
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User not authorized."), HttpStatus.FORBIDDEN);
			}
		}

		return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User not found."), HttpStatus.NOT_FOUND);

	}

	/**
	 * @param token String value of authorization token.
	 * @param username String value of logged in user's username.
	 * @return ResponseEntity object containing a Boolean object with value of true if a password change is required, false if it is not.
	 */
	@RequestMapping(value="/cookieLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object cookieLogin(@RequestHeader(value = "Authorization") String token, @RequestBody String username) {
		Token masterToken = tr.findByAuthToken(token);
		try {
			masterToken.getUser().blankPassword();
			masterToken.getUser().setID(0);
			if (masterToken.getUser().getUsername().equals(username)) {
				return new ResponseEntity<Token>(masterToken, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Cookie username/token do not match."), HttpStatus.NOT_FOUND);
			}
		} catch (NullPointerException e) {
			Logger.getRootLogger().debug("Inactive token", e);
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Stored token is inactive."), HttpStatus.UNAUTHORIZED);
		}
	}




	/**
	 * To update user info
	 * 
	 * @param token
	 *            Authorization token to make sure the user of the method has
	 *            appropriate access to run the command
	 * @param userDTO
	 *            User Data Transfer Object that carries only the new
	 *            information to be updated
	 * @return ResponseEntity object containing the updated user object if it
	 *         succeeds, or an error if there was a problem while updating the
	 *         user password
	 */
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object updatePassword(@RequestHeader(value = "Authorization") String token,
			@RequestBody UserDTO userDTO) {

		try {
			// validate token and update user password
			if (isValid(token, userDTO.getUsername())) {
				// verify user
				User oldUser = ur.findByUsername(userDTO.getUsername());
				if (oldUser.getHashedPassword().equals(userDTO.getOldPassword())) {
					oldUser.setHashedPassword(userDTO.getNewPassword());
					User newUser = ur.save(oldUser);
					newUser.blankPassword();
					newUser.setID(0);
					return new ResponseEntity<User>(newUser, HttpStatus.OK);
				} else {
					return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Password mismatch"),
							HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while updating user password", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while updating user password."), HttpStatus.NOT_FOUND);
		}

	}

	
	/**
	 * To allow superadmin update user info
	 * 
	 * @param token
	 *            Authorization token to make sure the user of the method has
	 *            appropriate access to run the command
	 * @param userDTO
	 *            User Data Transfer Object that carries only the new
	 *            information to be updated
	 * @return ResponseEntity object containing the updated user object if it
	 *         succeeds, or an error if there was a problem while updating the
	 *         user password
	 */
	@RequestMapping(value="/resetPass", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public Object resetPassword(@RequestHeader(value = "Authorization") String token,
			@RequestBody UserDTO userDTO) {

		try {
			// validate token and update user password
			if (isValid(token, userDTO.getUsername())) {
				
				// verify user
				User oldUser = ur.findByUsername(userDTO.getUsername());
				
				// set the database users password to the updated password from the working user
				oldUser.setHashedPassword( User.hashPassword( oldUser.getUsername() ) );
				
				
				User newUser = ur.save(oldUser);
				
				// set user password to blank because its info you don't want sent over
				newUser.blankPassword();
				
				// set user id to 0 because its info you don't want sent over
				newUser.setID(0);
				
				// return good job
				return new ResponseEntity<User>(newUser, HttpStatus.OK);
				
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while updating user password", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while updating user password."), HttpStatus.NOT_FOUND);
		}

	}	
	
//????????????????????????????????????????????????????????????????????????????????????????????????????????????????????	
	
	
	/**
	 * 
	 * To validate the token from the request and make sure username belongs to
	 * the current user who is changing the password
	 * 
	 * @param tokenString
	 *            String containing the actual auth token
	 * @param usernameString
	 *            String containing the actual username
	 * @return Boolean, true if token is valid, false if not
	 */
	public boolean isValid(String tokenString, String usernameString) {
		boolean valid = false;
		Token token = tr.findByAuthToken(tokenString);
		String superadminString = "superadmin";
		
		if (token != null) {
			if ((usernameString.equals(token.getUser().getUsername()))  || (token.getUser().getUserRole().getName().equalsIgnoreCase(superadminString)) ) {
				valid = true;
			} 
		}
		return valid;
	}
	
}