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

/**
 * 
 * Object that tracks the current tasks an associate is working on.
 *
 */
@Entity
@Table(name="ASSOCIATE_TASK")
public class AssociateTask {

	/**
	 * int value that represents the primary key of the record.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "associateTaskSeq", sequenceName = "ASSOCIATE_TASK_SEQ")
	@GeneratedValue(generator = "associateTaskSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	/**
	 * User object that represents the associate in this task record.
	 */
	@ManyToOne
	@JoinColumn(name="TASK_TYPE")
	private AssociateTaskType taskType;
	
	/**
	 * Date object that represents the date an associate started a task.
	 */
	@Column(name="TASK_DATE", nullable=false)
	private Date date;
	
	/**
	 * String value that allows an admin to include an optional note regarding the associate's task.
	 */
	@Column(name="Note")
	private String note;

	/**
	 * Default constructor for AssociateTask.
	 */
	public AssociateTask() {
		super();
	}
	
	/**
	 * Parameterized constructor for AssociateTask. 
	 * @param taskType - AssociateTaskType object that refers to the type of task an associate is working on  
	 * @param date - Date object that represents the date an associate started a task
	 * @param note - String value that allows an admin to include an optional note regarding the associate's task
	 */

	public AssociateTask(AssociateTaskType taskType, Date date, String note) {
		super();
		this.taskType = taskType;
		this.date = date;
		this.note = note;
	}

	/**
	 * Get method for ID.
	 * @return ID - int value that represents the primary key of the record
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Set method for ID
	 * @param iD - int value that represents the primary key of the record
	 */
	public void setID(int iD) {
		ID = iD;
	}
	public AssociateTaskType getTaskType() {
		return taskType;
	}
	
	/**
	 * Set method for taskType.
	 * @param taskType - AssociateTaskType object that refers to the type of task an associate is working on
	 */
	public void setTaskType(AssociateTaskType taskType) {
		this.taskType = taskType;
	}
	
	/**
	 * Get method for date.
	 * @return date - Date object that represents the date an associate started a task
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Set method for date.
	 * @param date - Date object that represents the date an associate started a task
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Get method for note.
	 * @return note - String value that allows an admin to include an optional note regarding the associate's task
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * Set method for note.
	 * @param note - String value that allows an admin to include an optional note regarding the associate's task
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * toString method for AssociateTask.
	 * 
	 */
	@Override
	public String toString() {
		return "AssociateTasks [ID=" + ID + ", taskType=" + taskType.getType() + ", date=" + date
				+ ", note=" + note + "]";
	}
	
	
}
