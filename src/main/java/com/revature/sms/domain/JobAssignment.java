package com.revature.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="JOB_ASSIGNMENT")
public class JobAssignment {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "jobAssignmentSeq", sequenceName = "JOB_ASSIGNMENT_SEQ")
	@GeneratedValue(generator = "jobAssignmentSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	@Column(name="COMPANY_NAME", nullable=false)
	private String companyName;
	
	@Column(name="LOCATION", nullable=false)
	private String location;
	
	@Column(name="JOB_TITLE", nullable=false)
	private String jobTitle;

	public JobAssignment(String companyName, String location, String jobTitle) {
		super();
		this.companyName = companyName;
		this.location = location;
		this.jobTitle = jobTitle;
	}

	public JobAssignment() {
		super();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Override
	public String toString() {
		return "JobAssignment [ID=" + ID + ", companyName=" + companyName + ", location=" + location + ", jobTitle="
				+ jobTitle + "]";
	}
}
