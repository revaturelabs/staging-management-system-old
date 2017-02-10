package com.revature.sms.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.Project;
import com.revature.sms.domain.ProjectUser;
import com.revature.sms.domain.Token;
import com.revature.sms.domain.dao.ProjectRepo;
import com.revature.sms.domain.dao.ProjectUserRepo;
import com.revature.sms.domain.dao.TokenRepo;
import com.revature.sms.domain.dto.ResponseErrorEntity;
import com.revature.sms.domain.dto.bc;


/**
 * Server-side controller to handle Project CRUD.
 *
 */
@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
	
	/**
	 * Autowired ProjectRepo object. Spring handles setting this up for actual use.
	 */
	@Autowired
	ProjectRepo pr;

	
	/**
	 * Autowired ProjectUserRepo object. Spring handles setting this up for actual use.
	 */
	@Autowired
	ProjectUserRepo pur;

	
	/**
	 * Autowired TokenRepo object. Spring handles setting this up for actual use.
	 */
	@Autowired
	private TokenRepo tokenRepo;

	/**
	 * Method that returns all projects.
	 * @return ResponseEntity object containing a list of all projects.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Object getAll() {
		try {
			List<Project> list = pr.findAll();
			return new ResponseEntity<List<Project>>(list, HttpStatus.OK);
		}  catch (Exception e) {
			Logger.getRootLogger().debug("Exception while retrieving Projects.", e);
			return new ResponseEntity<ResponseErrorEntity>( new ResponseErrorEntity("Exception while retrieving Projects."), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	/**
	 * Method to update or create projects.
	 * @param token String value of authorization token.
	 * @param projects List containing Projects to be updated or created.
	 * @return ResponseEntity object containing a Boolean object with value of true if if projects were successfully updated/created, false if it is not.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Object update(@RequestHeader(value = "Authorization") String token,@RequestBody List<Project> projects) {
		try {
			// validate token
			if (isValid(token)) {
				pr.save(projects);
				return new ResponseEntity<bc>(new bc(true), HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized."), HttpStatus.UNAUTHORIZED);
			}
		} catch (DataIntegrityViolationException dive) {
			Logger.getRootLogger().debug("Project name already exists.", dive);
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Project name already exists."), HttpStatus.CONFLICT);
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while creating Project.", e);
			return new ResponseEntity<ResponseErrorEntity>( new ResponseErrorEntity("Exception while creating Project."), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	/**
	 * Method to delete projects.
	 * @param token String value of authorization token.
	 * @param projects List containing Projects to be deleted.
	 * @return ResponseEntity object containing a Boolean object with value of true if projects were successfully deleted, false if it is not.
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody Object remove(@RequestHeader(value = "Authorization") String token, @RequestBody List<Project> projects) {
		try {
			// validate token
			if (isValid(token)) {
				for (Project p : projects) {
					try{
					pur.deleteByProject(p);
					}catch(Exception e){
						e.printStackTrace();
						Logger.getRootLogger().debug("Exception while removing Project.", e);
					}
				}
				
				for (Project p : projects) {
					try{
						pr.delete(p);
					}catch(Exception e){
						e.printStackTrace();
						Logger.getRootLogger().debug("Exception while removing Project.", e);
					}
				}
				
				return new ResponseEntity<bc>(new bc(true), HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized"), HttpStatus.UNAUTHORIZED);
			}
			
		} catch (Exception e) {
			Logger.getRootLogger().debug("Exception while removing Project.", e);
			return new ResponseEntity<ResponseErrorEntity>( new ResponseErrorEntity("Exception while removing Project."), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	/**
	 * Method that returns all projectUsers.
	 * @return ResponseEntity object containing a list of all projectUsers.
	 */
	@RequestMapping("/user")
	public Object getAllProjectUsers() {
		try {
			List<ProjectUser> list = pur.findAll();
			return new ResponseEntity<List<ProjectUser>>(list, HttpStatus.OK);
		}  catch (Exception e) {
			Logger.getRootLogger().debug("Exception while retrieving Projects.", e);
			return new ResponseEntity<ResponseErrorEntity>( new ResponseErrorEntity("Exception while retrieving Projects."), HttpStatus.SERVICE_UNAVAILABLE);
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
			// role = token.getUser().getUserRole().getName();
			valid = true;
		}
		return valid;
	}
}