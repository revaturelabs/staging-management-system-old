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
@Table(name="ASSOCIATE_ATTENDANCE")
public class AssociateAttendance {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "associateAttendanceSeq", sequenceName = "ASSOCIATE_ATTENDANCE_SEQ")
	@GeneratedValue(generator = "associateAttendanceSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	@Column(name="ATTENDANCE_DATE", nullable=false)
	private Date date;

	@Column(name="CHECKED_IN", nullable=false)
	private boolean checkedIn;
	
	@Column(name="VERIFIED", nullable=false)
	private boolean verified;
	
	@Column(name="NOTE")
	private String note;

	public AssociateAttendance() {
		super();
	}

	public AssociateAttendance(Date date, boolean checkedIn, boolean verified, String note) {
		super();
		this.date = date;
		this.checkedIn = checkedIn;
		this.verified = verified;
		this.note = note;
	}
	
	public AssociateAttendance(int iD, User associate, Date date, boolean checkedIn, boolean verified, String note) {
		super();
		ID = iD;
		this.date = date;
		this.checkedIn = checkedIn;
		this.verified = verified;
		this.note = note;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isCheckedIn() {
		return checkedIn;
	}

	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "AssociateAttendance [ID=" + ID + ", date=" + date + ", checkedIn="
				+ checkedIn + ", verified=" + verified + ", note=" + note + "]";
	}
}