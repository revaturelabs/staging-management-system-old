package com.revature.sms.controllers;

import static com.revature.sms.domain.User.hashPassword;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dto.LoginAttempt;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	static UserRepo ur;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createUser(@RequestBody LoginAttempt in) {
//		try {
//			User u = ur.findByUsername(in.getUsername());
//			if (u.getHashedPassword().equals(hashPassword(in.getInputPass()))) {
//				u.blankPassword();
//				u.setID(0);
//				return new ResponseEntity<User>(u, HttpStatus.OK);
//			} else {
//				return new ResponseEntity<String>("Invalid password.", HttpStatus.NOT_FOUND);
//			}
//		} catch (NullPointerException e) {
//			return new ResponseEntity<String>("Username does not exist.", HttpStatus.NOT_FOUND);
//		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.TRACE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createCurriculum(@RequestBody LoginAttempt in) {
		try {
			User u = ur.findByUsername(in.getUsername());	
			if (u.getHashedPassword().equals(hashPassword(in.getInputPass()))) {
				u.blankPassword();
				u.setID(0);
				return new ResponseEntity<User>(u, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Bad Password.", HttpStatus.NOT_FOUND);
			}
		} catch (NullPointerException e) {
			return new ResponseEntity<String>("Bad Email.", HttpStatus.NOT_FOUND);
		}

	}

}
