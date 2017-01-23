package com.revature.sms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.Status;

/**
 * 
 * DOA repository for Status
 *
 */
@Repository
public interface StatusRepo extends JpaRepository<Status, Integer>{
	
	

}
