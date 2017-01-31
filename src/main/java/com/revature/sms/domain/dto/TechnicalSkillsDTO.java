package com.revature.sms.domain.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.revature.sms.domain.User;
/**
 * DTO object for TechnicalSkills class.
 *
 */
public class TechnicalSkillsDTO {
	/**
	 * String that represents name of the Skill.
	 */
	private String skill;
	
	/**
	 * List of users associated with skill
	 */
	private Set<User> users;
	
	
	public TechnicalSkillsDTO(){
		super();
	}
	/**
	 * Parameterized constructor for technicalSkillsDTO.
	 * @param skill represents the unique skill that an associate has
	 */
	public TechnicalSkillsDTO(String skill){
		this.skill = skill;
	}
	/**
	 * Parameterized constructor for technicalSkills which also has list of users
	 * @param skill represents the unique skill that an associate has
	 * @param users gives a list of users for that specific skill
	 */
	public TechnicalSkillsDTO(String skill, Set<User> users) {
		
		this.skill = skill;
		this.users = users;
	}
	
	/**
	 * Method to retrieve the name of the TechnicalSkillsDTO object.
	 * @return skill String value that represents the skill name of the TechnicalSkillsDTO.
	 */
	public String getSkill() {
		return skill;
	}
	
	/**
	 * Method to manually set the skill of the current Technical_Skill object.
	 * @param skill String value that represents the skill name to be set.
	 */
	public void setSkill(String skill) {
		this.skill = skill;
	}

	/**
	 * Method that retrieves the list of users of the skill
	 */
	
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * Method that manually sets the users of the skill object
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Technical_Skills [skill=" + skill + ", users=" + users + "]";
	}
}
