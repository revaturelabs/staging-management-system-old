package com.revature.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * Class that represents specific user roles.
 *
 */
@Entity
@Table(name="USER_ROLE")
public class UserRole {
	/**
	 * int value that represents the primary key of the table, ID is used to
	 * identify a specific UserRole object by the unique int value. Set up to
	 * automatically generate a value so that it doesn't have to be specified in
	 * a constructor or set manually.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "userRoleSeq", sequenceName = "USER_ROLE_SEQ")
	@GeneratedValue(generator = "userRoleSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	/**
	 * String value that represents the specific name of the UserRole object. Each UserRole must
	 * have a unique name to be stored in the database.
	 */
	@Column(name = "NAME", unique=true, nullable=false)
	private String name;
/**
 * Null args constructor for the UserRole object.
 */
	public UserRole() {
		super();
	}
	/**
	 * Constructor that sets the name of the UserRole object based on the supplied name.
	 * @param name - String value that represents the name of the UserRole.
	 */
	public UserRole(String name) {
		super();
		this.name = name;
	}
/**
 * Method that retrieves the ID of the UserRole object.
 * @return ID - int value that represents the ID of the UserRole.
 */
	public int getID() {
		return ID;
	}
/**
 * Method that sets the ID of the UserRole object.
 * @param iD - int value of the id to be set.
 */
	public void setID(int iD) {
		ID = iD;
	}
/**
 * Method that retrieves the name of the UserRole object. 
 * @return name - String value that represents the name of the UserRole.
 */
	public String getName() {
		return name;
	}
/**
 * Method that sets the name of the UserRole object.
 * @param name - String value of the name to be set.
 */
	public void setName(String name) {
		this.name = name;
	}
/**
 * Method that returns the string representation of the UserRole object.
 */
	@Override
	public String toString() {
		return "UserRole [ID=" + ID + ", name=" + name + "]";
	}
	
	
}
