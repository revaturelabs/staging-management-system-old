package com.revature.sms.controllers;

import static com.revature.sms.domain.User.hashPassword;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.ResponseErrorDTO;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dto.EmailPassObject;



@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	static UserRepo ur;
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createCurriculum(@RequestBody EmailPassObject in ) {
		try{
			User u =ur.findByEmail(in.getEmail());
			if(u.getHashedPassword().equals(hashPassword(in.getInputPass()))){
				u.blankPassword();
				u.setID(0);
				return new ResponseEntity<User>(u, HttpStatus.OK);
			}
			else{
				return new ResponseEntity<String>("Bad Password.", HttpStatus.NOT_FOUND);
			}
		}catch(NullPointerException e){
			return new ResponseEntity<String>("Bad Email.", HttpStatus.NOT_FOUND);
		}
		
	}

}
