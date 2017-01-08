package com.revature.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ASSOCIATE_TASK_TYPE")
public class AssociateTaskType {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "taskTypeSeq", sequenceName = "TASK_TYPE_SEQ")
	@GeneratedValue(generator = "taskTypeSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	@Column(name = "TYPE", unique=true, nullable=false)
	private String type;

	public AssociateTaskType() {
		super();
	}

	public AssociateTaskType(String type) {
		super();
		this.type = type;
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
		return "TaskType [ID=" + ID + ", type=" + type + "]";
	}
	
	
}
