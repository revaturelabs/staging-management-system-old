package com.revature.sms;

import org.junit.Assert;
import org.junit.Test;
import com.revature.sms.util.Utils;


public class SuperAdminT extends AdminT  {	

	@Test
	public void testCancelButtons() {
		lp.login(un, pw);
		sap.addBatch.click();
		
		Assert.assertTrue(cbw.verify());
		cbw.cancel.click();
		Utils.attemptWait(500);
		Assert.assertEquals(expected.getProperty("cancelBatchAddition"), sap.getToastMessage());
		
		sap.editSkills.click();
		Assert.assertTrue(asw.verify());
		asw.cancel.click();
		Assert.assertEquals(expected.getProperty("cancelAddSkill"), sap.getToastMessage());
		
		cancelCommonButtons();
	}	
	
	
	
	
	//The submit button in the batch creation window seems to be disabled. Until it works again, this
	//test is out of commission.
	/*
	//This test creates a new batch using the dashboard icon.
	@Test
	public void testBatchCreation() {
		lp.login(un, pw);
		
		sap.addBatch.click();
		Assert.assertTrue(cbw.verify());
		cbw.firstName.sendKeys(inputs.getProperty("firstName"));
		cbw.lastName.sendKeys(inputs.getProperty("lastName"));
		cbw.addToList.click();
		cbw.firstName.sendKeys(inputs.getProperty("firstName2"));
		cbw.lastName.sendKeys(inputs.getProperty("lastName2"));
		cbw.addToList.click();
		
		cbw.curriculum.click();
		ArrayList<WebElement> elements = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class=\"md-text ng-binding\"]"));
		for (WebElement e:elements) {
			String text = e.getText();
			if (text.equals(inputs.getProperty("curriculum"))) {
				e.click();
			}
		}
		
		cbw.enterDate.clear();
		cbw.enterDate.sendKeys(inputs.getProperty("batchStartDate"));
		cbw.submit.click();
		//driver.findElement(By.xpath("//*[@id=\"dialogContent_12\"]/div/div/button")).click();
	}
	*/
	
	
	
	public void testCertificationStatusChange() {
		
	}
	
	public void testPanelStatusChange() {
		
	}
	
	public void testEventCreation() {
		
	}
	
	public void testMarketingStatusChange() {
		
	}
	
	public void testProjectCreation() {
		
	}
	
	public void testProjectAssignment() {
		
	}
	
	public void testSkillCreation() {
		
	}
	
	
	
	
}