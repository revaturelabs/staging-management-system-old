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

//This test class tests features that are particular to an Associate's home page.
public class AssociateT extends AbstractT {
	

	//Tests that when different types of users login and logout, they are navigated to the correct pages.
	@Test
	public void testLoginHeaderLogout() {
		String expectedValue = expected.getProperty("associatePg");
		LoginHeaderLogoutTemplate(inputs.getProperty("javaUN"), pw, expectedValue);
		LoginHeaderLogoutTemplate(inputs.getProperty("sdetUN"), pw, expectedValue);
		LoginHeaderLogoutTemplate(inputs.getProperty("dotnetUN"), pw, expectedValue);
	}
	
	
	//Tests that you can enter and exit all of the frames that pop up when you click icons on the dashboard or
	//the "Found a Bug" button.
	@Test
	public void testCancelButtons() {
		lp.login(un, pw);
		asp.certification.click();
		if (!(expected.getProperty("noMoreCerts").equals(lp.getToastMessage()))) {
			scw.cancel.click();
		}
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
		
		//Gets the date of every day this week from expected.properties...
		ArrayList<MonthDay> expectedMonthDays = new ArrayList<MonthDay>();
		expectedMonthDays.add(MonthDay.parse(expected.getProperty("Mon")));
		expectedMonthDays.add(MonthDay.parse(expected.getProperty("Tue")));
		expectedMonthDays.add(MonthDay.parse(expected.getProperty("Wed")));
		expectedMonthDays.add(MonthDay.parse(expected.getProperty("Thu")));
		expectedMonthDays.add(MonthDay.parse(expected.getProperty("Fri")));

		
		ArrayList<MonthDay> actualMonthDays = asp.goThroughWeek();
		//...and compares them to the dates displayed on the website
		Assert.assertEquals(expectedMonthDays, actualMonthDays);
	}
	
	//Asserts that the date displayed under the calendar is the same as the one displayed below the word Monday.
	@Test
	public void testWeekFooter()  {
		lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
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

	
	//Tests all of the check in functionality using the icon on the dashboard and asserts that the toast
	//notifications display the correct messages in each step of the process.
	@Test
	public void testCheckInCheckOut() {
		lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
		 //I need this long wait because the two toast notifications you can get after logging in can disrupt the other toast notification assertions in this method
		//Utils.attemptWait(1000);   
		String checked = expected.getProperty("checkedIn");
		String notChecked = expected.getProperty("notCheckedIn");
		String icon = asp.checkincheckout.getText();
		Assert.assertEquals(notChecked, icon);  //The test will fail if the user being tested has already checked in beforehand.
		asp.checkincheckout.click();
		Assert.assertEquals(expected.getProperty("checkInSuccess"), asp.getToastMessage());
		icon = asp.checkincheckout.getText(); //The text related to the dashboard icon changes whenever its clicked on, so the reference to the WebElement must be reset in this situation.
		Assert.assertEquals(checked, icon);   //This line appears to fail when there is not enough wait time after a click.
		asp.checkincheckout.click();
		//Utils.attemptWait(500);
		driver.findElement(By.xpath("/html/body/div[5]/md-dialog/md-dialog-actions/button[1]")).click();  //Chooses no in the pop-up that asks whether you really want to un-check in.
		icon = asp.checkincheckout.getText();
		Assert.assertEquals(checked, icon);
		asp.checkincheckout.click();
		driver.findElement(By.xpath("/html/body/div[5]/md-dialog/md-dialog-actions/button[2]")).click();  //Chooses yses in the pop-up that asks whether you really want to un-check in.
		//Utils.attemptWait(500);
		Assert.assertEquals(expected.getProperty("checkOutSuccess"), asp.getToastMessage());
		icon = asp.checkincheckout.getText();
		Assert.assertEquals(notChecked, icon);
	}
	
	//Tests that the user's attendance data that is displayed on the website matches the attendance data contained
	//in the database.
	@Test
	public void testAssociateAttendanceView() {
		lp.login(un, pw);
		asp.verify();

		User user = ur.findByUsername(un);
		List<AssociateAttendance> attendanceList = user.getAttendance();
		HashMap<MonthDay, String> expectedStatuses = new HashMap<MonthDay, String>();  //Database data
		
		//Goes through the User's list of attendance objects and gathers information from them in a way that allows
		//it to be compared to the website.
		for (AssociateAttendance a : attendanceList) {
			Timestamp ts = a.getDate();
			String fullTime = ts.toString();
			String monthDay = fullTime.substring(5, 10);
			String formattedMonthDay = "--" + monthDay;
			MonthDay md = MonthDay.parse(formattedMonthDay);

			String status;
			boolean ci = a.isCheckedIn();
			boolean v = a.isVerified();
			//Depending on whether an associate is checked in and verified, a certain icon should be displayed, 
			//and each icon is associated with a different string in the html.
			if (v) { 
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
		//This do-while loop uses the navigation buttons on the calendar to go through all of the weeks in 
		//reverse order until it receives the toast notification that says you can't go back any further.
		do {
			ArrayList<MonthDay> monthDays = asp.goThroughWeek();
			ArrayList<String> icons = asp.goThroughWeekIcons();
			HashMap<MonthDay, String> actualStatuses = new HashMap<MonthDay, String>();  //Website data
			
			int i = 0;
			//This while loop organizes the dates, check-ins, and verifications from the website into a HashMap.
			while (i < 5) {
				actualStatuses.put(monthDays.get(i), icons.get(i));
				i++;
			}

			//This for-each loop iterates through both HashMaps using every matching monthDay key, and asserts
			//that the values are equal.
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
			if (expected.getProperty("tooFarBack").equals(asp.getToastMessage())) {
				flag = false;
			}
		} while (flag);
	}
	
	
	
	
	
	
	/*
	//This test adds skills and confirms that these added skills are reflected in the database.
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
	//This test schedules a certification.
	@Test
	public void testCertificationScheduling() {
	 	lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
		asp.click("certification"); Assert.assertTrue(scw.verify());
		scw.enterDate.sendKeys(inputs.getProperty("certDate"));
		scw.enterNote.sendKeys(inputs.getProperty("certNote"));
		scw.click("submit"); }
	*/
	

}