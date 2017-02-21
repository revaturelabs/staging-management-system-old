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
 * Object that acts as a lookup for all batch types.
 *
 */
@Entity
@Table(name="BATCH_TYPE")
public class BatchType {
	
	/**
	 * int value that represents the primary key of the record.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "batchTypeSeq", sequenceName = "BATCH_TYPE_SEQ")
	@GeneratedValue(generator = "batchTypeSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	/**
	 * String value that represents a type batch that an associate is from.
	 */
	@Column(name = "TYPE", unique=true, nullable=false)
	private String type;
	
	/**
	 * Default constructor for BatchType.
	 */
	public BatchType() {
		super();
	}

	/**
	 * Parameterized constructor for BatchType.
	 * @param type String value that represents a type batch that an associate is from
	 */
	public BatchType(String type) {
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
	 * @return type String value that represents a type batch that an associate is from
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Set method for type
	 * @param type String value that represents a type batch that an associate is from
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * toString method for AssociateTaskType.
	 */
	@Override
	public String toString() {
		return "BatchType [ID=" + ID + ", type=" + type + "]";
	}

}
