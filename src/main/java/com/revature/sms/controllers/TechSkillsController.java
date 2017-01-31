package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.TechnicalSkills;
import com.revature.sms.domain.dao.TechnicalSkillsRepo;

@RestController
@RequestMapping("/api/v1/TechSkills")
public class TechSkillsController {

	@Autowired
	TechnicalSkillsRepo attr;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getAll() {
		List<TechnicalSkills> list = attr.findAll();
		return new ResponseEntity<List<TechnicalSkills>>(list, HttpStatus.OK);
	}
}
