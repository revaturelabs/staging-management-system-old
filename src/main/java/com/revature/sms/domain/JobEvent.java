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
@Table(name="JOB_EVENT")
public class JobEvent {
	
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "jobEventSeq", sequenceName = "JOB_EVENT_SEQ")
	@GeneratedValue(generator = "jobEventSeq", strategy = GenerationType.SEQUENCE)
	private int ID;

	@ManyToOne
	@JoinColumn(name="ASSOCIATE")
	private User associate;
	
	@ManyToOne
	@JoinColumn(name="ASSIGNMENT")
	private JobAssignment assignment;
	
	@ManyToOne
	@JoinColumn(name="TYPE")
	private JobEventType type;
	
	@Column(name="EVENT_DATE", nullable=false)
	private Date date;
	
	@Column(name="NOTE")
	private String note;

	public JobEvent() {
		super();
	}

	public JobEvent(User associate, JobAssignment assignment, JobEventType type, Date date, String note) {
		super();
		this.associate = associate;
		this.assignment = assignment;
		this.type = type;
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

	public JobAssignment getAssignment() {
		return assignment;
	}

	public void setAssignment(JobAssignment assignment) {
		this.assignment = assignment;
	}

	public JobEventType getType() {
		return type;
	}

	public void setType(JobEventType type) {
		this.type = type;
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
		return "JobEvent [ID=" + ID + ", associate=" + associate.getUsername() + ", assignment=" + assignment + ", type=" + type.getType()
				+ ", date=" + date + ", note=" + note + "]";
	}
	
	
	
	
}
