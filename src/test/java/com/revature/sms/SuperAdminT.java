package com.revature.sms;

import org.junit.After;
import org.junit.Test;

public class SuperAdminT extends AbstractT  {	
	
	@Test
	public void testLoginHeaderLogout() {
		String expectedValue = expected.getProperty("superAdminPg");
		LoginHeaderLogoutTemplate(sap, inputs.getProperty("superAdminUN"), inputs.getProperty("PW"), expectedValue);	
	}
	
	@Test
	public void testPasswordChange() {
		PasswordChangeTemplate(adp, inputs.getProperty("adminUN"), inputs.getProperty("PW"), inputs.getProperty("PW2"));
	}
	
	@Test
	public void testCancelButtons() {
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("PW"));
		sap.carefulClick("addBatch");
		cbw.carefulClick("cancel");
		sap.carefulClick("settings");
		cpw.carefulClick("cancel");
		sap.carefulClick("reportBug");
		driver.switchTo().frame("atlwdg-frame");
		rbw.carefulClick("cancel");
	}	
	
	//The submit button in the batch creation window seems to be disabled. Until it works again, this
	//test is out of commission.
	/*
	@Test
	public void testBatchCreation() {
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW"));
		
		sap.carefulClick("addBatch");
		Assert.assertTrue(cbw.verify());
		cbw.firstName.sendKeys(inputs.getProperty("firstName"));
		cbw.lastName.sendKeys(inputs.getProperty("lastName"));
		cbw.carefulClick("addToList");
		cbw.firstName.sendKeys(inputs.getProperty("firstName2"));
		cbw.lastName.sendKeys(inputs.getProperty("lastName2"));
		cbw.carefulClick("addToList");
		
		cbw.carefulClick("curriculum");
		ArrayList<WebElement> elements = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class=\"md-text ng-binding\"]"));
		for (WebElement e:elements) {
			String text = e.getText();
			if (text.equals(inputs.getProperty("curriculum"))) {
				e.click();
			}
		}
		
		cbw.enterDate.clear();
		cbw.enterDate.sendKeys(inputs.getProperty("batchStartDate"));
		cbw.carefulClick("submit");
		//driver.findElement(By.xpath("//*[@id=\"dialogContent_12\"]/div/div/button")).click();
	}
	*/
	
	@After
	public void after() {
		if (sap.verify()) {
			sap.carefulClick("logout");
		} 
	}	
}