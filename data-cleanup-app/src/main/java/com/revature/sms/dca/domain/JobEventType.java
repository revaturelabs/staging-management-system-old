package com.revature.sms.dca.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * Object that represents the Job Event Type. Mostly for use as part of a 
 * JobEvent object, and to follow normalization principles in the database.
 *
 */
@Entity
@Table(name="JOB_EVENT_TYPE")
public class JobEventType {

	/**
	 * int value that represents the primary key of the table, ID is used to 
	 * identify a specific JobEvent Type by the unique int value. Set up to automatically
	 * generate a value so that it doesn't have to be specified in a constructor or set manually.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "jobEventTypeSeq", sequenceName = "JOB_EVENT_TYPE_SEQ")
	@GeneratedValue(generator = "jobEventTypeSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	/**
	 * String value that contains the specific name of the JobEventType.
	 */
	@Column(name = "TYPE", unique=true, nullable=false)
	private String type;
/**
 * Constructor for JobEventType that creates a JobEventType object with the name of the
 * String value supplied.
 * @param type String value that contains the specific name of the JobEventType.
 */
	public JobEventType(String type) {
		super();
		this.type = type;
	}
/**
 * Null args constructor. Doesn't initialize the type field, so just using this constructor
 * alone isn't sufficient to create a JobEventType that can be persisted in the database.
 */
	public JobEventType() {
		super();
	}
/**
 * Method to retrieve the ID instance variable field of the JobEventType object.
 * @return int value that represents the ID of the JobEventType object.
 */
	public int getID() {
		return ID;
	}
/**
 * Method to set the ID instance variable of the JobEventType object manually.
 * @param iD int value to set the ID value of the object to.
 */
	public void setID(int iD) {
		ID = iD;
	}
/**
 * Method to retrieve the type instance variable of the JobEventType object.
 * @return type String value that represents the name of the JobEventType.
 */
	public String getType() {
		return type;
	}

	/**
	 * Method to set the type instance variable of the JobEventType object manually.
	 * @param type String value that represents the name of the JobEventType object.
	 */
	public void setType(String type) {
		this.type = type;
	}
/**
 * Method that creates a String representation of the JobEventType object.
 */
	@Override
	public String toString() {
		return "JobEventType [ID=" + ID + ", type=" + type + "]";
	}
	
	
}
