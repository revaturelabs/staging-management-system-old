package com.revature.sms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.AssociateTaskType;

/**
 * 
 * DAO repository for AssociateTaskType
 *
 */
@Repository
public interface AssociateTaskTypeRepo extends JpaRepository<AssociateTaskType, Integer>{

	/**
	 * Return a list of all associate task types.
	 * @param type - String of the task type
	 * @return AssosicateTaskType - AssociateTaskType object for the given string.
	 */
	AssociateTaskType findByType(String type);
}
