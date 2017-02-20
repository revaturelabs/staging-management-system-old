package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.dao.BatchTypeRepo;

@RestController
@RequestMapping("/api/v1/batchType")
public class BatchTypeController {

	@Autowired
	BatchTypeRepo btr;
	
	@RequestMapping(method = RequestMethod.GET)
	public Object getAll() {
		List<BatchType> list = btr.findAll();
		return new ResponseEntity<List<BatchType>>(list, HttpStatus.OK);
	}
}
