package com.revature.sms.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
	@Autowired
	ProjectRepo pr;
	
	@Autowired
	ProjectUserRepo pur;
	
	@Autowired
	private TokenRepo tokenRepo;
	
	//returns all projects
	@RequestMapping(method = RequestMethod.GET)
	public Object getAll() {
		List<Project> list = pr.findAll();
		return new ResponseEntity<List<Project>>(list, HttpStatus.OK);
	}
	
	//updates all inputed projects
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Object update(@RequestHeader(value = "Authorization") String token, @RequestBody List<Project> projects) {
		//validate token
		if(isValid(token)){
			pr.save(projects);
			return new ResponseEntity<bc>(new bc(true), HttpStatus.OK);
		}
		else{
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized"), HttpStatus.UNAUTHORIZED);
		}
	}
	
	//deletes all inputed projects
	@RequestMapping(value="/remove",method = RequestMethod.POST)
	public @ResponseBody Object remove(@RequestHeader(value = "Authorization") String token, @RequestBody List<Project> projects) {
		//validate token
		if(isValid(token)){
			
			for(Project p : projects){
				pur.deleteByProject(p);
			}
			pr.delete(projects);
			return new ResponseEntity<bc>(new bc(true), HttpStatus.OK);
		}
		else{
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("User is unauthorized"), HttpStatus.UNAUTHORIZED);
		}
	}
	
	//returns all ProjectUsers
	@RequestMapping("/user")
	public Object getAllProjectUsers(){
		List<ProjectUser> list = pur.findAll();
		return new ResponseEntity<List<ProjectUser>>(list, HttpStatus.OK);
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
			//role = token.getUser().getUserRole().getName();
			valid = true;
		}
		return valid;
	}
	
}
