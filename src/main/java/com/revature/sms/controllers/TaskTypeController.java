package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.AssociateTaskType;
import com.revature.sms.domain.dao.AssociateTaskTypeRepo;


@RestController
@RequestMapping("/api/v1/taskType")
public class TaskTypeController {

	@Autowired
	AssociateTaskTypeRepo attr;
	
	@RequestMapping(method = RequestMethod.GET)
	public Object getAll() {
		List<AssociateTaskType> list = attr.findAll();
		return new ResponseEntity<List<AssociateTaskType>>(list, HttpStatus.OK);
	}
}
