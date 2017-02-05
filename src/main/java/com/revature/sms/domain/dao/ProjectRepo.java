package com.revature.sms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.Project;

/**
 * 
 * DAO repository for Project
 *
 */
@Repository
public interface ProjectRepo extends JpaRepository<Project, Integer>{

	/**
	 * Method to retrieve Project by supplied String representing the name.
	 * @param name String value of the name to retrieve
	 * @return project object matching supplied String.
	 */
	Project findByName(String name);
}
