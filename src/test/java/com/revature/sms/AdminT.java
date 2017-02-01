package com.revature.sms;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AdminT extends AbstractT {

	// Tests that when different types of users login and logout, they are
	// navigated to the correct pages
	@Ignore
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

	@Ignore
	@Test
	public void testCancelButtons() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("PW"));
		adp.carefulClick("settings");
		cpw.carefulClick("cancel");
		adp.carefulClick("reportBug");
		driver.switchTo().frame("atlwdg-frame");
		rbw.carefulClick("cancel");
	}

	// This test is temporarily unusable and irrelevant while the search bar is
	// being fixed
	// Test to enter username in search box and verify correct associate name is
	// returned
	/*
	 * @Test public void testSearchBar() {
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
	 * adp.carefulClick("logout"); Assert.assertTrue(lp.verify()); }
	 */

	@Test
	public void testAdminAttendenceView() {
		lp.login(un, pw);
		int count = 0;
		int cellCount = 0;
		while (count < 18) {
			List<WebElement> allRows = adp.attendanceTable.findElements(By.tagName("tr"));
			row: for (WebElement row : allRows) {
				List<WebElement> cells = row.findElements(By.tagName("td"));
				for (WebElement cell : cells) {
					if (count == 0) {
						System.out.println("In count == 0");
						cell.click();
						Assert.assertTrue(adp.verifyAssoc.isDisplayed());
						adp.carefulClick("closeIcon");

					} else if (count == 1) {
						System.out.println("In count == 1");
						cell.click();
					} else {
						List<WebElement> allRows1 = adp.attendanceTable.findElements(By.tagName("tr"));
						cell: for (WebElement row1 : allRows1) {
							List<WebElement> cells1 = row1.findElements(By.tagName("td"));
							for (WebElement cell1 : cells1) {
								if (count == cellCount) {
									System.out.println("In cellCount == count");
									if (cellCount % 6 == 0) {
										System.out.println("In cellCount % 6 == 0");
										cell1.click();
										Assert.assertTrue(adp.verifyAssoc.isDisplayed());
										adp.carefulClick("closeIcon");
									} else {
										System.out.println("In else");
										cell1.click();
									}
									cellCount = 0;
									break cell;
								}
								cellCount++;
							}
						}
					}
					count++;
					if(count % 6 == 0){
						break row;
					}
				}
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