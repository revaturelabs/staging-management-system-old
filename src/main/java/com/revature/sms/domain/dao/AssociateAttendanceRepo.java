package com.revature.sms.domain.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.User;

/**
 * 
 * DAO repository for AssociateAttendance.
 *
 */
@Repository
public interface AssociateAttendanceRepo extends JpaRepository<AssociateAttendance, Integer> {
	
	/**
	 * Returns a list of attendance records for a specific date.
	 * @param date - Date object for the date of desired attendance records
	 * @return List of all attendance records for associates on the given date
	 */
	List<AssociateAttendance> findByDate(Date date);
	
	/**
	 * Returns a list of attendance records for a specific associate.
	 * @param associate - User object for the associate of desired attendance records
	 * @return List of all attendance records for the given associate
	 */ 
	List<AssociateAttendance> findByAssociate(User associate);
}
