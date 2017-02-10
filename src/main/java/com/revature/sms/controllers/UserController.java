package com.revature.sms.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.revature.sms.domain.TechnicalSkills;
import com.revature.sms.domain.Token;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.domain.dao.TokenRepo;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dto.ResponseErrorEntity;
import com.revature.sms.domain.dto.UserDTO;

/**
 * Server-side controller to handle User CRUD operations (Create, Retrieve,
 * Update, Delete)
 *
 */

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired

	private UserRepo userRepo;

	@Autowired
	private TokenRepo tokenRepo;

	/**
	 * Autowired AssociateAttendenceRepo object. Spring handles setting this up
	 * for actual use.
	 */
	@Autowired
	AssociateAttendanceRepo aar;

	private String role;

	/**
	 * To create user
	 * 
	 * @param token
	 *            Authorization token to make sure the user of the method has
	 *            appropriate access to run the command
	 * @param userDTO
	 *            User Data Transfer Object that carries user information
	 * @return ResponseEntity object containing the newly created user object if
	 *         it succeeds, or an error if there was a problem while creating
	 *         the user
	 */
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object createUser(@RequestHeader(value = "Authorization") String token,
			@RequestBody UserDTO userDTO) {

		
		try {
			// validate token and create user
			if (isValid(token) && isSuperAdmin(role)) {
				
				User user = getUser(userDTO);
				user = userRepo.save(user);
				user.blankPassword();
				user.setID(0);
				return new ResponseEntity<User>(user, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized"),
						HttpStatus.UNAUTHORIZED);

			}
		} catch (DataIntegrityViolationException dive) {
			Logger.getRootLogger().debug("Username already exists.", dive);
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Username already exists."),
					HttpStatus.CONFLICT);
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while creating user", e);
			e.printStackTrace();
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while creating user."), HttpStatus.SERVICE_UNAVAILABLE);
		}

	}

	/**
	 * To update user info
	 * 
	 * @param token
	 *            Authorization token to make sure the user of the method has
	 *            appropriate access to run the command
	 * @param userDTO
	 *            User Data Transfer Object that carries only the new
	 *            information to be updated
	 * @return ResponseEntity object containing the updated user object if it
	 *         succeeds, or an error if there was a problem while updating the
	 *         user
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object updateUser(@RequestHeader(value = "Authorization") String token,
			@RequestBody UserDTO userDTO) {

		try {
			// validate token and update user info
			if (isValid(token)) {
				
				User oldUser = (User) updateValidation(userDTO);
				User newUser = userRepo.save(oldUser);
				newUser.blankPassword();
				newUser.setID(0);
				return new ResponseEntity<User>(newUser, HttpStatus.OK);
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
	 * 
	 * To retrieve a given user based on their username
	 * 
	 * @param token
	 *            Authorization token to make sure the user of the method has
	 *            appropriate access to run the command
	 * @return ResponseEntity object containing the requested user object if it
	 *         succeeds, or an error if there was a problem while retrieving the
	 *         user
	 */
	@RequestMapping(value = "/{username}", method = {
			RequestMethod.GET }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object retrieveUser(@RequestHeader(value = "Authorization") String token,
			@PathVariable String username) {

		try {
			// validate token and retrieve associate info
			if (isValid(token)) {
				User user = userRepo.findByUsername(username);
				user.blankPassword();
				user.setID(0);
				return new ResponseEntity<User>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized"),
						HttpStatus.UNAUTHORIZED);

			}
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while retrieving user info", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while retrieving user info."), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * To retrieve all users
	 *
	 * @param token
	 *            Authorization token to make sure the user of the method has
	 *            appropriate access to run the command
	 * @return ResponseEntity object containing a list of the user objects if it
	 *         succeeds, or an error if there was a problem while retrieving the
	 *         users
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object retrieveAll(@RequestHeader(value = "Authorization") String authToken) {
		try {
			// validate token and retrieve all associates info
			Token userToken = tokenRepo.findByAuthToken(authToken);
			if (userToken == null) {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("AuthToken invalid."),
						HttpStatus.NOT_FOUND);
			} else if (("superadmin".equalsIgnoreCase(userToken.getUser().getUserRole().getName())
					|| "admin".equalsIgnoreCase(userToken.getUser().getUserRole().getName()))) {
				List<User> users = userRepo.findAll();
				for (User user : users) {
					user.blankPassword();
					user.setID(0);
				}
				return new ResponseEntity<List<User>>(users, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(
						new ResponseErrorEntity("User is unauthorized to access information."),
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
	 * 
	 * To delete a given user based on their username
	 * 
	 * @param token
	 *            Authorization token to make sure the user of the method has
	 *            appropriate access to run the command
	 * @return ResponseEntity object containing the ID of the user that was
	 *         deleted if it succeeds, or an error if there was a problem while
	 *         retrieving the users
	 */
	@RequestMapping(value = "/{username}", method = {
			RequestMethod.DELETE, }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object deleteUser(@RequestHeader(value = "Authorization") String token,
			@PathVariable String username) {

		try {
			// validate token and delete associate
			if (isValid(token) && isSuperAdmin(role)) {
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
	 * 
	 * To validate the token from the request and set the user role
	 * 
	 * @param tokenString
	 *            String containing the actual auth token
	 * @return Boolean, true if token is valid, false if not
	 */
	public boolean isValid(String tokenString) {
		boolean valid = false;

		Token token = tokenRepo.findByAuthToken(tokenString);
		if (token != null) {
			role = token.getUser().getUserRole().getName();
			valid = true;
		}
		return valid;
	}

	/**
	 * To verify superAdmin role
	 * 
	 * @param role
	 *            String with the role name
	 * @return Boolean, true if role is a superadmin, false if not
	 */

	public boolean isSuperAdmin(String role) {
		boolean authorized = false;
		if (("superAdmin".equalsIgnoreCase(role))) {
			authorized = true;
		}

		return authorized;
	}

	/**
	 * To transform UserInformationChangeDTO object into an User object
	 * 
	 * @param userDTO
	 *            User Data Transfer Object with all relevant attributes
	 *            included
	 * @return Standard user object
	 */

	public User getUser(UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		return user;
	}

	/**
	 * To validate user to be updated
	 * 
	 * @param userDTO
	 *            User Data Transfer Object with attributes that are to be
	 *            updated
	 * @return Updated user object
	 */

	public Object updateValidation(UserDTO userDTO) {
		User user = userRepo.findByUsername(userDTO.getUsername());
		if (userDTO.getUserRole() != null) {
			user.setUserRole(userDTO.getUserRole());
		}
		if (userDTO.getBatchType() != null) {
			user.setBatchType(userDTO.getBatchType());
		}
	
		if (userDTO.getFirstName() != null) {
			user.setFirstName(userDTO.getFirstName());
		}
		if (userDTO.getLastName() != null) {
			user.setLastName(userDTO.getLastName());
		}
		if (userDTO.getAttendance() != null) {
			user.setAttendance(userDTO.getAttendance());
		}
		if (userDTO.getGraduationDate() != null) {
			user.setGraduationDate(userDTO.getGraduationDate());
		}
		if (userDTO.getMarketingStatus() != null){
			user.setMarketingStatus(userDTO.getMarketingStatus());
		}
		if (userDTO.getTasks() != null){
			user.setTasks(userDTO.getTasks());
		}
		if(userDTO.getProject() != null){
			user.setProject(userDTO.getProject());

		}
		if (userDTO.getEvents() != null){
			
			user.setEvents(userDTO.getEvents());
		}
		if (userDTO.getSkill() != null){
			//remove deleted skills
			boolean found;
			Set<TechnicalSkills> list = userDTO.getSkill();
			list.size();
			for(TechnicalSkills ts: user.getSkill()){
				if(!list.contains(ts.getID())){
					userDTO.getSkill().remove(ts);
					ts.getUsers().remove(user);
				}
			}
			
			//add new skills
			Set<User> u;
			for(TechnicalSkills ts: userDTO.getSkill()){
				u = ts.getUsers();
				if(u !=null){
					u.add(user);
					
				}
				else{
					u = new HashSet<User>();
					u.add(user);
				}
				ts.setUsers(u);
			}
			
			user.setSkill(list);
			
		}
		
		return user;

	}

}
