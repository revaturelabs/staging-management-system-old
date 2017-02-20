package com.revature.sms;

import static com.revature.sms.database.DomainHelper.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.JobEvent;
import com.revature.sms.domain.User;
import com.revature.sms.util.Utils;

public class AdminTP extends AbstractT {
	
	//The admin page version of the user info panel has rows with the titles "Trainer"
	//and "Days in marketing" while the associate page version does not. Will this be true in the 
	//future?
	@Ignore
	@Test
	public void testUserInfoPanels() {
		lp.login(un, pw);
		for (User user:users) {
			if ("associate".equals(user.getUserRole().getName())) {
				revealCollapsedPanelsForUser(user.getUsername());
				
				adp.openPanel(adp.userInfoPanel);
				ArrayList<String> expectedInfo = getExpectedUserInfo(user, expected);
				ArrayList<String> actualInfo = adp.goThroughUserInfo();
				Assert.assertEquals(expectedInfo, actualInfo);
				adp.closePanel(adp.userInfoPanel);
				
			}
		}		
	}
	
	@Test
	public void testSkillsPanels() {
		lp.login(un, pw);
		for (User user:users) {
			if ("associate".equals(user.getUserRole().getName())) {
				revealCollapsedPanelsForUser(user.getUsername());
				adp.openPanel(adp.skillsPanel);
				
				ArrayList<String> expectedSkills = getExpectedSkills(user);
				ArrayList<String> actualSkills = asp.goThroughSkills();
				Collections.sort(expectedSkills);
				Collections.sort(actualSkills);

				Assert.assertEquals(expectedSkills, actualSkills);
				adp.closePanel(adp.skillsPanel);
			}
		}	
	}	
	
	
	//The date formatting for the events on the Associate Page needs to be fixed before this can work well.
	@Ignore
	@Test
	public void testEventsPanels() {
		lp.login(un, pw);
		for (User user:users) {
			if ("associate".equals(user.getUserRole().getName())) {
				revealCollapsedPanelsForUser(user.getUsername());
				adp.openPanel(adp.eventsPanel);
				
				List<JobEvent> eventObjects = user.getEvents();
				int i=0;
				
				while (i < eventObjects.size()) {
					JobEvent e = eventObjects.get(i);
					HashMap<String, String> expectedInfo = getExpectedEvent(e);
					HashMap<String, String> actualInfo = asp.goThroughEvent(i+1);  
					
					//Makes sure that job event type is correctly displayed above the rest of the information 
					String subheader = asp.eventsPanel.findElement(By.xpath("md-expansion-panel-expanded/md-expansion-panel-content/md-list/div["+(i+1)+"]")).getText();  
					Assert.assertEquals(subheader, expectedInfo.get("eventType"));
					compareHashes(expectedInfo, actualInfo);
					i++;
				}
				
				adp.closePanel(adp.eventsPanel);
			}
		}	
	}	
	
	
	//Associate certifications are not being displayed in the panels yet.
	@Ignore
	@Test
	public void testTasksPanels() {
		lp.login(un, pw);
		for (User user:users) {
			if ("associate".equals(user.getUserRole().getName())) {
				revealCollapsedPanelsForUser(user.getUsername());
				adp.openPanel(adp.tasksPanel);
				
				List<AssociateTask> tasks = user.getTasks();
				for (AssociateTask task:tasks) {
					Timestamp ts = task.getDate();
					LocalDate date = Utils.convertTimestampToLocalDate(ts);
					HashMap<String, String> expectedInfo = getExpectedTask(task);
					HashMap<String, String> actualInfo = asp.findTask(expectedInfo.get("taskType"), date);
					compareHashes(expectedInfo, actualInfo);
				}
				
				adp.closePanel(adp.tasksPanel);
			}
		}	
	}	
	
		
}
