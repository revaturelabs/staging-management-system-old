package com.revature.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.AssociateTasksRepo;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dto.ResponseErrorEntity;


@RestController
@RequestMapping("api/v1/associateTask")
public class AssociateTaskController {

	@Autowired
	AssociateTasksRepo taskRepo;
	
	@Autowired
	UserRepo userRepo;

	/**
	 * Update task
	 * @param task
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Object updateTask(@RequestBody AssociateTask associateTask) {
		try {
			AssociateTask task = (AssociateTask) updateValidation(associateTask);
			task = taskRepo.save(task);

			return new ResponseEntity<AssociateTask>(task, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Failed to update associate tasks"),
					HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Retrieve all tasks
	 * @return
	 */

	@RequestMapping(method = RequestMethod.GET)
	public Object retrieveAll() {
		try {
            List<User> users = userRepo.findAll();
			for (User user : users) {
				user.blankPassword();
				user.setID(0);
			}

			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResponseErrorEntity>(
					new ResponseErrorEntity("Failed to retrieve associate tasks"), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Delete a task
	 * @param task
	 * @return
	 */

	@RequestMapping(method = RequestMethod.DELETE)
	public Object deleteTask(@RequestBody AssociateTask task) {
		try {
			taskRepo.delete(task);

			return new ResponseEntity<AssociateTask>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResponseErrorEntity>(new ResponseErrorEntity("Failed to delete associate tasks"),
					HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * To validate task before update
	 * @param associateTask
	 * @return
	 */

	public Object updateValidation(AssociateTask associateTask) {
		AssociateTask task = taskRepo.findOne(associateTask.getID());
		if (associateTask.getNote() != null) {
			task.setNote(associateTask.getNote());
		}
		return task;

	}

}
