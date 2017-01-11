package com.revature.sms.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ASSOCIATE_TASK")
public class AssociateTask {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "associateTaskSeq", sequenceName = "ASSOCIATE_TASK_SEQ")
	@GeneratedValue(generator = "associateTaskSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	@ManyToOne
	@JoinColumn(name="TASK_TYPE")
	private AssociateTaskType taskType;
	
	@Column(name="TASK_DATE", nullable=false)
	private Timestamp date;
	
	@Column(name="Note")
	private String note;

	public AssociateTask() {
		super();
	}

	public AssociateTask(AssociateTaskType taskType, Timestamp date, String note) {
		super();
		this.taskType = taskType;
		this.date = date;
		this.note = note;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public AssociateTaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(AssociateTaskType taskType) {
		this.taskType = taskType;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

 	@Override
	public String toString() {
		return "AssociateTasks [ID=" + ID + ", taskType=" + taskType.getType() + ", date=" + date
				+ ", note=" + note + "]";
	}
}
