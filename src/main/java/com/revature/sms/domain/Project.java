package com.revature.sms.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 
 * Object that tracks the current available projects.
 *
 */
@Entity
@Table(name="PROJECT")
public class Project {
	/**
	 * int value that represents the primary key of the record.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "projectSeq", sequenceName = "PROJECT_SEQ")
	@GeneratedValue(generator = "projectSeq", strategy = GenerationType.SEQUENCE)
	private int ID;

	/**
	 * String value that represents name of the project.
	 */
	@Column(name="NAME", unique=true, nullable=false)
	private String name;
	
	/**
	 * Date object that represents the start date of the project.
	 */
	@Column(name="START_DATE", nullable=false)
	private Timestamp startDate;
	
	/**
	 * Date object that represents the end date of the project.
	 */
	@Column(name="END_DATE", nullable=false)
	private Timestamp endDate;

	/**
	 * String value that represents the description for the project.
	 */
	@Column(name="DESCRIPTION")
	private String description;
	
	/**
	 * Default constructor for Project.
	 */
	public Project() {
		super();
	}

	/**
	 * Parameterized constructor for AssociateTask. 
	 * @param name Name of the project
	 * @param startDate Date object that represents the start date of the project
	 * @param endDate Date object that represents the end date of the project
	 * @param description String value that represents the description of the project.
	 */
	public Project(String name, Timestamp startDate, Timestamp endDate, String description) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
	}

	/**
	 * Set method for ID.
	 * @return ID int value that represents the primary key of the record
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Get method for ID
	 * @param iD int value that represents the primary key of the record
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * Get method for name.
	 * @return name String value that represents name of the project
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set method for name.
	 * @param name String value that represents name of the project
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get method for startDate.
	 * @return startDate date object that represents the start date of a project
	 */
	public Timestamp getStartDate() {
		return startDate;
	}

	/**
	 * Set method for startDate.
	 * @param startDate Timestamp object that represents the startDate of a project
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	/**
	 * Get method for endDate.
	 * @return endDate date object that represents the end date of a project
	 */
	public Timestamp getEndDate() {
		return endDate;
	}

	/**
	 * Set method for endDate.
	 * @param endDate Timestamp object that represents the end date of a project
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	/**
	 * Get method for description.
	 * @return description String value that represents the description of a project
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set method for description.
	 * @param description String value that represents the description of a project
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * toString method for Project.
	 */
	@Override
	public String toString() {
		return "Project [ID=" + ID + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", description=" + description + "]";
	}
	
	
	
	
}
