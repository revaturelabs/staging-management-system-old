package com.revature.sms;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

//This test class tests features that are particular to an Admin's home page.
public class AdminT extends AbstractT {

	//Tests that when an Admin or a Super Admin logs in and out, they are navigated to the appropriate pages.
	@Test
	public void testAdminLogin() {
		String pageType = hp.getClass().getName();
		String expectedValue;
		if (pageType.contains(".Admin")) {
			expectedValue = expected.getProperty("adminPg");
		} else {
			expectedValue = expected.getProperty("superAdminPg");
		}
		loginHeaderLogoutTemplate(un, pw, expectedValue);
	}

	@Test
	public void testCancelButtons() {
		lp.login(un, pw);
		cancelCommonButtons();
	}

	// This test is temporarily unusable and irrelevant while the search bar is
	// being fixed
	// Test to enter username in search box and verify correct associate name is
	// returned
	/*
	 * @Test 
	 * public void testAdminSearchBar() {
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

	
	//This test clicks all of the buttons on the Admin attendance table and makes sure that they work.
	@Test
	public void testAdminAttendanceButtons() {
		lp.login(un, pw);
		
		//Navigation buttons
		adp.prevWeekTop.click();
		adp.nextWeekTop.click();
		adp.prevWeekBottom.click();
		adp.nextWeekBottom.click();
		
		List<WebElement> allCells = adp.attendanceTable.findElements(By.tagName("td"));
		int count = allCells.size();
		
		
		int i=0;
		//Iterates through every cell in the attendance table.
		while (i<count) {
			try {
				WebElement cell = allCells.get(i);
				String textBefore = cell.getText();
				cell.click();
				if (cell.getText().contains("\n")) {  //When an associate name is clicked
					adp.closeIcon.click();
				} else {  //When an attendance icon is clicked
					String textAfter = cell.getText();
					System.out.println(textBefore);
					System.out.println(textAfter);
					System.out.println();
					Assert.assertNotEquals(textBefore, textAfter);  //Asserts that the icon changed after being clicked.
				}
				
			} catch (StaleElementReferenceException e) {  //If the table changed on the web page, it must be reloaded.
				allCells = adp.attendanceTable.findElements(By.tagName("td"));
			}
			i++;
		}
		
	}

}