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
@RequestMapping("/api/v1/login")
public class LoginController {

	static UserRepo ur;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object login(@RequestBody LoginAttempt in) {
		try {
			User u = ur.findByUsername(in.getUsername());
			if (u.getHashedPassword().equals(hashPassword(in.getInputPass()))) {
				u.blankPassword();
				u.setID(0);
				return new ResponseEntity<User>(u, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Invalid password.", HttpStatus.NOT_FOUND);
			}
		} catch (NullPointerException e) {
			return new ResponseEntity<String>("Username does not exist.", HttpStatus.NOT_FOUND);
		}

	}
}
