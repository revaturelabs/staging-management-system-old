package com.revature.sms;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.revature.sms.util.Utils;

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
	
	
	@Test
	public void testWeekOf() {
		lp.login(un, pw);
		String monday = getThisMondayFromHeader();
		String weekTop = Utils.getDateFromText(adp.weekOfTop.getText());
		String weekBottom = Utils.getDateFromText(adp.weekOfBottom.getText());
		Assert.assertEquals(weekTop, weekBottom);
		Assert.assertEquals(monday, weekTop);
	}
	
	
	
	//Associate attendance icons do not always seem to change 100% of the time when they are clicked on many 
	//times during the following test, so I created this constant so anyone can choose how consistent they
	//want the test to be. Adding waits in critical spots can increase the success rate of the test
	final int tooManyMisses = 3;
	
	//This test clicks all of the buttons on the Admin attendance table and makes sure that they work.
	@Test
	public void testAdminAttendanceButtons() {
		lp.login(un, pw);
		
		//Test toast notification for attendance table cells
		adp.sampleAttendanceTableCell.click();
		Assert.assertEquals(expected.getProperty("attendanceUpdate"), adp.getToastMessage());
		adp.sampleAttendanceTableCell.click();
		
		//Navigation buttons
		while (!expected.getProperty("tooFarForward").equals(adp.getToastMessage())) {
			adp.nextWeekTop.click();
		}
		while (!expected.getProperty("tooFarBack").equals(adp.getToastMessage())) {
			adp.prevWeekBottom.click();
		}
		while (!expected.getProperty("tooFarForward").equals(adp.getToastMessage())) {
			adp.nextWeekBottom.click();
		}
		
		while (!expected.getProperty("tooFarBack").equals(adp.getToastMessage())) {
			List<WebElement> allCells = adp.attendanceBody.findElements(By.tagName("td"));
			WebElement cell;
			int count = allCells.size();
			int i=0;
			int misses=0;
			
			//Iterates through every cell in the attendance table.
			while (i<count) {
				cell = allCells.get(i);
				String textBefore = cell.getText();
				cell.click();
				Utils.attemptWait(600);  
				//The table on the web page is reloaded after every click on a cell, so the WebElements must
				//be reloaded too.
				allCells = adp.attendanceBody.findElements(By.tagName("td"));
				cell = allCells.get(i);
				if (cell.getText().contains("\n")) {  //When an associate name is clicked
					adp.closeIcon.click();
				} else {  //When an attendance icon is clicked
					String textAfter = cell.getText();
					if (textBefore.equals(textAfter)) {
						misses++;
					}
					Assert.assertTrue(misses < tooManyMisses);
				}
				i++;
			}
			Logger.getRootLogger().debug("Week: "+adp.weekOfTop.getText());
			Logger.getRootLogger().debug("Misses: "+misses);
			//System.out.println("Week: "+adp.weekOfTop.getText());
			//System.out.println("Misses: "+misses);
			Utils.attemptWait(1500);
			adp.prevWeekTop.click();
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
		
		
		
	}

}