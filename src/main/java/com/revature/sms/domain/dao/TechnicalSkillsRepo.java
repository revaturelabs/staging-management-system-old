package com.revature.sms.domain.dao;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.TechnicalSkills;
import com.revature.sms.domain.User;

/**
 * 
 * DAO repo for TechnicalSkills
 *
 */
@Repository
public interface TechnicalSkillsRepo extends JpaRepository<TechnicalSkills, Integer> { 

	/**
	 * method to retrieve object based on skill name
	 * @param skillName String value that contains the name of the skill
	 * @return skill object based on supplied skill name
	 */
	TechnicalSkills findBySkill(String skillName);

	/**
	 * Method that deletes skill based on skill
	 * @param skillName String value that contains name of skill
	 * @return Long value corresponding to the id of the skill that was deleted. 
	 * Returns 0 if the skill doesn't exist in the database.
	 */
	@Transactional
	Long deleteBySkill(String skillName);

	
	/**
	 * Method to retrieve Skills list by specific associate.
	 * @param associate User object that matches to the list of technical skills to retrieve.
	 * @return List of technical skills associated with the supplied user.
	 */
	//List<Technical_Skills> findbyTS_ID(User associate);
	
	/**
	 * Method to retrieve users list by specific skill.
	 * @param skill object that matches to the list of users to retrieve.
	 * @return List of users associated with the supplied skill.
	 */
	//List<User> findbySkill(Technical_Skills skill);
	
		
}
