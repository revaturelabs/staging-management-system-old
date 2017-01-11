package com.revature.sms.testlibs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.sms.domain.JobAssignment;
import com.revature.sms.domain.dao.JobAssignmentRepo;

/**
 * 
 * @author Sage
 *
 * 
 * class JobAssignmentDataManager is used to create new authorization jobAssignments and delete them after
 * the tests are executed. It's essential that all setup methods record any changes made to
 * the database so that cleanup methods can be called to undo those changes.  
 * 
 */

public class JobAssignmentDataManager {

	List<JobAssignment> createdJobAssignments = new ArrayList<>();
	
	@Autowired
	JobAssignmentRepo jaRepo;
	
	
	/**
	 * createTestJobAssignment is a setup method that is called to create an authorization jobAssignment for testing.
	 * The cleanup method RemoveAllTestJobAssignments must be called after an instance of this class is done being used.
	 * 
	 * @return The jobAssignment object that is created in the database
	 */
	
	public JobAssignment createTestJobAssignment(String companyName, String location, String jobTitle){
		JobAssignment newJobAssignment = new JobAssignment(companyName, location, jobTitle);
		jaRepo.save(newJobAssignment);
		createdJobAssignments.add(newJobAssignment);
		return newJobAssignment;
	}
	
	
	
	
	
	
	/**
	 * A cleanup method that must be called when an instance of the class is done being used.
	 */
	
	public void removeAllTestJobAssignments(){
		for(JobAssignment i:createdJobAssignments){
			jaRepo.delete(i);
		}
		createdJobAssignments.clear();
	}
	
}
