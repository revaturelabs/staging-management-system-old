package com.revature.sms;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class AdminT extends AbstractT {

	// Tests that when different types of users login and logout, they are
	// navigated to the correct pages
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
		adp.settings.click();
		sw.cancel.click();
		adp.reportBug.click();
		driver.switchTo().frame("atlwdg-frame");
		rbw.cancel.click();
	}

	// This test is temporarily unusable and irrelevant while the search bar is
	// being fixed
	// Test to enter username in search box and verify correct associate name is
	// returned
	/*
	 * @Test 
	 * public void testSearchBar() {
	 * lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
	 * Assert.assertTrue(adp.verify());
	 * Assert.assertEquals(expected.getProperty("adminPg"),
	 * adp.header.getText());
	 * 
	 * adp.searchBox.sendKeys("Java");
	 * Assert.assertEquals(expected.getProperty("java"),
	 * adp.searchResult.getText()); adp.searchBox.clear();
	 * 
	 * adp.searchBox.sendKeys("DotNet");
	 * Assert.assertEquals(expected.getProperty("dotnet"),
	 * adp.searchResult.getText()); adp.searchBox.clear();
	 * 
	 * adp.searchBox.sendKeys("SDET");
	 * Assert.assertEquals(expected.getProperty("sdet"),
	 * adp.searchResult.getText()); adp.searchBox.clear();
	 * 
	 * adp.logout.click(); 
	 * Assert.assertTrue(lp.verify()); 
	 * }
	 */

	
	@Test
	public void testAdminAttendanceButtons() {
		lp.login(un, pw);
		adp.prevWeekTop.click();
		adp.nextWeekTop.click();
		adp.prevWeekBottom.click();
		adp.nextWeekBottom.click();
		
		List<WebElement> allCells = adp.attendanceTable.findElements(By.tagName("td"));
		int count = allCells.size();
		
		int i=0;
		while (i<count) {
			try {
				WebElement cell = allCells.get(i);
				String textBefore = cell.getText();
				cell.click();
				if (cell.getText().contains("\n")) {
					adp.closeIcon.click();
				} else {
					String textAfter = cell.getText();
					System.out.println(textBefore);
					System.out.println(textAfter);
					System.out.println();
					Assert.assertNotEquals(textBefore, textAfter);
				}
				
			} catch (StaleElementReferenceException e) {
				allCells = adp.attendanceTable.findElements(By.tagName("td"));
			}
			i++;
		}
		
	}

}