package com.revature.sms.controllers;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
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
import com.revature.sms.domain.dao.TechnicalSkillsRepo;
import com.revature.sms.domain.dao.TokenRepo;
import com.revature.sms.domain.dto.ResponseErrorEntity;
import com.revature.sms.domain.dto.TechnicalSkillsDTO;

@RestController
@RequestMapping("/api/v1/TechSkills")
public class TechSkillsController {

	@Autowired
	TechnicalSkillsRepo attr;

	@Autowired
	TokenRepo tokenRepo;
	
	static final String SUPERADMIN = "superAdmin";
	static final String USERUNAUTHORIZED = "User is unauthorized";
	static final String AUTHINVALID = "AuthToken invalid.";

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getAll() {
		try {

			List<TechnicalSkills> list = attr.findAll();
			return new ResponseEntity<List<TechnicalSkills>>(list, HttpStatus.OK);

		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while retrieving all skills.", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while retrieving all skills."), HttpStatus.NOT_FOUND);

		}

	}

	@RequestMapping(value = "/{skillName}", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public Object retrieveSkill(@RequestHeader(value = "Authorization") String token,
			@PathVariable String skillName) {
		try {
			Token userToken = tokenRepo.findByAuthToken(token);
			if (userToken == null) {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity(AUTHINVALID),
						HttpStatus.NOT_FOUND);
			} else {
				TechnicalSkills skill = attr.findBySkill(skillName);
				return new ResponseEntity<TechnicalSkills>(skill, HttpStatus.OK);
			}

		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while retrieving " + skillName + " skill.", e);
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Problem occurred while retrieving " + skillName + " skill."),
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Method to create a new TechnicalSkill in the database.
	 * 
	 * @param token
	 *            String containing token value for authorization
	 * @param skillDTO
	 *            TechnicalSkillsDTO object with information for the new skill
	 * @return ResponseEntity giving result of the create operation.
	 */
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public Object createSkill(@RequestHeader(value = "Authorization") String token,
			@RequestBody TechnicalSkillsDTO skillDTO) {
		Token userToken = tokenRepo.findByAuthToken(token);
		if (userToken == null) {
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity(AUTHINVALID),
					HttpStatus.NOT_FOUND);
		} else if (SUPERADMIN.equalsIgnoreCase(userToken.getUser().getUserRole().getName())) {
			try {
				TechnicalSkills skill = getSkill(skillDTO);
				skill = attr.save(skill);
				return new ResponseEntity<TechnicalSkills>(skill, HttpStatus.CREATED);
			} catch (DataIntegrityViolationException dive) {
				Logger.getRootLogger().debug("Skill already exists.", dive);
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Skill already exists."),
						HttpStatus.CONFLICT);
			} catch (Exception e) {
				Logger.getRootLogger().debug("Exception while creating skill", e);
				return new ResponseEntity<ResponseErrorEntity>(
						new ResponseErrorEntity("Problem occurred while creating skill."),
						HttpStatus.SERVICE_UNAVAILABLE);
			}
		} else {
			// if the user isn't a SuperAdmin
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity(USERUNAUTHORIZED),
					HttpStatus.UNAUTHORIZED);
		}

	}
	/**
	 * Method to delete the supplied skill from the database.
	 * @param token String authentication token to make sure the user has the right permissions.
	 * @param skillName String the skill to be deleted from the database.
	 * @return ResponseEntity containing the results of the operation.
	 */
	@RequestMapping(value = "/{skillName}", method = {
			RequestMethod.DELETE, }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public Object deleteSkill(@RequestHeader(value = "Authorization") String token,
			@PathVariable String skillName){
		Token userToken = tokenRepo.findByAuthToken(token);
		if (userToken == null) {
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity(AUTHINVALID),
					HttpStatus.NOT_FOUND);
		}
		else if (SUPERADMIN.equalsIgnoreCase(userToken.getUser().getUserRole().getName())) {
			try {
				long result = attr.deleteBySkill(skillName);
				if (result == 0){
					return new ResponseEntity<ResponseErrorEntity>(
							new ResponseErrorEntity("Skill does not exist."), HttpStatus.NOT_FOUND);
				}
				return new ResponseEntity<Long>(result, HttpStatus.OK);
			}
			catch (Exception e){
				Logger.getRootLogger().debug(e.getMessage(), e);
				if (e instanceof DataIntegrityViolationException){
					return new ResponseEntity<ResponseErrorEntity>(
							new ResponseErrorEntity("Can't delete skill that's attached to a user."), HttpStatus.BAD_REQUEST);
				}
				
				return new ResponseEntity<ResponseErrorEntity>(
						new ResponseErrorEntity("Problem occurred while deleting skill."), HttpStatus.BAD_REQUEST);
			}
		}
		else {
			// if the user isn't a SuperAdmin
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity(USERUNAUTHORIZED),
					HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@RequestMapping(value = "/{skillName}/{newSkillName}", method = {
			RequestMethod.POST, },produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public Object updateSkill(@RequestHeader(value = "Authorization") String token,
			@PathVariable String skillName, @PathVariable String newSkillName){
		
		Token userToken = tokenRepo.findByAuthToken(token);
		if (userToken == null) {
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity(AUTHINVALID),
					HttpStatus.NOT_FOUND);
		}
		else if (SUPERADMIN.equalsIgnoreCase(userToken.getUser().getUserRole().getName())) {
			try {
				TechnicalSkills skill = attr.findBySkill(skillName);
				skill.setSkill(newSkillName);
				attr.save(skill);
				return new ResponseEntity<TechnicalSkills>(skill, HttpStatus.OK);
				
			}
			catch (DataIntegrityViolationException dive){
				Logger.getRootLogger().debug(dive.getMessage(), dive);
				return new ResponseEntity<ResponseErrorEntity>(
						new ResponseErrorEntity("A skill cannot be renamed to the same name as another skill."), HttpStatus.BAD_REQUEST);
			}
			catch (Exception e){
				Logger.getRootLogger().debug(e.getMessage(), e);
				
				return new ResponseEntity<ResponseErrorEntity>(
						new ResponseErrorEntity("Problem occurred while deleting skill."), HttpStatus.BAD_REQUEST);
			}
		}		
		else {
			// if the user isn't a SuperAdmin
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity(USERUNAUTHORIZED),
					HttpStatus.UNAUTHORIZED);
		}
	}
	/**
	 * Transforms TechnicalSkillsDTO object to TechnicalSkills object.
	 * 
	 * @param skillDTO
	 *            - TechnicalSkillsDTO with the data for the TechnicalSkills.
	 * @return TechnicalSkill object with the supplied properties.
	 */
	public TechnicalSkills getSkill(TechnicalSkillsDTO skillDTO) {
		TechnicalSkills skill = new TechnicalSkills();
		BeanUtils.copyProperties(skillDTO, skill);
		return skill;
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
			valid = true;
		}
		return valid;
	}

}
