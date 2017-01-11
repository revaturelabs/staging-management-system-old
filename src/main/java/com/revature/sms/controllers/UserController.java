package com.revature.sms.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	private UserRepo userRepo;

	@Autowired
	private TokenRepo tokenRepo;

	private String role;

	/**
	 * To create user
	 * 
	 * @param token
	 * @param userDTO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object createUser(@RequestHeader(value = "Authorization") String token,
			@RequestBody UserDTO userDTO) {

		try {
			// validate token and create user
			if (isValid(token) && isAuthorized(role)) {

				User user = getUser(userDTO);
				user = userRepo.save(user);
				return new ResponseEntity<User>(user, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized"),
						HttpStatus.UNAUTHORIZED);

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
	 * @param token
	 * @param userDTO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object updateUser(@RequestHeader(value = "Authorization") String token,
			@RequestBody UserDTO userDTO) {

		try {
			// validate token and update user info
			if (isValid(token)) {
				User oldUser = updateValidation(userDTO);
				oldUser = userRepo.save(oldUser);
				return new ResponseEntity<User>(oldUser, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized"),
						HttpStatus.UNAUTHORIZED);

	
	

			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while updating user info", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while updating user info."), HttpStatus.NOT_FOUND);
		}

	}

	/**

	 * To retrieve user
	 * 
	 * @param token
	 * @param userDTO
	 * @return
	 */
	@RequestMapping(value = "/{username}", method = {
			RequestMethod.GET }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object retrieveUser(@RequestHeader(value = "Authorization") String token,
			@PathVariable String username) {

		try {
			// validate token and retrieve associate info
			if (isValid(token)) {
				User user = userRepo.findByUsername(username);
				return new ResponseEntity<User>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized"),
						HttpStatus.UNAUTHORIZED);

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
	 * @param token
	 * @param userDTO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object retrieveAll(@RequestHeader(value = "Authorization") String token) {

		try {
			// validate token and retrieve all associates info
			if (isValid(token) && (role != "associate")) {
				List<User> user = userRepo.findAll();
				return new ResponseEntity<List<User>>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized"),
						HttpStatus.UNAUTHORIZED);

	

			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while retrieving all associates info", e);
			return new ResponseEntity<ResponseErrorEntity>(

					new ResponseErrorEntity("Problem occurred while retrieving all associates info."),
					HttpStatus.NOT_FOUND);

		}

	}

	/**

	 * To delete user
	 * 
	 * @param token
	 * @param userDTO
	 * @return
	 */
	@RequestMapping(value = "/{username}", method = {
			RequestMethod.DELETE, }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object deleteUser(@RequestHeader(value = "Authorization") String token,
			@PathVariable String username) {

		try {
			// validate token and delete associate
			if (isValid(token) && isAuthorized(role)) {
				long result = userRepo.deleteByUsername(username);
				return new ResponseEntity<Long>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized"),
						HttpStatus.UNAUTHORIZED);

			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("User not found", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while deleting user."), HttpStatus.NOT_FOUND);
		}

	}

	/**

	 * To validate the token from the request and set the user role
	 * 
	 * @param tokenDTO
	 * @return
	 */
	public boolean isValid(String tokenDTO) {
		boolean valid = false;

		Token token = tokenRepo.findByauthToken(tokenDTO);
		if (token != null) {
			role = token.getUser().getUserRole().getName();
			valid = true;
		}
		return valid;
	}
	/**
	 * to verify superAdmin role
	 * @param role
	 * @return
	 */

	public boolean isAuthorized(String role) {
		boolean authorized = false;
		if ((role.equalsIgnoreCase("superAdmin"))) {
			authorized = true;
		}

		return authorized;
	}

	/**
	 * To transform UserInformationChangeDTO object into an User object
	 * 
	 * @param userDTO
	 * @return
	 */

	public User getUser(UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
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