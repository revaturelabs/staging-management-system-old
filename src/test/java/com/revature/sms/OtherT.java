package com.revature.sms;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.revature.sms.util.Utils;

public class OtherT extends AbstractT {

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
		Utils.attemptWait(500);
		Assert.assertEquals(expected.getProperty("successfulPasswordSubmission"), hp.getToastMessage());
		hp.logout.click();

		lp.login(un, pw2);
		hp.settings.click();
		Assert.assertTrue(sw.verify());
		sw.oldPass.sendKeys(pw2);
		sw.newPass.sendKeys(pw);
		sw.confirmPass.sendKeys(pw);
		sw.submit.click();
		Utils.attemptWait(500);
		Assert.assertEquals(expected.getProperty("successfulPasswordSubmission"), hp.getToastMessage());
		hp.logout.click();
	}
	
	
	@Test
	public void testBugReport() {
		lp.login(un, pw);
		hp.reportBug.click();
		driver.switchTo().frame("atlwdg-frame");

		Assert.assertTrue(rbw.verify());
		rbw.messageBox.sendKeys(inputs.getProperty("bugReport"));
		rbw.enterName.sendKeys(inputs.getProperty("bugReportName"));
		rbw.enterEmail.sendKeys(inputs.getProperty("bugReportEmail"));
		rbw.webInfo.click();
		rbw.cancel.click();
		Assert.assertTrue(hp.verify());
	}
	
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
	
}
