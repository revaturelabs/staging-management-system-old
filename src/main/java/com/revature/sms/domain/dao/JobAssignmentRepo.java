package com.revature.sms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.JobAssignment;

@Repository
public interface JobAssignmentRepo extends JpaRepository<JobAssignment, Integer>{

}