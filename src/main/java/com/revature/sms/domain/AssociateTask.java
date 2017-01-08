package com.revature.sms.domain;

import java.sql.Date;

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
@Table(name="ASSOCIATE_TASKS")
public class AssociateTask {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "associateTasksSeq", sequenceName = "ASSOCIATE_TASKS_SEQ")
	@GeneratedValue(generator = "associateTasksSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	@ManyToOne
	@JoinColumn(name="ASSOCIATE")
	private User associate;
	
	@ManyToOne
	@JoinColumn(name="TASK_TYPE")
	private TaskType taskType;
	
	@Column(name="TASK_DATE", nullable=false)
	private Date date;
	
	@Column(name="Note")
	private String note;

	public AssociateTask() {
		super();
	}

	public AssociateTask(User associate, TaskType taskType, Date date, String note) {
		super();
		this.associate = associate;
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

	public User getAssociate() {
		return associate;
	}

	public void setAssociate(User associate) {
		this.associate = associate;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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
		return "AssociateTasks [ID=" + ID + ", associate=" + associate.getUsername() + ", taskType=" + taskType.getType() + ", date=" + date
				+ ", note=" + note + "]";
	}
	
	
}
