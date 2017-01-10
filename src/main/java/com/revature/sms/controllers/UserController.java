package com.revature.sms.controllers;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dto.ResponseErrorEntity;
import com.revature.sms.domain.dto.UserDTO;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	@Autowired
	UserRepo userRepo;

	@Autowired
	AssociateAttendanceRepo attendanceRepo;

	
	/**
	 * To find user by first name
	 * 
	 * @param userDTO
	 * @return
	 */
	@RequestMapping(value = "/findByFirstName", method = { RequestMethod.POST,
			RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object findUserByFirstName(@RequestBody UserDTO userDTO) {

		try {
			// validate token and retrieve associate info

			if (isValid(userDTO.getToken())) {
				List<User> user = userRepo.findByFirstName(userDTO.getFirstName());
				return new ResponseEntity<List<User>>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Invalid token"),
						HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while retrieving associate info", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while retrieving associate info."), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * To find user by batch type
	 * 
	 * @param userDTO
	 * @return
	 */
	@RequestMapping(value = "/findByBatchType", method = { RequestMethod.POST,
			RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object findByBatchType(@RequestBody UserDTO userDTO) {

		try {
			// validate token and retrieve associate info

			if (isValid(userDTO.getToken())) {
				List<User> user = userRepo.findByBatchType(userDTO.getBatchType());
				return new ResponseEntity<List<User>>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Invalid token"),
						HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while retrieving associate info", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while retrieving associate info."), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * To delete user by username
	 * 
	 * @param userDTO
	 * @return
	 */
	@RequestMapping(value = "/deleteByUsername", method = { RequestMethod.POST,
			RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object deleteByUserName(@RequestBody UserDTO userDTO) {

		try {
			// validate token and delete associate

			if (isValid(userDTO.getToken())) {
				userRepo.deleteByUsername(userDTO.getUsername());
				return new ResponseEntity<User>(HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Invalid token"),
						HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("Bad username", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while deleting user."), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * To validate the token from the request
	 * 
	 * @param tokenDTO
	 * @return
	 */
	public boolean isValid(String tokenDTO) {
		boolean valid = false;
		// String token = tokenRepo.findByToken(tokenDTO);
		// if (token == tokenDTO) {
		// valid = true;
		// }
		return valid;
	}

}