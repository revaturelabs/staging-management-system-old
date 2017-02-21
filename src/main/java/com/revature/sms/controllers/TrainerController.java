package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.Trainer;

import com.revature.sms.domain.dao.TrainerRepo;

@RestController
@RequestMapping("/api/v1/Trainer")
public class TrainerController {

	@Autowired
	TrainerRepo attr;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getAll() {
		List<Trainer> list = attr.findAll();
		return new ResponseEntity<List<Trainer>>(list, HttpStatus.OK);
	}
}
