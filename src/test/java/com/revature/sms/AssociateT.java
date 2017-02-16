package com.revature.sms;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
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
			Assert.assertTrue(scw.verify());
			scw.cancel.click();
		}
		cancelCommonButtons();
	}
	
	//Asserts that the date displayed under the calendar is the same as the one displayed below the word Monday.
	@Test
	public void testWeekOf()  {
		lp.login(un, pw);
		String monday = hp.getThisMondayFromHeader();
		String week = Utils.getDateFromText(asp.weekOf.getText());
		Assert.assertEquals(monday, week);
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