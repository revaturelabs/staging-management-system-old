package com.revature.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="BATCH_TYPE")
public class BatchType {
	
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "batchTypeSeq", sequenceName = "BATCH_TYPE_SEQ")
	@GeneratedValue(generator = "batchTypeSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	@Column(name = "TYPE", unique=true, nullable=false)
	private String type;
	
	public BatchType() {
		super();
	}

	public BatchType(String type) {
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
		return "BatchType [ID=" + ID + ", type=" + type + "]";
	}

}
