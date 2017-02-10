package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.Project;
import com.revature.sms.domain.ProjectUser;
import com.revature.sms.domain.dao.ProjectRepo;
import com.revature.sms.domain.dao.ProjectUserRepo;


@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
	@Autowired
	ProjectRepo pr;
	
	@Autowired
	ProjectUserRepo pur;
	
	//returns all project
	@RequestMapping(method = RequestMethod.GET)
	public Object getAll() {
		List<Project> list = pr.findAll();
		return new ResponseEntity<List<Project>>(list, HttpStatus.OK);
	}
	
	//returns all ProjectUsers
	@RequestMapping("/user")
	public Object getAllProjectUsers(){
		List<ProjectUser> list = pur.findAll();
		return new ResponseEntity<List<ProjectUser>>(list, HttpStatus.OK);

	}
	
}
