package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.TechnicalSkills;
import com.revature.sms.domain.dao.TechnicalSkillsRepo;

import com.revature.sms.domain.dao.TokenRepo;
import com.revature.sms.domain.Token;


@RestController
@RequestMapping("/api/v1/TechSkills")
public class TechSkillsController {

	@Autowired
	TechnicalSkillsRepo attr;
	
	@Autowired
	TokenRepo tokenRepo;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getAll(@RequestHeader(value = "Authorization") String token) {
		try {
			//validate token and retrieve all skills
		}
		List<TechnicalSkills> list = attr.findAll();
		return new ResponseEntity<List<TechnicalSkills>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{skillName}", method = {
			RequestMethod.GET }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object retrieveSkill(@RequestHeader(value = "Authorization") String token,
			@PathVariable String skillName){
		return null;
	}
	
}
