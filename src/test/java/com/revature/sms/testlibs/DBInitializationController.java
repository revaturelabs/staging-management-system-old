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
	
	public UserDataManager initializeUsers(int columnNumber) {
		ExcelHelper eh = new ExcelHelper(columnNumber);
		
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
		while (i < columnNumber) {
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
	public UserDataManager initializeAttendance(int columnNumber) {
		ExcelHelper eh = new ExcelHelper(columnNumber);
		
		ArrayList<String> dates = eh.getValues("attendanceDate");
		ArrayList<String> checkIns = eh.getValues("checkedIn");
		ArrayList<String> verifications = eh.getValues("verified");
		ArrayList<String> notes = eh.getValues("attendanceNote");
	
		//This loop gives an attendance object to all recently created test users
		int i = 0;
		while (i < columnNumber) {
			String date = dates.get(i);
			Timestamp ts = Utils.convertDate(date);
			String checkedIn = checkIns.get(i);
			boolean ci = Boolean.parseBoolean(checkedIn);
			String verified = verifications.get(i);
			boolean v = Boolean.parseBoolean(verified);
			String note = notes.get(i);
			
			AssociateAttendance aa = new AssociateAttendance(ts, ci, v, note);
			ArrayList<AssociateAttendance> listOfOne = new ArrayList<AssociateAttendance>();
			listOfOne.add(aa);
			
			udm.editTestUser(i, "", "", "", "", new BatchType(), listOfOne, new ArrayList<AssociateTask>(), new UserRole(), new Timestamp(0));
			i++;
			
		}
		return udm;
	}
	
	
	public void clearData() {
		udm.removeAllTestUsers();
	}
	
}
