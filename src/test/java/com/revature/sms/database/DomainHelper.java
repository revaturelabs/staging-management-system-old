package com.revature.sms.database;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.AssociateTaskType;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.JobAssignment;
import com.revature.sms.domain.JobEvent;
import com.revature.sms.domain.JobEventType;
import com.revature.sms.domain.Project;
import com.revature.sms.domain.ProjectUser;
import com.revature.sms.domain.TechnicalSkills;
import com.revature.sms.domain.User;
import com.revature.sms.util.Utils;

import static java.time.temporal.ChronoUnit.DAYS;

//This class contains methods that take in database domain objects and format their information so that they
//can be compared to what is found on an SMS web page.
public class DomainHelper {

	//On the web page, user info is laid out in rows, which each contain both data and a title explaining 
	//what that data is. Each row is saved into a hash, with the title and data separated by a colon and space
	//for easy comparison later.
	public static ArrayList<String> getExpectedUserInfo(User user, Properties expected) {
		ArrayList<String> expectedInfo = new ArrayList<String>();
		String title1 = expected.getProperty("title1");
		String nameRow = title1+": "+user.getFirstName()+" "+user.getLastName();
		expectedInfo.add(nameRow);
		String title2 = expected.getProperty("title2");
		String usernameRow = title2+": "+user.getUsername();
		expectedInfo.add(usernameRow);
		String title3 = expected.getProperty("title3");
		BatchType bt = user.getBatchType();
		String batchType = bt.getType();
		String batchCurriculumRow = title3+": "+batchType;
		expectedInfo.add(batchCurriculumRow);
		String title5 = expected.getProperty("title5");
		LocalDate dateObject = Utils.convertTimestampToLocalDate(user.getGraduationDate());
		String gDateRow = title5+": "+dateObject.toString();
		expectedInfo.add(gDateRow);
		
		/*
		String title4 = expected.getProperty("title4");
		Trainer trainer = user.getTrainer();
		String trainerRow = title4+": "+trainer.getFirstName()+" "+trainer.getLastName();
		expectedInfo.add(trainerRow);
		
		String title6 = expected.getProperty("title6");
		LocalDate msd = Utils.convertTimestampToLocalDate(user.getGraduationDate());
		LocalDate today = LocalDate.now();
		int daysBetween = (int) DAYS.between(msd, today);
		daysBetween++;
		String marketingDaysRow = title6+": "+daysBetween+" days";
		
		String title7 = expected.getProperty("title7");
		String marketingStatusRow = title7+": "+user.getMarketingStatus().getName();
		*/
		
		return expectedInfo;
	}
	
	public static ArrayList<String> getExpectedSkills(User user) {
		ArrayList<String> expectedInfo = new ArrayList<String>();
		Set<TechnicalSkills> skillset = user.getSkill();
		
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		//This if statement is a convoluted way of making up for the fact that having no skills is displayed
		//differently on the admin page panel.
		if (skillset.size() == 0 && stackTraceElements[2].getClassName().contains("Admin")) {
			expectedInfo.add("User has not added any skills.");
		}
		
		
		Iterator<TechnicalSkills> itr = skillset.iterator();
		while (itr.hasNext()) {
			TechnicalSkills ts = itr.next();
			String skill = ts.getSkill();
			expectedInfo.add(skill);
		}
		return expectedInfo;
	}
	
	
	public static HashMap<String, String> getExpectedEvent(JobEvent e) {
		HashMap<String, String> expectedInfo = new HashMap<String, String>();
		//Events have three nested objects that have information needed by this program.
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
	
	public static HashMap<String, String> getExpectedTask(AssociateTask task) {
		HashMap<String, String> expectedInfo = new HashMap<String, String>();
		
		Timestamp ts = task.getDate();
		LocalDate date = Utils.convertTimestampToLocalDate(ts);
		expectedInfo.put("taskDate", date.toString());  //The comparison may be inaccurate if the taskDate is before 5am in the database
		
		expectedInfo.put("taskNote", task.getNote());
		
		AssociateTaskType taskType = task.getTaskType();
		expectedInfo.put("taskType", taskType.getType());
		
		expectedInfo.put("taskStatus", String.valueOf(task.getPassed()));  //Because task statuses are returned by the domain object as a boolean
		return expectedInfo;
	}
	
	//This data is checked against everything that can be found in the Associate Information Table on the 
	//Super Admin page.
	public static HashMap<String, String> getExpectedAssociateInfo(User user) {
		HashMap<String, String> expectedInfo = new HashMap<String, String>();
		expectedInfo.put("fullName", user.getFirstName()+" "+user.getLastName());
		expectedInfo.put("batchType", user.getBatchType().getType());
		expectedInfo.put("trainer", user.getTrainer().getFirstName()+" "+user.getTrainer().getLastName());
		
		LocalDate msd = Utils.convertTimestampToLocalDate(user.getGraduationDate());
		expectedInfo.put("marketingStartDate", msd.toString());
		
		//Days on market is not directly stored in the database. It is calculated on the fly using the
		//marketing start date and today's date.
		LocalDate today = LocalDate.now();
		int daysBetween = (int) DAYS.between(msd, today);
		daysBetween++;
		expectedInfo.put("daysOnMarket", daysBetween+" days");
		
		expectedInfo.put("marketingStatus", user.getMarketingStatus().getName());
		
		List<AssociateTask> tasks = user.getTasks();
		for (AssociateTask task:tasks) {
			String td = Utils.convertTimestampToSimpleDate(task.getDate());
			HashMap<String, String> expectedTaskInfo = getExpectedTask(task);
			
			//Tasks are handled quite differently in the table depending on their type.
			if ("Panel".equals(expectedTaskInfo.get("taskType"))) {
				//QUESTION: How are panels that have not happened yet displayed?
				if ("true".equals(expectedTaskInfo.get("taskStatus"))) {
					expectedInfo.put("panelStatus", "check_circle");
					expectedInfo.put("panelDate", "");
				//QUESTION: Why is the date only displayed when the user has not passed their panel?
				} else {  
					expectedInfo.put("panelStatus", "not_interested");
					expectedInfo.put("panelDate", td.toString());
				}
			}
			//QUESTION: How do I know which certification is the primary certification?
			//QUESTION: Shouldn't the table say whether the certification has been passed or not?
			if ("Certification".equals(expectedTaskInfo.get("taskType"))) {
				expectedInfo.put("certName", task.getNote());
				expectedInfo.put("certDate", td.toString());	
			}
		}
		
		//QUESTION: Why is only the last of a user's projects shown?
		List<ProjectUser> pus = user.getProject();
		if (pus.isEmpty()) {
			expectedInfo.put("project", "No project");
		} else {
			ProjectUser pu = pus.get(pus.size()-1); //last of a user's projects
			Project project = pu.getProject();
			expectedInfo.put("project", project.getName());
		}
		
		return expectedInfo;
	}
	
	
}
