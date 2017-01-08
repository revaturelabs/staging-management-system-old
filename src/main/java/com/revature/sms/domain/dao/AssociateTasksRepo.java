package com.revature.sms.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.AssociateTaskType;
import com.revature.sms.domain.User;

@Repository
public interface AssociateTasksRepo extends JpaRepository<AssociateTask, Integer>{

	List<AssociateTask> findByAssociate(User associate);
	
	List<AssociateTask> findByTaskType(AssociateTaskType taskType);
}
