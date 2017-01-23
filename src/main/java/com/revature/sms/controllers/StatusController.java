package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.Status;
import com.revature.sms.domain.dao.StatusRepo;


@RestController
@RequestMapping("/api/v1/statuses")
public class StatusController {

	@Autowired
	StatusRepo sts;
	
	@RequestMapping(method = RequestMethod.GET)
	public Object getAll() {
		List<Status> list = sts.findAll();
		return new ResponseEntity<List<Status>>(list, HttpStatus.OK);
	}
}
