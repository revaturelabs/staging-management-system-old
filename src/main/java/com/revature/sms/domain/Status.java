package com.revature.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="STATUS")
public class Status {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "statusSeq", sequenceName = "STATUS_SEQ")
	@GeneratedValue(generator = "statusSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	@Column(name="STATUS_NAME", nullable=false)
	private String name;

	public Status() {
		super();
	}

	public Status(String name) {
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
		return "Status [ID=" + ID + ", name=" + name + "]";
	}

}
