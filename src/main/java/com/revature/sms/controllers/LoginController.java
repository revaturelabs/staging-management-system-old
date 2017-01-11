package com.revature.sms.controllers;

<<<<<<< HEAD
import com.revature.sms.domain.dto.UserTokenDTO;
=======
import java.sql.Timestamp;
import java.util.List;

>>>>>>> 3e344ce9fecb25e8c32208a7482e0a4497c8022c
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
import com.revature.sms.domain.dao.TokenRepo;
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
<<<<<<< HEAD
=======
	/**
	 * Autowired AssociateAttendenceRepo object. Spring handles setting this up
	 * for actual use.
	 */
>>>>>>> 3e344ce9fecb25e8c32208a7482e0a4497c8022c
	@Autowired
	TokenRepo tokenRepo;

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
	public Object login(@RequestBody LoginAttempt in) {
		User u = ur.findByUsername(in.getUsername());
		try {
			if (u.getHashedPassword().equals(in.getInputPass())) {
				u.blankPassword();
				Token token = new Token(u);
				token=tokenRepo.save(token);
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
<<<<<<< HEAD
}
=======

	/**
	 * Marks an associate as present
	 * 
	 * @param username
	 *            User to be marked as present
	 */
	private void markPresent(String username) {
		User user = ur.findByUsername(username);
		Timestamp d = new Timestamp(new java.util.Date().getTime());
		List<AssociateAttendance> associateAttendanceList = user.getAttendance();

		if (!associateAttendanceList.isEmpty()) {

			for (AssociateAttendance aa : associateAttendanceList) {
				if (d.toString().equals(aa.getDate().toString())) {
					// Associate has checked in before and current day exists
					aa.setCheckedIn(true);
					aar.save(aa);
					return;
				}
			}
		}
		// Associate has not checked in before
		// or
		// Associate has checked in before but current day does not exist
		AssociateAttendance aa = new AssociateAttendance(d, true, false, "");

		List<AssociateAttendance> l = user.getAttendance();
		l.add(aa);
		user.setAttendance(l);

		ur.save(user);
	}

}
>>>>>>> 3e344ce9fecb25e8c32208a7482e0a4497c8022c
