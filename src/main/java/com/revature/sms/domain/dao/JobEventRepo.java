package com.revature.sms.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.JobEvent;
import com.revature.sms.domain.JobEventType;
import com.revature.sms.domain.User;

@Repository
public interface JobEventRepo extends JpaRepository<JobEvent, Integer>{

	List<JobEvent> findByAssociate(User associate);
	
	List<JobEvent> findByType(JobEventType type);
	
}
