package com.revature.sms.testLibs;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.dao.AssociateAttendanceRepo;

/**
 * 
 * @author Sage
 *
 * 
 * class AssociateAttendanceDataManager is used to create new authorization associateAttendances and delete them after
 * the tests are executed. It's essential that all setup methods record any changes made to
 * the database so that cleanup methods can be called to undo those changes.  
 * 
 */

public class AssociateAttendanceDataManager {

	List<AssociateAttendance> createdAssociateAttendances = new ArrayList<>();
	
	@Autowired
	AssociateAttendanceRepo tr;
	
	
	/**
	 * createTestAssociateAttendance is a setup method that is called to create an authorization associateAttendance for testing.
	 * The cleanup method RemoveAllTestAssociateAttendances must be called after an instance of this class is done being used.
	 * 
	 * @return The associateAttendance object that is created in the database
	 */
	
	public AssociateAttendance createTestAssociateAttendance(Date date, boolean checkedIn, boolean verified, String note){
		AssociateAttendance newAssociateAttendance = new AssociateAttendance(date, checkedIn, verified, note);
		tr.save(newAssociateAttendance);
		createdAssociateAttendances.add(newAssociateAttendance);
		return newAssociateAttendance;
	}
	
	
	
	
	
	
	/**
	 * A cleanup method that must be called when an instance of the class is done being used.
	 */
	
	public void removeAllTestAssociateAttendances(){
		for(AssociateAttendance i:createdAssociateAttendances){
			tr.delete(i);
		}
		createdAssociateAttendances.clear();
	}
	
}
