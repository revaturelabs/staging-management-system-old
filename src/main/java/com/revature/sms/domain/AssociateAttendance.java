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

/**
 * 
 * Object that tracks the daily attendance of a particular associate.
 *
 */

@Entity
@Table(name="ASSOCIATE_ATTENDANCE")
public class AssociateAttendance {

	/**
	 * int value that represents the primary key of the record.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "associateAttendanceSeq", sequenceName = "ASSOCIATE_ATTENDANCE_SEQ")
	@GeneratedValue(generator = "associateAttendanceSeq", strategy = GenerationType.SEQUENCE)
	private int ID;

	/**
	 * Date object that represents the date of attendance.
	 */
	@Column(name="ATTENDANCE_DATE", nullable=false)
	private Timestamp date;

	/**
	 * Boolean value representing if the associate has logged in.
	 */
	@ManyToOne
	@JoinColumn(name="CHECKED_IN", nullable=false)
	private Status checkedIn;
	
	/**
	 * Boolean value that represents whether an admin has verified an associates attendance.
	 */
	@Column(name="VERIFIED", nullable=false)
	private boolean verified;
	
	/**
	 * String value that allows an admin to include an optional note regarding the associate's attendance.
	 */
	@Column(name="NOTE")
	private String note;

	/**
	 * Default constructor for AssociateAttendance.
	 */
	public AssociateAttendance() {
		super();
	}
	
	/**
	 * Parameterized constructor for AssociateAttendance where ID is automatically generated.
	 * @param date Timestamp object that represents the date of attendance 
	 * @param checkedIn Boolean value representing if the associate has logged in 
	 * @param verified Boolean value that represents whether an admin has verified an associates attendance
	 * @param note String value that allows admin to include an optional note regarding the associates attendance 
	 */

	public AssociateAttendance(Timestamp date, Status checkedIn, boolean verified, String note) {

		super();
		this.date = date;
		this.checkedIn = checkedIn;
		this.verified = verified;
		this.note = note;
	}


	/**
	 * Get method for ID
	 * @return ID int value that represents the primary key of the record 
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Set method for ID.
	 * @param iD int value that represents the primary key of the record 
	 */
	public void setID(int iD) {
		ID = iD;
	}

/**
 * Get method for date	
 * @return Timestamp object that represents the date of attendance.
 */
	public Timestamp getDate() {
		return date;
	}
	/**
	 * Set method for date.
	 * @param date Timestamp object that represents the date of attendance
	 */
	public void setDate(Timestamp date) {

		this.date = date;
	}

	/**
	 * Get method for checkedIn.
	 * @return checkedIn Boolean value representing if the associate has logged in
	 */
	public Status isCheckedIn() {
		return checkedIn;
	}
	
	/**
	 * Set method for checkedIn.
	 * @param checkedIn Boolean value representing if the associate has logged in
	 */
	public void setCheckedIn(Status checkedIn) {
		this.checkedIn = checkedIn;
	}

	/**
	 * Get method for verified.
	 * @return verified Boolean value that represents whether an admin has verified an associates attendance
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * Set method for verified.
	 * @param verified Boolean value that represents whether an admin has verified an associates attendance
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	/**
	 * Get method for note.
	 * @return note String value that allows admin to include an optional note regarding the associates attendance 
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * Set method for note.
	 * @param note String value that allows admin to include an optional note regarding the associates attendance
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * toString method for AssociateAttendance.
	 * 
	 */
	@Override
	public String toString() {
		return "AssociateAttendance [ID=" + ID + ", date=" + date + ", checkedIn="
				+ checkedIn + ", verified=" + verified + ", note=" + note + "]";
	}
}
