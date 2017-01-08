package com.revature.sms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.JobEventType;

@Repository
public interface JobEventTypeRepo extends JpaRepository<JobEventType, Integer> {

	JobEventType findByType(String type);
}
