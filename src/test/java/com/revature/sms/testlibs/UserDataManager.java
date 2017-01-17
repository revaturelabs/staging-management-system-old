package com.revature.sms.testlibs;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.Token;
import com.revature.sms.domain.User;
import com.revature.sms.domain.UserRole;
import com.revature.sms.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.domain.dao.AssociateTasksRepo;
import com.revature.sms.domain.dao.TokenRepo;
import com.revature.sms.domain.dao.UserRepo;

import static com.revature.sms.util.Utils.hashPassword;

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
	
	@Autowired
	private AssociateAttendanceRepo aar;
	
	@Autowired
	private AssociateAttendanceRepo aar;
	
	@Autowired
	private AssociateTasksRepo atr;
	
	@Autowired
	private TokenRepo tr;
	
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
			List<AssociateAttendance> attendance, List<AssociateTask> tasks, UserRole userRole, Timestamp graduationDate){
		User newUser = new User(username, firstName, lastName, hashPassword(unhashedPassword), batchType, attendance, tasks, userRole, graduationDate);
		return createTestUser(newUser);
	}
	
	
		//Corey's Method
		//Changes one or more of the fields of a user. These changes are reflected in both the database and this
	    //this UserDataManager object.
		public void editTestUser(int userIndex, String username, String firstName, String lastName, String unhashedPassword, BatchType batchType,
				List<AssociateAttendance> attendance, List<AssociateTask> tasks, UserRole userRole) {
			User createdUser = createdUsers.get(userIndex);
			if (username != "") {
				createdUser.setUsername(username);
			}
			if (firstName != "") {
				createdUser.setFirstName(firstName);
			}
			if (lastName != "") {
				createdUser.setLastName(lastName);
			}
			if (unhashedPassword != "") {
				createdUser.setLastName(hashPassword(unhashedPassword));
			}
			if (batchType.getType() != null) {
				createdUser.setBatchType(batchType);
			}
			if (!attendance.isEmpty()) {
				createdUser.setAttendance(attendance);
			}
			if (!tasks.isEmpty()) {
				createdUser.setTasks(tasks);
			}
			if (userRole.getName() != null) {
				createdUser.setUserRole(userRole);
			}
			
			ur.save(createdUser);
			createdUsers.remove(userIndex);
			createdUsers.add(userIndex, createdUser);
			
			
			/*
			User recreatedUser = ur.findByUsername(createdUser.getUsername());
			List<AssociateAttendance> al = recreatedUser.getAttendance();
			
			//Why do I get a LazyInitializationException here. How do I prevent it? 
			System.out.println(al.get(0).getID());
			
			
			System.out.println("OOGLYBOOGLY: "+createdUsers.get(userIndex).getUsername());
			System.out.println();
			for (User user:createdUsers) {
				System.out.println("BEFORE");
				System.out.println("Username: "+user.getUsername());
				if (!user.getAttendance().isEmpty()) {
					System.out.println("Attendance ID: "+user.getAttendance().get(0).getID());
				}
				System.out.println();
			}
			
			createdUsers.remove(userIndex);
			createdUsers.add(userIndex, recreatedUser);
			
			
			for (User user:createdUsers) {
				System.out.println("AFTER");
				System.out.println("Username: "+user.getUsername());
				if (!user.getAttendance().isEmpty()) {
					System.out.println("Attendance ID: "+user.getAttendance().get(0).getID());
				}
				System.out.println();
			}
			*/
			
			
		}
	
	
	
	/**
	 * A cleanup method that must be called when an instance of the class is done being used.
	 */
	
	public void removeAllTestUsers(){
		for (User u:createdUsers){
			//System.out.println("Deleting User: "+u.getUsername());
			List<AssociateAttendance> attendanceList = u.getAttendance();
			for(AssociateAttendance a:attendanceList) {
				//System.out.println("Deleting attendance record with id of: "+a.getID());
				aar.delete(a);
			}
			
			ur.delete(u);
			
			for (AssociateAttendance a : currentUser.getAttendance()) {
				aar.delete(a);
			}
			
			
			
			for (AssociateTask a : currentUser.getTasks()) {
				atr.delete(a);
			}
			
			List<Token> tList = tr.getByUser(i);
			tList.size();
			for(Token a : tList){
				tr.delete(a);
			}
			
		}
		createdUsers.clear();
	}
	
	
	
	
	
	
}
