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

//This test class tests features on the front end that are particular to an Associate's home page.
public class AssociateT extends AbstractT {
	

	//Tests that when different types of users login and logout, they are navigated to the correct pages.
	@Test
	public void testAssociateLogin() {
		String expectedValue = expected.getProperty("associatePg");
		loginHeaderLogoutTemplate(inputs.getProperty("javaUN"), pw, expectedValue);
		loginHeaderLogoutTemplate(inputs.getProperty("sdetUN"), pw, expectedValue);
		loginHeaderLogoutTemplate(inputs.getProperty("dotnetUN"), pw, expectedValue);
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
		cancelCommonButtons();
	}

	//Makes sure the current week is shown on the associate page when you log in.
	@Test
	public void testDefaultWeek() {
		lp.login(un, pw);
		
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
		lp.login(un, pw);
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
		lp.login(un, pw);
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
	
	@Test
	public void testArrowButtons() {
		lp.login(un, pw);
		
		asp.openPanel(asp.tasksPanel);
		asp.openPanel(asp.eventsPanel);
		asp.openPanel(asp.skillsPanel);
		asp.openPanel(asp.userInfoPanel);
		
		asp.closePanel(asp.userInfoPanel);
		asp.closePanel(asp.skillsPanel);
		asp.closePanel(asp.eventsPanel);
		asp.closePanel(asp.tasksPanel);
		
		
		
		while (!expected.getProperty("tooFarForward").equals(asp.getToastMessage())) {
			asp.nextWeek.click();
		}
		
		while (!expected.getProperty("tooFarBack").equals(asp.getToastMessage())) {
			asp.prevWeek.click();
		}
	
	
	}

}