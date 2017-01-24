package com.revature.sms.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.Technical_Skills;
import com.revature.sms.domain.User;

/**
 * 
 * DAO repo for Technical_Skills
 *
 */
@Repository
public interface Technical_SkillsRepo extends JpaRepository<Technical_Skills, Integer> { 

	/**
	 * method to retrieve object based on skill name
	 * @param skillName String value that contains the name of the skill
	 * @return skill object based on supplied skill name
	 */
	Technical_Skills findBySkill(String skill);
	
	/**
	 * Method to retrieve Skills list by specific associate.
	 * @param associate User object that matches to the list of technical skills to retrieve.
	 * @return List of technical skills associated with the supplied user.
	 */
	List<Technical_Skills> findbyAssociate(User associate);
	
	/**
	 * Method to retrieve users list by specific skill.
	 * @param skill object that matches to the list of users to retrieve.
	 * @return List of users associated with the supplied skill.
	 */
	List<User> findbySkill(Technical_Skills skill);
}
