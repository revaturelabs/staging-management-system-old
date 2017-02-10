package com.revature.sms.domain.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.sms.domain.Project;
import com.revature.sms.domain.ProjectUser;


/**
 * 
 * DAO repository for Projects.
 *
 */
public interface ProjectUserRepo extends JpaRepository<ProjectUser, Integer> {

	/**
	 * Deletes projectUser entries by specified project.
	 * @param project Project object to be deleted
	 */
	@Transactional
	Long deleteByProject(Project project);
	
}
