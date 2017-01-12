package com.revature.sms.testlibs;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.AssociateTaskType;
import com.revature.sms.domain.dao.AssociateTasksRepo;

/**
 * 
 * @author Sage
 *
 * 
 * class AssociateTaskDataManager is used to create new authorization associateTasks and delete them after
 * the tests are executed. It's essential that all setup methods record any changes made to
 * the database so that cleanup methods can be called to undo those changes.  
 * 
 */

@Service
public class AssociateTaskDataManager {

	private List<AssociateTask> createdAssociateTasks = new ArrayList<>();
	
	@Autowired
	private AssociateTasksRepo atr;
	
	
	/**
	 * createTestAssociateTask is a setup method that is called to create an authorization associateTask for testing.
	 * The cleanup method RemoveAllTestAssociateTasks must be called after an instance of this class is done being used.
	 * 
	 * @return The associateTask object that is created in the database
	 */
	
	public AssociateTask createTestAssociateTask(AssociateTaskType taskType, Timestamp date, String note){
		AssociateTask newAssociateTask = new AssociateTask(taskType, date, note);
		atr.save(newAssociateTask);
		createdAssociateTasks.add(newAssociateTask);
		return newAssociateTask;
	}
	
	
	
	
	
	
	/**
	 * A cleanup method that must be called when an instance of the class is done being used.
	 */
	
	public void removeAllTestAssociateTasks(){
		for(AssociateTask i:createdAssociateTasks){
			atr.delete(i);
		}
		createdAssociateTasks.clear();
	}
	
}