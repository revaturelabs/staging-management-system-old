package com.revature.sms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.JobEventType;
/**
 * DAO repo for JobEventType 
 */
@Repository
public interface JobEventTypeRepo extends JpaRepository<JobEventType, Integer> {
/**
 * Method to retrieve JobEventType by supplied String representing the type.
 * @param type - String value of the JobEventType to retrieve
 * @return JobEventType object matching supplied String.
 */
	JobEventType findByType(String type);
}
