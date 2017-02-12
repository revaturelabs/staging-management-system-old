package com.revature.sms;

import java.time.MonthDay;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.revature.sms.util.Utils;

//This class tests features of the website that are not associated with a specific type of home page.
public class OtherT extends AbstractT {

	//This test changes the password of a user, logs out, logs in with the new password, and changes the password
	//back to the original password. Throughout this proccess, the test also asserts that all of the expected pages 
	//and toast notifications are loaded appropriately.
	@Test
	public void testPasswordChange() {
		String pw2 = inputs.getProperty("PW2");
		
		lp.login(un, pw);
		hp.settings.click();
		Assert.assertTrue(sw.verify());
		sw.oldPass.sendKeys(pw);
		sw.newPass.sendKeys(pw2);
		sw.confirmPass.sendKeys(pw2);
		sw.submit.click();
		//Utils.attemptWait(500);
		Assert.assertEquals(expected.getProperty("successfulPasswordSubmission"), hp.getToastMessage());
		hp.logout.click();

		lp.login(un, pw2);
		hp.settings.click();
		Assert.assertTrue(sw.verify());
		sw.oldPass.sendKeys(pw2);
		sw.newPass.sendKeys(pw);
		sw.confirmPass.sendKeys(pw);
		sw.submit.click();
		//Utils.attemptWait(500);
		Assert.assertEquals(expected.getProperty("successfulPasswordSubmission"), hp.getToastMessage());
		hp.logout.click();
	}
	
	
	//Ensures that all of the web elements in the "Raise a Bug" frame can be interacted with as intended.
	@Test
	public void testBugReport() {
		lp.login(un, pw);
		hp.reportBug.click();
		driver.switchTo().frame("atlwdg-frame");  //Unlike other pop-ups on this website, this one actually opens a new frame.

		Assert.assertTrue(rbw.verify());
		rbw.messageBox.sendKeys(inputs.getProperty("bugReport"));
		rbw.enterName.sendKeys(inputs.getProperty("bugReportName"));
		rbw.enterEmail.sendKeys(inputs.getProperty("bugReportEmail"));
		rbw.webInfo.click();
		rbw.cancel.click();
		Assert.assertTrue(hp.verify());
	}
	
	//This test attempts to log in with different combinations of incorrect information in order to ensure that 
	//a user can't log in when they are not authorized, and that such a user is shown a toast notification that
	//properly identifies what they did wrong.
	@Test
	public void testLoginRejection() {
		WebElement submission = driver.findElement(By.xpath("/html/body/div[1]/div/ui-view[2]/div/md-card/form"));
		Assert.assertFalse(submission.getAttribute("class").contains("ng-submitted"));
		lp.submit.click();
		Assert.assertTrue(submission.getAttribute("class").contains("ng-submitted"));
		
		String fakeUN = inputs.getProperty("fakeUN");
		String fakePW = inputs.getProperty("fakePW");
		String usernameFail = expected.getProperty("usernameFail");
		String passwordFail = expected.getProperty("passwordFail");
		
		
		lp.login(fakeUN, fakePW);
		String message = lp.getToastMessage();
		Assert.assertEquals(usernameFail, message);
		lp.unField.clear();
		lp.pwField.clear();
		
		lp.login(fakeUN, pw);
		message = lp.getToastMessage();
		
		Assert.assertEquals(usernameFail, message);
		lp.unField.clear();
		lp.pwField.clear();
		
		lp.login(un, fakePW);
		message = lp.getToastMessage();
		Assert.assertEquals(passwordFail, message);
		lp.unField.clear();
		lp.pwField.clear();	
	}
	
	//Makes sure the current week is shown on the home page when you log in.
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

		
		ArrayList<MonthDay> actualMonthDays = hp.goThroughWeek();
		//...and compares them to the dates displayed on the website
		Assert.assertEquals(expectedMonthDays, actualMonthDays);
	}
	
}
