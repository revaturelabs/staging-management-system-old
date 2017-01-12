package com.revature.sms.testlibs;

import static com.revature.sms.StagingManagementSystemApplicationTests.hashPassword;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.User;
import com.revature.sms.domain.UserRole;
import com.revature.sms.domain.dao.UserRepo;

import static com.revature.sms.util.Utils.hashPassword;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Sage
 *
 * 
 * class UserDataManager is used to create new users and delete them after the tests are executed.
 * It's essential that all setup methods store any changes made to the database so that
 * cleanup methods can be called to undo those changes. 
 * 
 */

@Service
public class UserDataManager {
	
	/**
	 * createdUsers is a list to keep track of users created by this userDataManager class.
	 * Each element of createdUsers is removed from the database when removeAllTestUsers is called.
	 */
	
	private List<User> createdUsers = new ArrayList<>();
	
	@Autowired
	private UserRepo ur;
	
	
	
	
	/**
	 * createTestAdmin is a setup method that is called to create a user of Admin or Super Admin type for testing.
	 * The cleanup method RemoveAllTestUsers must be called after an instance of this class is done being used.
	 * 
	 * @return The user object that is created in the database
	 */
	
	public User createTestAdmin(String username, String firstName, String lastName, String unhashedPassword, UserRole userRole){
		User newUser = new User(username, firstName, lastName, hashPassword(unhashedPassword), userRole);
		
		return createTestUser(newUser);
	}
	
	/**
	 * createTestUser is a setup method that is called to create a user of any type for testing.
	 * The cleanup method RemoveAllTestUsers must be called after an instance of this class is done being used.
	 * 
	 * @param user The user object to be added to the database
	 * @return The user object that is created in the database
	 */
	
	public User createTestUser(User user){
		ur.save(user);
		createdUsers.add(user);
		return user;
	}
	
	
	/**
	 * createTestAdmin is a setup method that is called to create a user of Associate type for testing.
	 * The cleanup method RemoveAllTestUsers must be called after an instance of this class is done being used.
	 * 
	 * @return The user object that is created in the database
	 */
	
	public User createTestUser(String username, String firstName, String lastName, String unhashedPassword, BatchType batchType,
			List<AssociateAttendance> attendance, List<AssociateTask> tasks, UserRole userRole){
		User newUser = new User(username, firstName, lastName, hashPassword(unhashedPassword), batchType, attendance, tasks, userRole);
		System.out.println(ur.getClass());
		return createTestUser(newUser);
	}
	
	
	
	/**
	 * A cleanup method that must be called when an instance of the class is done being used.
	 */
	
	public void removeAllTestUsers(){
		for(User i:createdUsers){
			ur.delete(i);
		}
		createdUsers.clear();
	}
	
	
	
}