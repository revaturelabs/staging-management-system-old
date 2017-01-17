package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.dao.AssociateTasksRepo;
import com.revature.sms.domain.dto.ResponseErrorEntity;

@RestController
@RequestMapping("api/v1/associateTask")
public class AssociateTaskController {
	@Autowired
	AssociateTasksRepo taskRepo;

	@RequestMapping(method = RequestMethod.GET)
	public Object getTask() {
		try {
			List<AssociateTask> tasks = taskRepo.findAll();
			return new ResponseEntity<List<AssociateTask>>(tasks, HttpStatus.OK);

		} catch (Exception e) {
             return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Exception occured while retriving tasks"),HttpStatus.NOT_FOUND);
		}

	}

}
