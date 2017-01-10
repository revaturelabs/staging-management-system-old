package com.revature.sms.controllers;

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
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dao.UserRoleRepo;
import com.revature.sms.domain.dto.NewAssociateDTO;
import com.revature.sms.domain.dto.ResponseErrorEntity;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	UserRepo ur;
	
	@Autowired
	UserRoleRepo urr;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object createNewAssociate(@RequestBody NewAssociateDTO in) {
		try {
			User user=ur.save(new User(in.getUsername(), in.getFirstName(), in.getLastName(), in.getHashedPassword(), in.getBatchType(), in.getAttendance(), in.getTasks(), urr.findByName("associate")));
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}catch(Exception e){
			Logger.getRootLogger().debug("There was an error.", e);
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("There was an error."), HttpStatus.NOT_FOUND);
		}
	}
}
