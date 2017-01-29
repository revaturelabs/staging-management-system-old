package com.revature.sms;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.revature.sms.pagefactory.AdminPage;
import com.revature.sms.pagefactory.SuperAdminPage;

public class SuperAdminT2 extends AdminT2  {
	SuperAdminPage mp;
	
	@Before
	public void setPageType() {
		mp = new SuperAdminPage(driver);
	}
	
	
	@Test
	public void testBatchCreation() {
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW"));
		
		mp.carefulClick("addBatch");
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
		driver.findElement(By.xpath("//*[@id=\"dialogContent_12\"]/div/div/button")).click();
		mp.carefulClick("logout");
	}
	
}
