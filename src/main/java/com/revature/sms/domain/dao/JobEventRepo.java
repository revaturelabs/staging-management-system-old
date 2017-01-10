package com.revature.sms.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.JobEvent;
import com.revature.sms.domain.JobEventType;
import com.revature.sms.domain.User;
/**
 * 
 * DAO repo for JobEvent.
 *
 */
@Repository
public interface JobEventRepo extends JpaRepository<JobEvent, Integer>{
/**
 * Method to retrieve JobEvent list by specific associate.
 * @param associate User object that matches to the list of job events to retrieve.
 * @return List of JobEvents associated with the supplied user.
 */
	List<JobEvent> findByAssociate(User associate);

	/**
	 * Method to retrieve JobEvent list by JobEventType.
	 * @param type JobEventType object that matches to the list of job events to retrieve.
	 * @return List of JobEvents of the specific JobEventType.
	 */
	List<JobEvent> findByType(JobEventType type);
	
}
