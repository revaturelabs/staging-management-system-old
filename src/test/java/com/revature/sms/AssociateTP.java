package com.revature.sms;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.AssociateTaskType;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.JobAssignment;
import com.revature.sms.domain.JobEvent;
import com.revature.sms.domain.JobEventType;
import com.revature.sms.domain.TechnicalSkills;
import com.revature.sms.domain.User;
import com.revature.sms.util.Utils;

//This test class makes sure that the data displayed in each of the panels on the associate page (expectedInfo) 
//matches the data in the database (actualInfo).
public class AssociateTP extends AbstractT {

	@Test
	public void testUserInfoPanel() {
		lp.login(un, pw);
		asp.openPanel(asp.userInfoPanel);
		User user = ur.findByUsername(un);
		
		ArrayList<String> expectedInfo = new ArrayList<String>();
		String title1 = expected.getProperty("nameTitle");
		String fname = user.getFirstName();
		String lname = user.getLastName();
		String nameRow = title1+": "+fname+" "+lname;
		expectedInfo.add(nameRow);
		String title2 = expected.getProperty("usernameTitle");
		String usernameRow = title2+": "+un;
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
		
		ArrayList<String> actualInfo = asp.goThroughUserInfo();
		Assert.assertEquals(expectedInfo, actualInfo);
		asp.closePanel(asp.userInfoPanel);
	}
	
	
	@Test
	public void testSkillsPanel() {
		lp.login(un, pw);
		asp.openPanel(asp.skillsPanel);
		User user = ur.findByUsername(un);
		
		ArrayList<String> expectedSkills = new ArrayList<String>();
		Set<TechnicalSkills> skillset = user.getSkill();
		Iterator<TechnicalSkills> itr = skillset.iterator();
		while (itr.hasNext()) {
			TechnicalSkills ts = itr.next();
			String skill = ts.getSkill();
			expectedSkills.add(skill);
		}
		
		ArrayList<String> actualSkills = asp.goThroughSkills();
		System.out.println(expectedSkills);
		System.out.println(actualSkills);
		Collections.sort(expectedSkills);
		Collections.sort(actualSkills);

		Assert.assertEquals(expectedSkills, actualSkills);
		asp.closePanel(asp.skillsPanel);
	}
	
	
	@Test
	public void testEventsPanel() {
		lp.login(un, pw);
		asp.openPanel(asp.eventsPanel);
		
		User user = ur.findByUsername(un);
		
		List<JobEvent> eventObjects = user.getEvents();
		int i=0;
		while (i < eventObjects.size()) {
			JobEvent e = eventObjects.get(i);
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
			Set<String> keys = expectedInfo.keySet();
			
			HashMap<String, String> actualInfo = asp.goThroughEvent(i+1);
			String subheader = asp.eventsPanel.findElement(By.xpath("md-expansion-panel-expanded/md-expansion-panel-content/md-list/div["+(i+1)+"]")).getText();  
			Assert.assertEquals(subheader, expectedInfo.get("eventType"));
			
			Iterator<String> itr = keys.iterator();
			while (itr.hasNext()) {
				String key = itr.next();
				Assert.assertEquals(expectedInfo.get(key), actualInfo.get(key));
			}
			i++;
		}
		
		asp.closePanel(asp.eventsPanel);
	}
	
	@Test
	public void testTasksPanel() {
		lp.login(un, pw);
		asp.openPanel(asp.tasksPanel);
		User user = ur.findByUsername(un);
		
		List<AssociateTask> taskObjects = user.getTasks();
		for (AssociateTask task:taskObjects) {
			HashMap<String, String> expectedInfo = new HashMap<String, String>();
			AssociateTaskType taskType = task.getTaskType();
			Timestamp ts = task.getDate();
			LocalDate date = Utils.convertTimestampToLocalDate(ts);
			
			expectedInfo.put("taskDate", date.toString());  //The comparison will mess up if the taskDate is before 5am in the database
			expectedInfo.put("taskNote", task.getNote());
			expectedInfo.put("taskType", taskType.getType());
			expectedInfo.put("taskStatus", String.valueOf(task.getPassed()));
			
			Set<String> keys = expectedInfo.keySet();
			HashMap<String, String> actualInfo; 
			
			if ("Certification".equals(expectedInfo.get("taskType"))) {
				actualInfo = asp.findCertification(date);
			} else {
				actualInfo = asp.findPanel();
			}
				
			Iterator<String> itr = keys.iterator();
			while (itr.hasNext()) {
				String key = itr.next();
				if (!("taskNote".equals(key) && "Panel".equals(expectedInfo.get("taskType")))) {
					Assert.assertEquals(expectedInfo.get(key), actualInfo.get(key));
				}
			}
		}
		asp.closePanel(asp.tasksPanel);
	}
		
}
