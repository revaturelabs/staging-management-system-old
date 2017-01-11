package com.revature.sms.controllers;

<<<<<<< HEAD
import org.apache.log4j.Logger;
=======

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

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.Token;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.TokenRepo;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dto.ResponseErrorEntity;
import com.revature.sms.domain.dto.UserDTO;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	TokenRepo tokenRepo;

	/**
	 * To create user 
	 * 
	 * @param UserDTO
	 * @return
	 */
	@RequestMapping(value = "/createUser", method = { RequestMethod.POST,
			RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object createUser(@RequestBody UserDTO UserDTO) {

		try {
			// validate token and create user
			if (isValid(UserDTO.getToken())) {
				User user=getUser(UserDTO);
				user=userRepo.save(user);
				return new ResponseEntity<User>(user, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Invalid token"),
						HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while creating user", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while creating user."), HttpStatus.SERVICE_UNAVAILABLE);
		}

	}
	
	
	/**
	 * To update user info
	 * 
	 * @param UserDTO
	 * @return
	 */
	@RequestMapping(value = "/updateUser", method = { RequestMethod.POST,
			RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object updateUser(@RequestBody UserDTO userDTO) {

		try {
			// validate token and update user info
			if (isValid(userDTO.getToken())) {
				User oldUser=updateValidation(userDTO);
				userRepo.save(oldUser);
				return new ResponseEntity<User>(oldUser, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Invalid token"),
						HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while updating user info", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while updating user info."), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * To retrieve user by ID
	 * 
	 * @param UserDTO
	 * @return
	 */
	@RequestMapping(value = "/retrieveUser", method = { RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object retrieveUser(@RequestBody UserDTO userDTO) {

		try {
			// validate token and retrieve associate info
			if (isValid(userDTO.getToken())) {
				User user = userRepo.findByUsername(userDTO.getUsername());
				return new ResponseEntity<User>(user, HttpStatus.OK);
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
	 * To retrieve all user
	 * 
	 * @param UserDTO
	 * @return
	 */
	@RequestMapping(value = "/retrieveAll", method = { RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object retrieveAll(@RequestBody UserDTO userDTO) {

		try {
			// validate token and retrieve all associates info
			if (isValid(userDTO.getToken())) {
				List<User> user = userRepo.findAll();
				return new ResponseEntity<List<User>>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Invalid token"),
						HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while retrieving all associates info", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while retrieving all associates info."), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * To delete user by username
	 * 
	 * @param UserDTO
	 * @return
	 */
	@RequestMapping(value = "/deleteUser", method = { RequestMethod.POST,
			RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object deleteByUserName(@RequestBody UserDTO userDTO) {

		try {
			// validate token and delete associate
			if (isValid(userDTO.getToken())) {
				Long result=userRepo.deleteByUsername(userDTO.getUsername());
				return new ResponseEntity<Long>(result,HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Invalid token"),
						HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("User not found", e);
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
		 Token token = tokenRepo.findByauthToken(tokenDTO);
		 if (token !=null) {
		 valid = true;
		 }
		return valid;
	}
	/**
	 * To transform UserInformationChangeDTO object into an User object
	 * @param UserDTO
	 * @return
	 */
	
	public User getUser(UserDTO userDTO){
		User user = new User();
		String[] ignoreProperties = { "token" };
		BeanUtils.copyProperties(userDTO, user, ignoreProperties);
		return user;
	}
	
	/**
	 * To validate user to be updated
	 * @param userDTO
	 * @return
	 */
	
	public User updateValidation(UserDTO userDTO){
		User oldUser=userRepo.findByUsername(userDTO.getUsername());
		if(userDTO.getUserRole()!=null)
		{
			oldUser.setUserRole(userDTO.getUserRole());
		}
		if(userDTO.getBatchType()!=null)
		{
			oldUser.setBatchType(userDTO.getBatchType());
		}
		if(userDTO.getHashedPassword()!=null)
		{
			oldUser.setHashedPassword(userDTO.getHashedPassword());
		}
		if(userDTO.getFirstName()!=null)
		{
			oldUser.setFirstName(userDTO.getFirstName());
		}
		if(userDTO.getLastName()!=null)
		{
			oldUser.setLastName(userDTO.getLastName());
		}
		
		return oldUser;
		
	}

}
