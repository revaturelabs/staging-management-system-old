package com.revature.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="JOB_EVENT_TYPE")
public class JobEventType {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "jobEventTypeSeq", sequenceName = "JOB_EVENT_TYPE_SEQ")
	@GeneratedValue(generator = "jobEventTypeSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	@Column(name = "TYPE", unique=true, nullable=false)
	private String type;

	public JobEventType(String type) {
		super();
		this.type = type;
	}

	public JobEventType() {
		super();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "JobEventType [ID=" + ID + ", type=" + type + "]";
	}
	
	
}
