package com.revature.sms;

import java.sql.Timestamp;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.User;
import com.revature.sms.util.Utils;

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
		driver.switchTo().frame("atlwdg-frame");
		rbw.carefulClick("cancel");
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
	}
	
	@Test
	public void testBugReport() {
		lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
		asp.carefulClick("reportBug");
		driver.switchTo().frame("atlwdg-frame");
		
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
	
	
	
	
	//This is Corey's work on issue SMS-85.
	@Test
	public void testAssociateAttendanceView() {
		String username = inputs.getProperty("javaUN");
		String password = inputs.getProperty("PW");
		lp.login(username, password);
		asp.verify();
		
		User user = ur.findByUsername(inputs.getProperty("javaUN"));
		List<AssociateAttendance> attendanceList = user.getAttendance();
		HashMap<MonthDay, String> expectedStatuses = new HashMap<MonthDay, String>();
		for (AssociateAttendance a:attendanceList) {
			Timestamp ts = a.getDate();
			String fullTime = ts.toString();
			String monthDay = fullTime.substring(5,10);
			String formattedMonthDay = "--"+monthDay;
			MonthDay md = MonthDay.parse(formattedMonthDay);
			
			String status;
			boolean ci = a.isCheckedIn();
			boolean v = a.isVerified();
			if (v) { //If the associate is checked in and verified, the double check icon should be displayed, which is represented by the string done_all in the html.
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
		
		
		String week;
		String weekBefore;
		do {
			week = asp.weekOf.getText();
			ArrayList<MonthDay> monthDays = asp.goThroughWeek();
			ArrayList<String> icons = asp.goThroughWeekIcons();
			HashMap<MonthDay, String> actualStatuses = new HashMap<MonthDay, String>();
			int i=0;
			while (i<5) {
				actualStatuses.put(monthDays.get(i), icons.get(i));
				i++;
			}
			
			for (MonthDay md:monthDays) {
				String es = expectedStatuses.get(md);
				String as = actualStatuses.get(md);
				if (es == null) {
					es = "close";
				}
				Assert.assertEquals(es, as);
			}
		
			asp.carefulClick("prevWeek");
			weekBefore = asp.weekOf.getText();
		} while (!week.equals(weekBefore));

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
