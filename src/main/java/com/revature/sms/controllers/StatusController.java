package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.Status;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.StatusRepo;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dto.bc;


@RestController
@RequestMapping("/api/v1/statuses")
public class StatusController {
	@Autowired
	StatusRepo sts;
	
	@Autowired
	UserRepo usr;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getAll() {
			 List<Status> list = sts.findAll();
			 return new ResponseEntity<bc>((new bc(list)), HttpStatus.OK);
	}

	
}

