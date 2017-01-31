package com.revature.sms;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.domain.dao.UserRepo;

public class AssociateT extends AbstractT {
	//Tests that when different types of users login and logout, they are navigated to the correct pages
	
	@Test
	public void testLoginHeaderLogout() {
		String expectedValue = expected.getProperty("associatePg");
		LoginHeaderLogoutTemplate(asp, inputs.getProperty("javaUN"), inputs.getProperty("PW"), expectedValue);
		LoginHeaderLogoutTemplate(asp, inputs.getProperty("sdetUN"), inputs.getProperty("PW"), expectedValue);
		LoginHeaderLogoutTemplate(asp, inputs.getProperty("dotnetUN"), inputs.getProperty("PW"), expectedValue);
	}
	
	@Test
	public void testPasswordChange() {
		PasswordChangeTemplate(adp, inputs.getProperty("javaUN"), inputs.getProperty("PW"), inputs.getProperty("PW2"));
	}
	
	@Test
	public void testCancelButtons() {
		lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
		//asp.carefulClick("certification");
		//scw.carefulClick("cancel");
		asp.carefulClick("settings");
		cpw.carefulClick("cancel");
		asp.carefulClick("reportBug");
		rbw.carefulClick("cancel");
	}
	
	
	//Makes sure the current week is shown on the associate page when you log in.
	@Test
	public void testDefaultWeek() {
		lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
		
		ArrayList<String> expectedMonthDays = new ArrayList<String>();
		expectedMonthDays.add(expected.getProperty("Mon"));
		expectedMonthDays.add(expected.getProperty("Tue"));
		expectedMonthDays.add(expected.getProperty("Wed"));
		expectedMonthDays.add(expected.getProperty("Thu"));
		expectedMonthDays.add(expected.getProperty("Fri"));
		
		ArrayList<String> actualMonthDays = asp.goThroughWeek();
		Assert.assertEquals(expectedMonthDays, actualMonthDays);
	}
	
	@Test
	public void testBugReport() {
		lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
		asp.carefulClick("reportBug");
		Assert.assertTrue(rbw.verify());
		rbw.messageBox.sendKeys(inputs.getProperty("bugReport"));
		rbw.enterName.sendKeys(inputs.getProperty("bugReportName"));
		rbw.enterEmail.sendKeys(inputs.getProperty("bugReportEmail"));
		rbw.webInfo.click();
		rbw.cancel.click();
	}
	
	
	//Maybe we should wait until there is a way to unschedule certifications on the website 
	//before completing this test.
	/*
	@Test
	public void testCertificationScheduling() {
		lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
		asp.carefulClick("certification");
		Assert.assertTrue(scw.verify());
		scw.enterDate.sendKeys(inputs.getProperty("certDate"));
		scw.enterNote.sendKeys(inputs.getProperty("certNote"));
		scw.carefulClick("submit");
	}
	*/
	
	@Autowired
	protected UserRepo ur;
	protected AssociateAttendanceRepo aar;
	
	//This is Corey's work on issue SMS-85.
	@Ignore
	@Test
	public void testAssociateAttendanceView() {
		String username = inputs.getProperty("javaUN");
		String password = inputs.getProperty("PW");
		lp.login(username, password);
		
		User user = ur.findByUsername(username);
		System.out.println(user.getUsername());
		System.out.println(user.getFirstName());
		user.getAttendance().size();
		List<AssociateAttendance> attendanceList = user.getAttendance();
		for (AssociateAttendance associateAttendance : attendanceList) {
			System.out.println(associateAttendance);
		}
		//ArrayList<Timestamp> expectedDates = new ArrayList<Timestamp>();
		//for (AssociateAttendance a:attendanceList) {
			//Timestamp ts = a.getDate();
			//System.out.println(ts);
			//expectedDates.add(a.getDate());
		//}
		
		
		//Convert the associate's dates of attendance to the same format that is used
		//by the web application.
		/*
		ArrayList<String> expectedMonthDays = new ArrayList<String>();
		for (String s:expectedDates) {
			String monthDay;
			if (s.charAt(0) == '0') {
				monthDay = s.substring(1, 5);
			} else {
				monthDay = s.substring(0, 5);
			}
			expectedMonthDays.add(monthDay);
		}
		*/
		
		/*
		String week;
		String weekBefore;
		//This do-while loop iterates through each available week on the associate page by 
		//carefulClicking the arrow icons
		do {
			week = asp.weekOf.getText();
			ArrayList<String> actualMonthDays = asp.goThroughWeek();
			ArrayList<String> icons = asp.goThroughWeekIcons();
			int aCount = 0;  
			//The outer loop goes through each day of the week.
			for (String a:actualMonthDays) {
				int eCount = 0;
				//The inner loop compares the date on the website with the attendance date from the excel file.
				for (String e:expectedMonthDays) {
					//If the two dates are equal, then we check whether the website displays the attendance information correctly.
					if (a.equals(e)) {
						Boolean ci = Boolean.parseBoolean(checkIns.get(eCount));
						Boolean v = Boolean.parseBoolean(verifications.get(eCount));
						String icon = icons.get(aCount);
						if (ci && v) { //If the associate is checked in and verified, the double check icon should be displayed, which is represented by the string done_all in the html.
							Assert.assertEquals("done_all", icon);
						} else if (ci && !v) {
							Assert.assertEquals("done", icon);
						} else if (!ci && !v) {
							Assert.assertEquals("close", icon);
						} else {
							System.out.println("You did not fill out the associate attendance spreadsheet correctly.");
						}						
					} 
					eCount++;
				}
				aCount++;
				//These count variables ensure that this test compares the data from the excel sheet with the appropriate data from the web page.
			}
			asp.carefulClick("prevWeek");
			weekBefore = asp.weekOf.getText();
		} while (!week.equals(weekBefore));
		*/
	}

		
	public void testAssociatePageToastContainer() {
		
	}
	
	
	
	
	@After
	public void after() {
		if (asp.verify()) {
			asp.carefulClick("logout");
		} 
	}
	
	
}
