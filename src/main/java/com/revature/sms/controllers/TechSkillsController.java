package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.Technical_Skills;
import com.revature.sms.domain.dao.Technical_SkillsRepo;

@RestController
@RequestMapping("/api/v1/TechSkills")
public class TechSkillsController {

	@Autowired
	Technical_SkillsRepo attr;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getAll() {
		List<Technical_Skills> list = attr.findAll();
		return new ResponseEntity<List<Technical_Skills>>(list, HttpStatus.OK);
	}
}