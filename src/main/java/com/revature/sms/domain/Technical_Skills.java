package com.revature.sms.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

/**
 * Object that represents the technical_skills for users.
 *
 */
@Entity
@Table(name = "TECHNICAL_SKILLS")
public class Technical_Skills {
	/**
	 * int value that represents the primary key of the table
	 */
	@Id
	@Column(name = "TS_ID")
	@SequenceGenerator(allocationSize = 1, name = "TechSkillSeq", sequenceName = "TECH_SKILL_SEQ")
	@GeneratedValue(generator = "TechSkillSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	/**
	 * Technical skill that a user has
	 */
	@Column(name = "SKILL", unique=true, nullable=false)
	private String skill;
	
	/**
	 * List of users associated with skill
	 */
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="USER_SKILLS", 
	joinColumns=@JoinColumn(name="TS_ID"), 
	inverseJoinColumns=@JoinColumn(name="USER_ID"))
	private List<User> users;

	/**
	 * Null args constructor. Doesn't initialize any of the technical_skills instance variables.
	 */
	public Technical_Skills() {
		super();
		this.users = new ArrayList<User>();
	}

	/**
	 * Parameterized constructor for technicalSkills.
	 * @param skill represents the unique skill that an associate has
	 */
	public Technical_Skills(String skill) {
		super();
		this.skill = skill;
	}

	/**
	 * Parameterized constructor for technicalSkills which also has list of users
	 * @param skill represents the unique skill that an associate has
	 * @param users gives a list of users for that specific skill
	 */
	public Technical_Skills(String skill, List<User> users) {
		super();
		this.skill = skill;
		this.users = users;
	}

	/**
	 * Method to retrieve the ID value of the Technical_Skills object.
	 * @return ID int value that represents the id of the Technical_Skills.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Method to manually set the ID value of the current Technical_Skills object.
	 * 
	 * @param iD
	 *            int value that represents the id to be set.
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * Method to retrieve the name of the Technical_Skill object.
	 * @return skill String value that represents the skill name of the Technical_Skill.
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
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Method that manually sets the users of the skill object
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Technical_Skills [ID=" + ID + ", skill=" + skill + ", users=" + users + "]";
	}
	
}