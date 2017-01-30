package com.revature.sms;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.After;
import org.junit.Assert;

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
	}
	
	//blah
	
	
	//Makes sure the current week is shown on the associate page when you log in.
	@Test
	public void testDefaultWeek() {
		lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
		Assert.assertTrue(asp.verify());
		
		ArrayList<String> expectedMonthDays = new ArrayList<String>();
		expectedMonthDays.add(expected.getProperty("Mon"));
		expectedMonthDays.add(expected.getProperty("Tue"));
		expectedMonthDays.add(expected.getProperty("Wed"));
		expectedMonthDays.add(expected.getProperty("Thu"));
		expectedMonthDays.add(expected.getProperty("Fri"));
		
		ArrayList<String> actualMonthDays = asp.goThroughWeek();
		Assert.assertEquals(expectedMonthDays, actualMonthDays);
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
	
	
	//ERROR??? When I run this test, and an associate is set as checked in and not verified for a 
	//certain date, the associate is shown as verified for that date on the website (there is a double
	//checkmark), even though the attendance is correctly identified as not verified in the database.
	//Also, an extra junk attendance associated with the user is added to the database, and is also 
	//incorrectly displayed on the website.
	
	//This is Corey's work on issue SMS-85.
	/*
	@Ignore
	@Test
	public void testAssociateAttendanceView() {
		try {
			//Login as a test associate.
			ExcelHelper userGetter = new ExcelHelper("NewUsers");
			ArrayList<String> usernames = userGetter.getValues("username");
			ArrayList<String> passwords = userGetter.getValues("unhashedPassword");
			String ericUN = usernames.get(1);
			String ericPW = passwords.get(1);
			lp.login(ericUN, ericPW);
			Assert.assertTrue(asp.verify());
			
			//Determine what that associate's attendance is supposed to be using the Excel sheet
			//as a reference.
			ExcelHelper attendanceGetter = new ExcelHelper(ericUN);
			ArrayList<String> expectedDates = attendanceGetter.getValues("attendanceDate");
			ArrayList<String> checkIns = attendanceGetter.getValues("checkedIn");
			ArrayList<String> verifications = attendanceGetter.getValues("verified");
			//ArrayList<String> notes = attendanceGetter.getValues("attendanceNote");
			
			//Convert the associate's dates of attendance to the same format that is used
			//by the web application.
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
			
			//ERROR??? The website says you can't go back more than 3 weeks after trying to go back more than
			//four weeks.
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
		} catch (FilloException e) {}
	}
	*/
	
	
	//Corey's Test ideas
	public void negativeTestAssociateAttendanceView() {
		//Make sure icons are not displayed under dates when there is no associated attendance record.
		//Maybe this can be integrated into testAssociateAttendanceView.
	}
	
	
	public void testAssociatePageToastContainer() {
		
	}
	
	
	public void testAssociateCalendarNavigation() {
		//This is already done indirectly in testAssociateAttendanceView but maybe the navigation 
		//buttons should be directly tested too.
	}
	
	
	@After
	public void after() {
		if (asp.verify()) {
			asp.carefulClick("logout");
		} 
	}
	
	
}
