package com.revature.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * Object that tracks the current projects an associate is working on.
 *
 */

@Entity
@Table(name="PROJECT_USERS") 
public class ProjectUser {

	/**
	 * int value that represents the primary key of the record.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "projectUserSeq", sequenceName = "PROJECT_USER_SEQ")
	@GeneratedValue(generator = "projectUserSeq", strategy = GenerationType.SEQUENCE)
	private int id;
	
	/**
	 * Project object that represents the project in this record.
	 */
	@ManyToOne
	@JoinColumn(name="PROJECT")
	private Project project;

/*	@Column
	private User associate;*/
	
	/**
	 * Default constructor for ProjectUser.
	 */
	public ProjectUser() {
		super();
	}
	
	/**
	 * Parameterized constructor for ProjectUser. 
	 * @param project Project object that associates a project with a user   
	 * @param user User object that associates a user with a project
	 */
	public ProjectUser(Project project) {
		super();
		this.project = project;
	}

	/**
	 * Get method for Project.
	 * @return Project value that represents the project of the record
	 */
	public Project getProject() {
		return project;
	}

	public int getID() {
		return id;
	}

	public void setID(int iD) {
		id = iD; 
	}

	/**
	 * Set method for Project.
	 * @return Project value that represents the project of the record
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * toString method for ProjectUser.
	 * 
	 */
	@Override
	public String toString() {
		return "ProjectUser [id=" + id + ", project=" + project + "]";
	}
}