package com.revature.sms;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.AssociateTaskType;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.JobAssignment;
import com.revature.sms.domain.JobEvent;
import com.revature.sms.domain.JobEventType;
import com.revature.sms.domain.TechnicalSkills;
import com.revature.sms.domain.User;
import com.revature.sms.util.Utils;


public class AssociateT extends AbstractT {
	// Tests that when different types of users login and logout, they are
	// navigated to the correct pages

	
	@Test
	public void testLoginHeaderLogout() {
		String expectedValue = expected.getProperty("associatePg");
		LoginHeaderLogoutTemplate(inputs.getProperty("javaUN"), pw, expectedValue);
		LoginHeaderLogoutTemplate(inputs.getProperty("sdetUN"), pw, expectedValue);
		LoginHeaderLogoutTemplate(inputs.getProperty("dotnetUN"), pw, expectedValue);
	}
	
	
	@Test
	public void testCancelButtons() {
		lp.login(un, pw);
		asp.certification.click();
		scw.cancel.click();
		asp.settings.click();
		sw.cancel.click();
		asp.reportBug.click();
		driver.switchTo().frame("atlwdg-frame");
		rbw.cancel.click();
	}

	//Makes sure the current week is shown on the associate page when you log in.
	
	@Test
	public void testDefaultWeek() {
		lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
		
		ArrayList<MonthDay> expectedMonthDays = new ArrayList<MonthDay>();
		expectedMonthDays.add(MonthDay.parse(expected.getProperty("Mon")));
		expectedMonthDays.add(MonthDay.parse(expected.getProperty("Tue")));
		expectedMonthDays.add(MonthDay.parse(expected.getProperty("Wed")));
		expectedMonthDays.add(MonthDay.parse(expected.getProperty("Thu")));
		expectedMonthDays.add(MonthDay.parse(expected.getProperty("Fri")));

		ArrayList<MonthDay> actualMonthDays = asp.goThroughWeek();
		Assert.assertEquals(expectedMonthDays, actualMonthDays);
		
		String monday = asp.attendanceCells.get(0).getText();
		if (monday.contains("/0")) {
			monday = monday.replace("0", "");
		}
		String[] splitMonday = monday.split("\n");
		String mondate = splitMonday[1];
		
		
		String week = asp.weekOf.getText();
		String[] splitWeek = week.split(" ");
		String dateOfWeek = splitWeek[2];
		
		Assert.assertEquals(mondate, dateOfWeek);
	}

	
	@Test
	public void testCheckInCheckOut() {
		lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
		String checked = expected.getProperty("checkedIn");
		String notChecked = expected.getProperty("notCheckedIn");
		String icon = asp.checkincheckout.getText();
		Assert.assertEquals(notChecked, icon);
		asp.checkincheckout.click();
		Assert.assertEquals(expected.getProperty("checkInSuccess"), asp.getToastMessage());
		icon = asp.checkincheckout.getText();
		Assert.assertEquals(checked, icon);   //This line appears to fail when there is not enough wait time after a click
		asp.checkincheckout.click();
		driver.findElement(By.xpath("/html/body/div[5]/md-dialog/md-dialog-actions/button[1]")).click();
		icon = asp.checkincheckout.getText();
		Assert.assertEquals(checked, icon);
		asp.checkincheckout.click();
		driver.findElement(By.xpath("/html/body/div[5]/md-dialog/md-dialog-actions/button[2]")).click();
		Assert.assertEquals(expected.getProperty("checkOutSuccess"), asp.getToastMessage());
		icon = asp.checkincheckout.getText();
		Assert.assertEquals(notChecked, icon);
	}
	
	
	@Test
	public void testAssociateAttendanceView() {
		lp.login(un, pw);
		asp.verify();

		User user = ur.findByUsername(un);
		List<AssociateAttendance> attendanceList = user.getAttendance();
		HashMap<MonthDay, String> expectedStatuses = new HashMap<MonthDay, String>();
		for (AssociateAttendance a : attendanceList) {
			Timestamp ts = a.getDate();
			String fullTime = ts.toString();
			String monthDay = fullTime.substring(5, 10);
			String formattedMonthDay = "--" + monthDay;
			MonthDay md = MonthDay.parse(formattedMonthDay);

			String status;
			boolean ci = a.isCheckedIn();
			boolean v = a.isVerified();
			if (v) { 
				//If the associate is checked in and verified, the double
				//check icon should be displayed, which is represented
				//by the string done_all in the html.
				status = "done_all";
			} else if (ci && !v) {
				status = "done";
			} else if (!ci && !v) {
				status = "close";
			} else {
				status = "???";
			}
			expectedStatuses.put(md, status);
		}
		
		boolean flag = true;
		do {
			ArrayList<MonthDay> monthDays = asp.goThroughWeek();
			ArrayList<String> icons = asp.goThroughWeekIcons();
			HashMap<MonthDay, String> actualStatuses = new HashMap<MonthDay, String>();
			int i = 0;
			while (i < 5) {
				actualStatuses.put(monthDays.get(i), icons.get(i));
				i++;
			}

			for (MonthDay md : monthDays) {
				String es = expectedStatuses.get(md);
				String as = actualStatuses.get(md);
				if (es == null) {
					if (md.compareTo(MonthDay.now()) < 0) {
						es = "close";
					} else {
						es = "";
					}
				}
				Assert.assertEquals(es, as);
			}

			asp.prevWeek.click();
			if (asp.getToastMessage() != null) {
				if (expected.getProperty("tooFarBack").equals(asp.getToastMessage())) {
					flag = false;
				}
			}
		} while (flag);
	}
	
	
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
	
	
	
	
	/*
	@Test
	public void testAddSkill() {
		lp.login(un, pw);
		asp.settings.click();
		Assert.assertTrue(sw.verify());
		
		ArrayList<String> expectedSkills = new ArrayList<String>();
		expectedSkills.add(inputs.getProperty("javaSkill"));
		expectedSkills.add(inputs.getProperty("jdbcSkill"));
		expectedSkills.add(inputs.getProperty("pythonSkill"));
		
		sw.chooseSkill(expectedSkills.get(0));
		sw.addSkill.click();
		sw.chooseSkill(expectedSkills.get(1));
		sw.addSkill.click();
		sw.chooseSkill(expectedSkills.get(2));
		sw.addSkill.click();
		
		List<WebElement> addedSkillList = sw.getAddedSkills();
		int i=0;
		for (WebElement addedSkill:addedSkillList) {
			String skill = addedSkill.getText().trim();
			System.out.println(skill);
			String[] skillPieces = skill.split("\n");
			Assert.assertEquals(expectedSkills.get(i), skillPieces[0]);
			i++;
		}
		
		sw.saveSkills.click();
		Assert.assertEquals(expected.getProperty("skillUpdate"), asp.getToastMessage());
		sw.cancel.click();
		Assert.assertTrue(asp.verify());
		
		User user = ur.findByUsername(un);
		ArrayList<String> actualSkills = new ArrayList<String>();
		Set<TechnicalSkills> skillset = user.getSkill();
		Iterator<TechnicalSkills> itr = skillset.iterator();
		while (itr.hasNext()) {
			TechnicalSkills ts = itr.next();
			String actualSkill = ts.getSkill();
			actualSkills.add(actualSkill);
		}
		Collections.sort(expectedSkills);
		Collections.sort(actualSkills);
		Assert.assertEquals(expectedSkills, actualSkills);
		
		asp.settings.click();
		Assert.assertTrue(sw.verify());
		
		
		List<WebElement> deletionIcons = sw.getDeletionIcons();
		for (WebElement icon:deletionIcons) {
			icon.click();
		}
		sw.saveSkills.click();
		Assert.assertEquals(expected.getProperty("skillUpdate"), asp.getToastMessage());
		sw.cancel.click();
		Assert.assertTrue(asp.verify());
		
		
		//I'd like to check and make sure that the database has been emptied, but when I try to
		//do that, I get a StackOverflowError.
		
		System.out.println("Here1");
		User userAgain = ur.findByUsername(un);
		System.out.println(userAgain.getLastName());
		System.out.println("Here2");
		Set<TechnicalSkills> nullset = userAgain.getSkill();
		System.out.println(nullset);
		System.out.println("Here3");
		Assert.assertNull(nullset);
		System.out.println("Here4");
		
	}
	*/
	
		
	// Maybe we should wait until there is a way to unschedule certifications on
	// the website before completing this test.
	/*
	@Test
	public void testCertificationScheduling() {
	 	lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
		asp.click("certification"); Assert.assertTrue(scw.verify());
		scw.enterDate.sendKeys(inputs.getProperty("certDate"));
		scw.enterNote.sendKeys(inputs.getProperty("certNote"));
		scw.click("submit"); }
	*/
	

}