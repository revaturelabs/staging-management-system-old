package com.revature.sms.controllers;

import java.sql.Date;
import java.util.List;
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

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.domain.Token;
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
		User u = ur.findByUsername(in.getUsername());
		try {
			if (u.getHashedPassword().equals(in.getInputPass())) {
				// Successful login
				//u.blankPassword();
				//u.setID(0);

				if ("associate".equals(u.getUserRole().getName())) {
					// if associate mark attendance as present
					markPresent(u);
				}

				Token token = new Token(u);
				UserTokenDTO userToken = new UserTokenDTO();
				userToken.setUser(u);
				userToken.setToken(token.getAuthToken());
				return new ResponseEntity<UserTokenDTO>(userToken, HttpStatus.OK);
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
	 * Marks an associate as present
	 * @param u User to be marked as present
	 */
	private void markPresent(User user) {
		Date d = new Date(new java.util.Date().getTime());
		List<AssociateAttendance> associateAttendanceList = aar.findByAssociate(user);
		if (!associateAttendanceList.isEmpty()) {
			System.out.println("\n" + associateAttendanceList.size() + " : size of associateAttendanceList on " + d);
			for (AssociateAttendance aa : associateAttendanceList) {
				System.out.println(d.toString() + " : " + aa.getDate().toString());
				if (d.toString().equals(aa.getDate().toString())) {
					aa.setCheckedIn(true);
					aar.save(aa);
					System.out.println("\n" + aa.getAssociate().getUsername() + " marked as present");
					break;
				}
			}
		}
		// Something is wrong here
		else{
			//create an AssociateAttendance row
			AssociateAttendance aa = new AssociateAttendance();

			aa.setAssociate(user);
			aa.setCheckedIn(true);
			aa.setDate(d);
			aa.setVerified(false);
			aa.setID((int) (Math.random()*1000));

			
/*			List<AssociateAttendance> l = user.getAttendance();
			l.add(aa);
			user.setAttendance(l);
			
			System.out.println("update user");
			ur.save(user);*/

			
			System.out.println("update aa");
			aar.save(aa); 


		}
	}

}
