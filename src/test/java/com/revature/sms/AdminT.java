package com.revature.sms;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AdminT extends AbstractT {

	//Tests that when different types of users login and logout, they are
	//navigated to the correct pages
	@Test
	public void testLoginHeaderLogout() {
		String pageType = hp.getClass().getName();
		String expectedValue;
		if (pageType.contains(".Admin")) {
			expectedValue = expected.getProperty("adminPg");
		} else {
			expectedValue = expected.getProperty("superAdminPg");
		}
		LoginHeaderLogoutTemplate(un, pw, expectedValue);
	}
	

	@Test
	public void testCancelButtons() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("PW"));
		adp.carefulClick("settings");
		cpw.carefulClick("cancel");
		adp.carefulClick("reportBug");
		driver.switchTo().frame("atlwdg-frame");
		rbw.carefulClick("cancel");
	}
	
	
	// This test is temporarily unusable and irrelevant while the search bar is being fixed
	// Test to enter username in search box and verify correct associate name is returned
	/*
	@Test
	public void testSearchBar() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		Assert.assertTrue(adp.verify());
		Assert.assertEquals(expected.getProperty("adminPg"), adp.header.getText());
		
		adp.searchBox.sendKeys("Java");
		Assert.assertEquals(expected.getProperty("java"), adp.searchResult.getText());
		adp.searchBox.clear();
		
		adp.searchBox.sendKeys("DotNet");
		Assert.assertEquals(expected.getProperty("dotnet"), adp.searchResult.getText());
		adp.searchBox.clear();
		
		adp.searchBox.sendKeys("SDET");
		Assert.assertEquals(expected.getProperty("sdet"), adp.searchResult.getText());
		adp.searchBox.clear();
		
		adp.carefulClick("logout");
		Assert.assertTrue(lp.verify());
	}
	*/

	@Ignore
	@Test
	public void testAdminAttendenceView() {
		lp.login(un, pw);
		List<WebElement> allRows = adp.attendanceTable.findElements(By.tagName("tr"));
		int count = 0;
		for (WebElement row : allRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for (WebElement cell : cells) {
				if (count % 6 == 0) {
					cell.click();
					Assert.assertTrue(adp.verifyAssoc.isDisplayed());
					adp.carefulClick("closeIcon");
					
				} else {
					cell.click();
					cell.click();
				}
				count++;
			}
		}
	}
	
	@Ignore
	@Test
	public void testAdminCalendarNavigation() {
		lp.login(un, pw);
		adp.carefulClick("prevWeekTop");
		adp.carefulClick("nextWeekTop");
		adp.carefulClick("prevWeekBottom");
		adp.carefulClick("nextWeekBottom");
	}

}