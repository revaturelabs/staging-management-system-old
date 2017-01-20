package com.revature.sms.testlibs;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.User;
import com.revature.sms.domain.UserRole;
import com.revature.sms.domain.dao.BatchTypeRepo;
import com.revature.sms.domain.dao.UserRoleRepo;
import com.revature.sms.util.ExcelHelper;
import com.revature.sms.util.Utils;

//This class can be used before tests to conveniently populate the database with test data   
@Service
public class DBInitializationController {

	@Autowired
	private UserDataManager udm;
	
	@Autowired
	private BatchTypeRepo btr;
	
	@Autowired
	private UserRoleRepo urr;
	
	private DBInitializationController() {
		super();
	}
	
	public UserDataManager initializeUsers() {
		ExcelHelper eh = new ExcelHelper("NewUsers");
		
		ArrayList<String> usernames = eh.getValues("username");
		ArrayList<String> firstNames = eh.getValues("firstName");
		ArrayList<String> lastNames = eh.getValues("lastName");
		ArrayList<String> unhashedPasswords = eh.getValues("unhashedPassword");
		ArrayList<String> batchTypes = eh.getValues("batchType");
		List<AssociateAttendance> attendance = new ArrayList<AssociateAttendance>();
		List<AssociateTask> tasks = new ArrayList<AssociateTask>();
		ArrayList<String> userRoles = eh.getValues("userRole");
		ArrayList<String> graduationDates = eh.getValues("graduationDate");
		
		//Each iteration of the loop corresponds to a new user that is added
		int i = 0;
		while (i < usernames.size()) {
			//Finds already existing batch types and user role types, which are added to the new user
			
			BatchType batchType = btr.findByType(batchTypes.get(i));
			UserRole userRole = urr.findByName(userRoles.get(i));
			String graduationDate = graduationDates.get(i);
			Timestamp gts = Utils.convertDate(graduationDate);
			
			udm.createTestUser(usernames.get(i), firstNames.get(i), lastNames.get(i), unhashedPasswords.get(i), batchType, attendance, tasks, userRole, gts);
			i++;
		}
		return udm;
	}
	
	//
	public UserDataManager initializeAttendance() {
		//This createdUsers array must be copied to avoid a ConcurrentModificationException
		ArrayList<User> arrayCopy = new ArrayList<User>();
		for (User user: udm.createdUsers) {
			arrayCopy.add(user);
		}
		
		//The outer loop looks through every new user.
		int i = 0;
		for (User u: arrayCopy) {
			String username = u.getUsername();
			ExcelHelper eh = new ExcelHelper(username);
			
			ArrayList<String> dates = eh.getValues("attendanceDate");
			ArrayList<String> checkIns = eh.getValues("checkedIn");
			ArrayList<String> verifications = eh.getValues("verified");
			ArrayList<String> notes = eh.getValues("attendanceNote");
		
			//The inner loop creates each user's attendance records and saves them to the database.
			int j = 0;
			ArrayList<AssociateAttendance> attendanceList = new ArrayList<AssociateAttendance>();
			while (j < dates.size()) {
				String date = dates.get(j);
				Timestamp ts = Utils.convertDate(date);
				String checkedIn = checkIns.get(j);
				boolean ci = Boolean.parseBoolean(checkedIn);
				String verified = verifications.get(j);
				boolean v = Boolean.parseBoolean(verified);
				String note = notes.get(j);
				
				AssociateAttendance aa = new AssociateAttendance(ts, ci, v, note);
				attendanceList.add(aa);				
				j++;
			}
			udm.editTestUser(i, "", "", "", "", new BatchType(), attendanceList, new ArrayList<AssociateTask>(), new UserRole(), new Timestamp(0));
			i++;
		}
		
		return udm;
	}
	
	public void clearData() {
		udm.removeAllTestUsers();
	}
	
}
