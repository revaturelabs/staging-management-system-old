package com.revature.sms;

import org.junit.Test;
import com.revature.sms.util.Utils;


public class SuperAdminT extends AdminT  {	

	@Test
	public void testCancelButtons() {
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("PW"));
		sap.addBatch.click();
		cbw.cancel.click();
		
		//System.out.println("HERE: "+sap.getToastMessage());
		//String here2 = sap.getToastMessage();
		//System.out.println("HERE2: "+here2);
		//Assert.assertEquals(expected.getProperty("cancelBatchAddition"), sap.getToastMessage());
		
		sap.settings.click();
		sw.cancel.click();
		sap.reportBug.click();
		driver.switchTo().frame("atlwdg-frame");
		rbw.cancel.click();
		Utils.attemptWait(500);
	}	
	
	
	
	
	//The submit button in the batch creation window seems to be disabled. Until it works again, this
	//test is out of commission.
	/*
	//This test creates a new batch using the dashboard icon.
	@Test
	public void testBatchCreation() {
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW"));
		
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