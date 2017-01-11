package com.revature.sms.testlibs;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.sms.domain.JobAssignment;
import com.revature.sms.domain.JobEvent;
import com.revature.sms.domain.JobEventType;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.JobEventRepo;

/**
 * 
 * @author Sage
 *
 * 
 * class JobEventDataManager is used to create new authorization jobEvents and delete them after
 * the tests are executed. It's essential that all setup methods record any changes made to
 * the database so that cleanup methods can be called to undo those changes.  
 * 
 */

public class JobEventDataManager {

	List<JobEvent> createdJobEvents = new ArrayList<>();
	
	@Autowired
	JobEventRepo jer;
	
	
	/**
	 * createTestJobEvent is a setup method that is called to create an authorization jobEvent for testing.
	 * The cleanup method RemoveAllTestJobEvents must be called after an instance of this class is done being used.
	 * 
	 * @return The jobEvent object that is created in the database
	 */
	
	public JobEvent createTestJobEvent(User associate, JobAssignment assignment, JobEventType type, Date date, String note){
		JobEvent newJobEvent = new JobEvent(associate, assignment, type, date, note);
		jer.save(newJobEvent);
		createdJobEvents.add(newJobEvent);
		return newJobEvent;
	}
	
	
	
	
	
	
	/**
	 * A cleanup method that must be called when an instance of the class is done being used.
	 */
	
	public void removeAllTestJobEvents(){
		for(JobEvent i:createdJobEvents){
			jer.delete(i);
		}
		createdJobEvents.clear();
	}
	
}