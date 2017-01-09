package com.revature.sms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.BatchType;

@Repository
public interface BatchTypeRepo extends JpaRepository<BatchType, Integer>{
	BatchType findByType(String type);
	
}
