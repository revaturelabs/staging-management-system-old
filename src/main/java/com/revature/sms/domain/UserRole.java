package com.revature.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="USER_ROLE")
public class UserRole {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "userRoleSeq", sequenceName = "USER_ROLE_SEQ")
	@GeneratedValue(generator = "userRoleSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	@Column(name = "NAME", unique=true, nullable=false)
	private String name;
	
	/*This is how you create a boolean:
	@Column(name="ALIVE")
	private Boolean alive;
	*/

	public UserRole() {
	}

	public UserRole(int iD, String name) {
		super();
		ID = iD;
		this.name = name;
	}
	
	public UserRole(String name) {
		super();
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserRole [ID=" + ID + ", name=" + name + "]";
	}
	
	
}
