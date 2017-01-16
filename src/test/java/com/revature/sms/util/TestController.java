package com.revature.sms.util;

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
import com.revature.sms.testlibs.AssociateAttendanceDataManager;
import com.revature.sms.testlibs.UserDataManager;

@Service
public class TestController {

	@Autowired
	private UserDataManager udm;
	
	@Autowired
	private BatchTypeRepo btr;
	
	@Autowired
	private UserRoleRepo urr;
	
	private TestController() {
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
		
		int i = 0;
		while (i < columnNumber) {
			//System.out.println(i);
			//System.out.println(usernames.get(i));
			BatchType batchType = btr.findByType(batchTypes.get(i));
			UserRole userRole = urr.findByName(userRoles.get(i));
			
			udm.createTestUser(usernames.get(i), firstNames.get(i), lastNames.get(i), unhashedPasswords.get(i), batchType, attendance, tasks, userRole);
			i++;
		}
		return udm;
	}
	
	
	public UserDataManager initializeAttendance(int columnNumber) {
		ExcelHelper eh = new ExcelHelper(columnNumber);
		
		ArrayList<String> dates = eh.getValues("attendanceDate");
		ArrayList<String> checkIns = eh.getValues("checkedIn");
		ArrayList<String> verifications = eh.getValues("verified");
		ArrayList<String> notes = eh.getValues("attendanceNote");
	
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
			
			udm.editTestUser(i, "", "", "", "", new BatchType(), listOfOne, new ArrayList<AssociateTask>(), new UserRole());
			i++;
			
		}
		return udm;
	}
	
	
	public void clearData() {
		udm.removeAllTestUsers();
	}
	
}
