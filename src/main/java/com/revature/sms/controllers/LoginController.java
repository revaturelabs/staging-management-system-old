package com.revature.sms.controllers;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dto.LoginAttempt;
import com.revature.sms.domain.dto.ResponseErrorEntity;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

	@Autowired
	UserRepo ur;

	@Autowired
	AssociateAttendanceRepo aar;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object login(@RequestBody LoginAttempt in) {
		//System.out.println(in.getUsername() + " " + in.getInputPass());

		User u = ur.findByUsername(in.getUsername());
		//System.out.println(u);
		try {
			if (u.getHashedPassword().equals(in.getInputPass())) {
				// Successful login
				u.blankPassword();
				u.setID(0);

				if ("associate".equals(u.getUserRole().getName())) {
					// if associate mark attendance as present
					markPresent(u);
				}

				return new ResponseEntity<User>(u, HttpStatus.OK);
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
	
	private void markPresent(User u){
		List<AssociateAttendance> associateAttendanceList = aar.findByDate(new Date(new java.util.Date().getTime()));
		//if (associateAttendanceList.size() <= 0) {} 

		for (AssociateAttendance aa : associateAttendanceList) {
			if(aa.getAssociate().getID() == u.getID()){
				aa.setCheckedIn(true);
				break;
			}
		}

		aar.save(associateAttendanceList);
		//System.out.println(u + "logged in");
	}
	
}
