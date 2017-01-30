package com.revature.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * Object that represents specific user roles.
 *
 */
@Entity
@Table(name="Marketing_Status")
public class MarketingStatus {
	/**
	 * tryign to get it together int value that represents the primary key of the table, ID is used to
	 * identify a specific UserRole object by the unique int value. Set up to
	 * automatically generate a value so that it doesn't have to be specified in
	 * a constructor or set manually.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "marketingStatusSeq", sequenceName = "MARKETING_STATUS_SEQ")
	@GeneratedValue(generator = "marketingStatusSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	/**
	 * String value that represents the specific name of the UserRole object. Each UserRole must
	 * have a unique name to be stored in the database.
	 */
	@Column(name = "STATUS", nullable=false)
	private String status;
/**
 * Null args constructor for the UserRole object.
 */
	public MarketingStatus() {
		super();
	}
	/**
	 * Constructor that sets the name of the UserRole object based on the supplied name.
	 * @param name String value that represents the name of the UserRole.
	 */
	public MarketingStatus(String status) {
		super();
		this.status = status;
	}
/**
 * Method that retrieves the ID of the UserRole object.
 * @return ID int value that represents the ID of the UserRole.
 */
	public int getID() {
		return ID;
	}
/**
 * Method that sets the ID of the UserRole object.
 * @param iD int value of the id to be set.
 */
	public void setID(int iD) {
		ID = iD;
	}
/**
 * Method that retrieves the name of the UserRole object. 
 * @return name String value that represents the name of the UserRole.
 */
	public String getName() {
		return status;
	}
/**
 * Method that sets the name of the UserRole object.
 * @param name String value of the name to be set.
 */
	public void setName(String status) {
		this.status = status;
	}
/**
 * Method that returns the string representation of the UserRole object.
 */
	@Override
	public String toString() {
		return "UserRole [ID=" + ID + ", status=" + status + "]";
	}
	
	
}
