package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.Project;
import com.revature.sms.domain.dao.ProjectRepo;


@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
	@Autowired
	ProjectRepo pr;
	
	@RequestMapping(method = RequestMethod.GET)
	public Object getAll() {
		List<Project> list = pr.findAll();
		return new ResponseEntity<List<Project>>(list, HttpStatus.OK);
	}
}
