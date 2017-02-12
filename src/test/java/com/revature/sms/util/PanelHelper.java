package com.revature.sms.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.AssociateTaskType;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.JobAssignment;
import com.revature.sms.domain.JobEvent;
import com.revature.sms.domain.JobEventType;
import com.revature.sms.domain.TechnicalSkills;
import com.revature.sms.domain.User;

public class PanelHelper {

	public static ArrayList<String> getExpectedUserInfo(Properties expected, User user) {
		ArrayList<String> expectedInfo = new ArrayList<String>();
		String title1 = expected.getProperty("nameTitle");
		String fname = user.getFirstName();
		String lname = user.getLastName();
		String nameRow = title1+": "+fname+" "+lname;
		expectedInfo.add(nameRow);
		String title2 = expected.getProperty("usernameTitle");
		String usernameRow = title2+": "+user.getUsername();
		expectedInfo.add(usernameRow);
		String title3 = expected.getProperty("batchCurriculumTitle");
		BatchType bt = user.getBatchType();
		String batchType = bt.getType();
		String batchCurriculumRow = title3+": "+batchType;
		expectedInfo.add(batchCurriculumRow);
		String title4 = expected.getProperty("graduationDateTitle");
		LocalDate dateObject = Utils.convertTimestampToLocalDate(user.getGraduationDate());
		String gDateRow = title4+": "+dateObject.toString();
		expectedInfo.add(gDateRow);
		return expectedInfo;
	}
	
	public static ArrayList<String> getExpectedSkills(Properties expected, User user) {
		ArrayList<String> expectedInfo = new ArrayList<String>();
		Set<TechnicalSkills> skillset = user.getSkill();
		Iterator<TechnicalSkills> itr = skillset.iterator();
		while (itr.hasNext()) {
			TechnicalSkills ts = itr.next();
			String skill = ts.getSkill();
			expectedInfo.add(skill);
		}
		return expectedInfo;
	}
	
	public static HashMap<String, String> getExpectedEvent(Properties expected, User user, JobEvent e) {
		HashMap<String, String> expectedInfo = new HashMap<String, String>();
		JobAssignment a = e.getAssignment();
		expectedInfo.put("companyName", a.getCompanyName());
		expectedInfo.put("companyLocation", a.getLocation());
		expectedInfo.put("jobTitle", a.getJobTitle());
		
		JobEventType t = e.getType();
		expectedInfo.put("eventType", t.getType());
		
		Timestamp ts = e.getDate();
		LocalDate date = Utils.convertTimestampToLocalDate(ts);
		expectedInfo.put("eventDate", date.toString());
		
		expectedInfo.put("eventNote", e.getNote());
		expectedInfo.put("eventLocation", e.getLocation());
		return expectedInfo;
	}
	
	public static HashMap<String, String> getExpectedTask(Properties expected, User user, AssociateTask task, String date) {
		HashMap<String, String> expectedInfo = new HashMap<String, String>();
		AssociateTaskType taskType = task.getTaskType();
		expectedInfo.put("taskDate", date);  //The comparison will mess up if the taskDate is before 5am in the database
		expectedInfo.put("taskNote", task.getNote());
		expectedInfo.put("taskType", taskType.getType());
		expectedInfo.put("taskStatus", String.valueOf(task.getPassed()));
		return expectedInfo;
	}
	
	
}
