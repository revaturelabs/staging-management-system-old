package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.JobEventType;

import com.revature.sms.domain.dao.JobEventTypeRepo;

@RestController
@RequestMapping("/api/v1/JobEventType")
public class JobEventTypeController {

	@Autowired
	JobEventTypeRepo attr;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getAll() {
		List<JobEventType> list = attr.findAll();
		return new ResponseEntity<List<JobEventType>>(list, HttpStatus.OK);
	}
}
