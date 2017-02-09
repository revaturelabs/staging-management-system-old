package com.revature.sms.snas.domain.dao;

/**
 * DAO repository for the JobAssignment
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.snas.domain.JobAssignment;

@Repository
public interface JobAssignmentRepo extends JpaRepository<JobAssignment, Integer>{

}
