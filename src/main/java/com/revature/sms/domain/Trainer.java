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
@Table(name="TRAINER")
public class Trainer {
	/**
	 * int value that represents the primary key of the table, ID is used to
	 * identify a specific Trainer object by the unique int value. Set up to
	 * automatically generate a value so that it doesn't have to be specified in
	 * a constructor or set manually.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "trainerSeq", sequenceName = "TRAINER_SEQ")
	@GeneratedValue(generator = "trainerSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	/**
	 * String that represents the first name of the Trainer object.
	 */
	@Column(name = "FIRST_NAME", nullable=false)
	private String firstName;
	/**
	 * String that represents the last name of the Trainer object.
	 */
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
/**
 * Null args constructor for the UserRole object.
 */
	public Trainer() {
		super();
	}

public Trainer(String firstName, String lastName) {
	super();
	this.firstName = firstName;
	this.lastName = lastName;
}

public int getID() {
	return ID;
}

public void setID(int iD) {
	ID = iD;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}


	
}
