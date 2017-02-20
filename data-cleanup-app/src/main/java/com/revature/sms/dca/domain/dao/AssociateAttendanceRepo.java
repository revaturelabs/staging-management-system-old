package com.revature.sms.dca.domain.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.dca.domain.AssociateAttendance;

/**
 * 
 * DAO repository for AssociateAttendance.
 *
 */
@Repository
public interface AssociateAttendanceRepo extends JpaRepository<AssociateAttendance, Integer> {
	
	/**
	 * Returns a list of attendance records for a specific date.
	 * @param date Date object for the date of desired attendance records
	 * @return List of all attendance records for associates on the given date
	 */
	List<AssociateAttendance> findByDate(Timestamp date);
}
