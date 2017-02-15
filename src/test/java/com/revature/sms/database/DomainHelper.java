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

import static com.revature.sms.database.DomainHelper.getExpectedTask;
import static java.time.temporal.ChronoUnit.DAYS;

//This class contains methods that take in database domain objects and format their information so that they
//can be compared to what is found on an SMS web page.
public class DomainHelper {

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
		String title4 = expected.getProperty("title4");
		LocalDate dateObject = Utils.convertTimestampToLocalDate(user.getGraduationDate());
		String gDateRow = title4+": "+dateObject.toString();
		expectedInfo.add(gDateRow);
		return expectedInfo;
	}
	
	public static ArrayList<String> getExpectedSkills(User user) {
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
	
	public static HashMap<String, String> getExpectedEvent(JobEvent e) {
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
	
	public static HashMap<String, String> getExpectedTask(AssociateTask task, String date) {
		HashMap<String, String> expectedInfo = new HashMap<String, String>();
		AssociateTaskType taskType = task.getTaskType();
		expectedInfo.put("taskDate", date);  //The comparison will mess up if the taskDate is before 5am in the database
		expectedInfo.put("taskNote", task.getNote());
		expectedInfo.put("taskType", taskType.getType());
		expectedInfo.put("taskStatus", String.valueOf(task.getPassed()));
		return expectedInfo;
	}
	
	public static HashMap<String, String> getExpectedAssociateInfo(User user) {
		HashMap<String, String> expectedInfo = new HashMap<String, String>();
		expectedInfo.put("fullName", user.getFirstName()+" "+user.getLastName());
		expectedInfo.put("batchType", user.getBatchType().getType());
		expectedInfo.put("trainer", user.getTrainer().getFirstName()+" "+user.getTrainer().getLastName());
		
		Timestamp gts = user.getGraduationDate();
		LocalDate msd = Utils.convertTimestampToLocalDate(gts);
		expectedInfo.put("marketingStartDate", msd.toString());
		
		LocalDate today = LocalDate.now();
		int daysBetween = (int) DAYS.between(msd, today);
		expectedInfo.put("daysOnMarket", daysBetween+" days");
		
		expectedInfo.put("marketingStatus", user.getMarketingStatus().getName());
		
		List<AssociateTask> tasks = user.getTasks();
		for (AssociateTask task:tasks) {
			Timestamp tts = task.getDate();
			LocalDate td = Utils.convertTimestampToLocalDate(tts);
			HashMap<String, String> expectedTaskInfo = getExpectedTask(task, td.toString());
			if ("Panel".equals(expectedTaskInfo.get("taskType"))) {
				if ("true".equals(expectedTaskInfo.get("taskStatus"))) {
					expectedInfo.put("panelStatus", "check_circle");
				} else {  //the user failed their panel
					expectedInfo.put("panelStatus", "not_interested");
				}
				//QUESTION: Why is the date only displayed when the user has not passed their panel?
				expectedInfo.put("panelDate", td.toString());
			}
			//QUESTION: How do I know which certification is the primary certification?
			//QUESTION: Shouldn't the table say whether the certification has been passed or not?
			if ("Certification".equals(expectedTaskInfo.get("taskType"))) {
				expectedInfo.put("certName", task.getNote());
				expectedInfo.put("certDate", td.toString());	
			}
		}
		
		//QUESTION: Why is only one of a user's projects shown?
		List<ProjectUser> pus = user.getProject();
		ProjectUser pu = pus.get(0);
		Project project = pu.getProject();
		expectedInfo.put("project", project.getName());
		
		return expectedInfo;
	}
	
	
}
