package com.revature.sms.snas.domain;

import java.sql.Date;

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
 * Object that represents an associate who has a job assignment.
 *
 */
@Entity
@Table(name="JOB_EVENT")
public class JobEvent {
	
	/**
	 * int value that represents the primary key of the record.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "jobEventSeq", sequenceName = "JOB_EVENT_SEQ")
	@GeneratedValue(generator = "jobEventSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	/**
	 * User object that represents the associate in this job assignment record.
	 */
	@ManyToOne
	@JoinColumn(name="ASSOCIATE")
	private User associate;
	
	/**
	 * JobAssignment object that represents the job that an associate has been assigned to.
	 */
	@ManyToOne
	@JoinColumn(name="ASSIGNMENT")
	private JobAssignment assignment;
	
	/**
	 * JobEventType object that represents the type of job an associate is assigned to.
	 */
	@ManyToOne
	@JoinColumn(name="TYPE")
	private JobEventType type;
	
	/**
	 * Date object that represents the start date for an associate's job assignment.
	 */
	@Column(name="EVENT_DATE", nullable=false)
	private Date date;
	
	/**
	 * String value that allows an admin to include an optional note regarding the associate's job status.
	 */
	@Column(name="NOTE")
	private String note;

	/**
	 * Default constructor for JobEvent.
	 */
	public JobEvent() {
		super();
	}

	/**
	 * Parameterized constructor for JobEvent.
	 * @param associate User object that represents the associate in this job assignment record
	 * @param assignment JobAssignment object that represents the job that an associate has been assigned to
	 * @param type JobEventType object that represents the type of job an associate is assigned to
	 * @param date Date object that represents the start date for an associate's job assignment
	 * @param note String value that allows an admin to include an optional note regarding the associate's job status
	 */
	public JobEvent(User associate, JobAssignment assignment, JobEventType type, Date date, String note) {
		super();
		this.associate = associate;
		this.assignment = assignment;
		this.type = type;
		this.date = date;
		this.note = note;
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
	 * Get method for associate.
	 * @return associate User object that represents the associate in this job assignment record
	 */
	public User getAssociate() {
		return associate;
	}
	
	/**
	 * Set method for associate.
	 * @param associate User object that represents the associate in this job assignment record
	 */
	public void setAssociate(User associate) {
		this.associate = associate;
	}
	
	/**
	 * Get method for assignment.
	 * @return assignment JobAssignment object that represents the job that an associate has been assigned to
	 */
	public JobAssignment getAssignment() {
		return assignment;
	}
	
	/**
	 * Set method for assignment.
	 * @param assignment JobAssignment object that represents the job that an associate has been assigned to
	 */
	public void setAssignment(JobAssignment assignment) {
		this.assignment = assignment;
	}
	
	/**
	 * Get method for type.
	 * @return type JobEventType object that represents the type of job an associate is assigned to
	 */
	public JobEventType getType() {
		return type;
	}
	
	/**
	 * Set method for type.
	 * @param type JobEventType object that represents the type of job an associate is assigned to
	 */
	public void setType(JobEventType type) {
		this.type = type;
	}

	/**
	 * Get method for date.
	 * @return date Date object that represents the start date for an associate's job assignment
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Set method for date.
	 * @param date Date object that represents the start date for an associate's job assignment
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Get method for note.
	 * @return note String value that allows an admin to include an optional note regarding the associate's job status
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * Set method for note.
	 * @param note String value that allows an admin to include an optional note regarding the associate's job status
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * toString method for JobEvent.
	 */
	@Override
	public String toString() {
		return "JobEvent [ID=" + ID + ", associate=" + associate.getUsername() + ", assignment=" + assignment + ", type=" + type.getType()
				+ ", date=" + date + ", note=" + note + "]";
	}
	
	
	
	
}
