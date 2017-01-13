package com.revature.sms.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.UserRole;
import com.revature.sms.domain.dao.BatchTypeRepo;
import com.revature.sms.domain.dao.UserRoleRepo;
import com.revature.sms.testlibs.UserDataManager;

@Service
public class EnterData {

	@Autowired
	private UserDataManager udm;
	
	@Autowired
	private BatchTypeRepo btr;
	
	@Autowired
	private UserRoleRepo urr;
	
	private EnterData() {
		super();
	}
	
	public UserDataManager initializeUsers(int userNumber) {
		ExcelHelper eh = new ExcelHelper(userNumber);
		
		ArrayList<String> usernames = eh.getValues("username");
		ArrayList<String> firstNames = eh.getValues("firstName");
		ArrayList<String> lastNames = eh.getValues("lastName");
		ArrayList<String> unhashedPasswords = eh.getValues("unhashedPassword");
		ArrayList<String> batchTypes = eh.getValues("batchType");
		List<AssociateAttendance> attendance = new ArrayList<AssociateAttendance>();
		List<AssociateTask> tasks = new ArrayList<AssociateTask>();
		ArrayList<String> userRoles = eh.getValues("userRole");
		
		int i = 0;
		while (i < userNumber) {
			System.out.println(i);
			System.out.println(usernames.get(i));
			BatchType batchType = btr.findByType(batchTypes.get(i));
			UserRole userRole = urr.findByName(userRoles.get(i));
			System.out.println(batchType.getType());
			udm.createTestUser(usernames.get(i), firstNames.get(i), lastNames.get(i), unhashedPasswords.get(i), batchType, attendance, tasks, userRole);
			i++;
		}
		
		
		
		
		
		
		return udm;
	}
	
	/*
	public static void initializeAttendance(Properties input) {
		AssociateAttendanceDataManager aadm = new AssociateAttendanceDataManager();
		
		String dateString = ExcelHelper.getValues("date");
		String myDate = dateString;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(myDate);
		} catch (ParseException e) {
			Logger.getRootLogger().debug("You got a ParseException", e);
		}
		long millis = date.getTime();
		Timestamp ts = new Timestamp(millis);
	}
	*/
	
	
}
