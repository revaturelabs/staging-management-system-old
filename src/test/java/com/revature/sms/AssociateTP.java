package com.revature.sms;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.JobEvent;
import com.revature.sms.domain.User;
import com.revature.sms.util.Utils;

import static com.revature.sms.database.DomainHelper.*;

//This test class makes sure that the data displayed in each of the panels on the associate page (expectedInfo) 
//matches the data in the database (actualInfo).
public class AssociateTP extends AbstractT {

	//Mixes together user related information (retrieved using the User domain object) with the
	//row names that are expected to be used in the user info panel (from expected.properties) and
	//combines them into strings that can be compared with the text that is actually visible on
	//the website (retrieved with Selenium)
	@Test
	public void testUserInfoPanel() {
		lp.login(un, pw);
		asp.openPanel(asp.userInfoPanel);
		User user = ur.findByUsername(un);
		
		ArrayList<String> expectedInfo = getExpectedUserInfo(user, expected);
		ArrayList<String> actualInfo = asp.goThroughUserInfo();
		Assert.assertEquals(expectedInfo, actualInfo);
		asp.closePanel(asp.userInfoPanel);
	}
	
	
	@Test
	public void testSkillsPanel() {
		lp.login(un, pw);
		asp.openPanel(asp.skillsPanel);
		User user = ur.findByUsername(un);
		
		//Iterates through the Set of skills of the logged-in user
		ArrayList<String> expectedSkills = getExpectedSkills(user);
		ArrayList<String> actualSkills = asp.goThroughSkills();
		
		//All data is transfered to sorted ArrayLists, since they can be easily compared
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
		//Each event from the database has a bunch of information that needs to be compared to what
		//is actually displayed on the website, so a HashMap is created for each event to organize
		//this information, and this while loop is used so the two representations of an event are
		//compared one at a time
		while (i < eventObjects.size()) {
			JobEvent e = eventObjects.get(i);
			HashMap<String, String> expectedInfo = getExpectedEvent(e);
			//i+1 is used to compensate for the difference between zero-based indexing in Java and one-based indexing in XPaths
			HashMap<String, String> actualInfo = asp.goThroughEvent(i+1);  
			
			//Makes sure that job event type is correctly displayed above the rest of the information 
			String subheader = asp.eventsPanel.findElement(By.xpath("md-expansion-panel-expanded/md-expansion-panel-content/md-list/div["+(i+1)+"]")).getText();  
			Assert.assertEquals(subheader, expectedInfo.get("eventType"));
			compareHashes(expectedInfo, actualInfo);
			i++;
		}
		asp.closePanel(asp.eventsPanel);
	}
	
	
	//Certifications and panels share a domain object and therefore have enough in common to be tested
	//using the same hash.
	@Test
	public void testTasksPanel() {
		lp.login(un, pw);
		asp.openPanel(asp.tasksPanel);
		User user = ur.findByUsername(un);
		
		List<AssociateTask> tasks = user.getTasks();
		for (AssociateTask task:tasks) {
			Timestamp ts = task.getDate();
			LocalDate date = Utils.convertTimestampToLocalDate(ts);
			HashMap<String, String> expectedInfo = getExpectedTask(task);
			HashMap<String, String> actualInfo = asp.findTask(expectedInfo.get("taskType"), date);
			compareHashes(expectedInfo, actualInfo);
		}
		asp.closePanel(asp.tasksPanel);
	}
		
}
