package com.revature.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * Object that represents all the job assignments that associate's have been assigned to.
 *
 */
@Entity
@Table(name="JOB_ASSIGNMENT")
public class JobAssignment {
	
	/**
	 * int value that represents the primary key of the record.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "jobAssignmentSeq", sequenceName = "JOB_ASSIGNMENT_SEQ")
	@GeneratedValue(generator = "jobAssignmentSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	/**
	 * String value that represents the company name.
	 */
	@Column(name="COMPANY_NAME", nullable=false)
	private String companyName;
	
	/**
	 * String value that represents the location of the company.
	 */
	@Column(name="LOCATION", nullable=false)
	private String location;
	
	/**
	 * String value for the title an associate is assigned for a job.
	 */
	@Column(name="JOB_TITLE", nullable=false)
	private String jobTitle;

	/**
	 * Parameterized constructor for JobAssignment.
	 * @param companyName String value that represents the company name
	 * @param location String value that represents the location of the company
	 * @param jobTitle String value for the title an associate is assigned for a job
	 */
	public JobAssignment(String companyName, String location, String jobTitle) {
		super();
		this.companyName = companyName;
		this.location = location;
		this.jobTitle = jobTitle;
	}
	
	/**
	 * Default constructor for JobAssignment
	 */
	public JobAssignment() {
		super();
	}
	
	/**
	 * Get method for ID.
	 * @return ID int value that represents the primary key of the record
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Set method for ID.
	 * @param iD int value that represents the primary key of the record
	 */
	public void setID(int iD) {
		ID = iD;
	}
	
	/**
	 * Get method for companyName.
	 * @return companyName String value that represents the company name
	 */
	public String getCompanyName() {
		return companyName;
	}
	
	/**
	 * Set method for companyName.
	 * @param companyName String value that represents the company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	/**
	 * Get method for location.
	 * @return location String value that represents the location of the company
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Set method for location.
	 * @param location String value that represents the location of the company
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Get method for jobTitle.
	 * @return jobTitle jobTitle String value for the title an associate is assigned for a job
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * Set method for jobTitle.
	 * @param jobTitle jobTitle String value for the title an associate is assigned for a job
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	/**
	 * toString method for JobAssignment.
	 */
	@Override
	public String toString() {
		return "JobAssignment [ID=" + ID + ", companyName=" + companyName + ", location=" + location + ", jobTitle="
				+ jobTitle + "]";
	}
}
