package com.revature.sms.controllers;

import com.revature.sms.domain.dto.UserTokenDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.User;
import com.revature.sms.domain.Token;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dto.LoginAttempt;
import com.revature.sms.domain.dto.ResponseErrorEntity;
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
 * Method that's called via Http Post method. Used for submitting a login attempt when trying to login.
 * @param in - LoginAttempt object that contains the user name and password of the user trying to login.
 * @return ResponseEntity object containing user information if the login is successful,
 * otherwise it returns ResponseEntity with an error message if login fails. 
 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object login(@RequestBody LoginAttempt in) {
		User u = ur.findByUsername(in.getUsername());
		try {
			if (u.getHashedPassword().equals(in.getInputPass())) {
				u.blankPassword();
				u.setID(0);
				Token token = new Token(u);
				UserTokenDTO userToken = new UserTokenDTO();
				userToken.setUser(u);
				userToken.setToken(token.getAuthToken());
				return new ResponseEntity<UserTokenDTO>(userToken, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Invalid password."), HttpStatus.NOT_FOUND);
			}
		} catch (NullPointerException e) {
			Logger.getRootLogger().debug("Bad username", e);
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Username does not exist."), HttpStatus.NOT_FOUND);
		}
	}
}
