package com.revature.sms.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.sms.domain.ProjectUser;

public interface ProjectUserRepo extends JpaRepository<ProjectUser, Integer> {
	//returns all ProjectUsers with the inputed id
	List<ProjectUser> findByProject(int id);
	
}
