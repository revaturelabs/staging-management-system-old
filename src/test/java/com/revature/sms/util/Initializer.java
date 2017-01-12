package com.revature.sms.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.UserRole;
import com.revature.sms.testLibs.AssociateAttendanceDataManager;
import com.revature.sms.testLibs.UserDataManager;

public class Initializer {

	private Initializer() {
		super();
	}
	
	public static UserDataManager initializeUsers(int userNumber) {
		UserDataManager udm = new UserDataManager();
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
			BatchType batchType = new BatchType(batchTypes.get(i));
			UserRole userRole = new UserRole(userRoles.get(i));
			System.out.println("here2");
			udm.createTestAssociate(usernames.get(i), firstNames.get(i), lastNames.get(i), unhashedPasswords.get(i), batchType, attendance, tasks, userRole);
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
