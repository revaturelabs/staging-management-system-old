package com.revature.sms.snas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * Object that acts as a lookup for all associate tasks.
 *
 */
@Entity
@Table(name="ASSOCIATE_TASK_TYPE")
public class AssociateTaskType {
	
	/**
	 * int value that represents the primary key of the record.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "taskTypeSeq", sequenceName = "TASK_TYPE_SEQ")
	@GeneratedValue(generator = "taskTypeSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	/**
	 * String value that represents a type of task that an associate can be working on.
	 */
	@Column(name = "TYPE", unique=true, nullable=false)
	private String type;

	/**
	 * Default constructor for AssociateTaskType.
	 */
	public AssociateTaskType() {
		super();
	}

	/**
	 * Parameterized constructor for AssociateTaskType.
	 * @param type String value that represents a type of task that an associate can be working on
	 */
	public AssociateTaskType(String type) {
		super();
		this.type = type;
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
	 * Get method for type.
	 * @return type String value that represents a type of task that an associate can be working on
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Set method for type.
	 * @param type String value that represents a type of task that an associate can be working on
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * toString method for AssociateTaskType.
	 * 
	 */
	@Override
	public String toString() {
		return "TaskType [ID=" + ID + ", type=" + type + "]";
	}
	
	
}
