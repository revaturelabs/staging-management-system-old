package com.revature.sms.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.AssociateTaskType;
import com.revature.sms.domain.User;

/**
 * 
 * DAO repository for AssociateTasks.
 *
 */
@Repository
public interface AssociateTasksRepo extends JpaRepository<AssociateTask, Integer>{
	
	/**
	 * Returns a list of tasks based on a given type.
	 * @param taskType - AssociateTaskType of the type of task 
	 * @return List of all tasks of the given type
	 */
	List<AssociateTask> findByTaskType(AssociateTaskType taskType);
}
