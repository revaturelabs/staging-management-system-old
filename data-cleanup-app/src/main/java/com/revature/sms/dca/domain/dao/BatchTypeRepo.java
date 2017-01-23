package com.revature.sms.dca.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.dca.domain.BatchType;

/**
 * 
 * DAO repository for BatchType.
 *
 */
@Repository
public interface BatchTypeRepo extends JpaRepository<BatchType, Integer>{
	
	/**
	 * Return a list of all batch types.
	 * @param type String of the batch type
	 * @return BatchType BatchType object for the given string
	 */
	BatchType findByType(String type);
	
}
