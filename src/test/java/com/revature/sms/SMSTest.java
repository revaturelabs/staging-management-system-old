
package com.revature.sms;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.revature.sms.util.InstanceTestClassListener;
import com.revature.sms.util.SpringInstanceTestClassRunner;
import com.revature.sms.util.TestSetup;
import com.codoid.products.exception.FilloException;
import com.revature.sms.pagefactory.AdminPage;
import com.revature.sms.pagefactory.AssociatePage;
import com.revature.sms.pagefactory.LoginPage;
import com.revature.sms.pagefactory.SuperAdminPage;
import com.revature.sms.testlibs.DBInitializationController;
import com.revature.sms.util.EventListener;
import com.revature.sms.util.ExcelHelper;

@Service
@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest
public class SMSTest implements InstanceTestClassListener {
	private final String browser = "Chrome"; 
	private final String inputsPath = "src/test/resources/PropertiesFiles/inputs.properties";
	private final String locationsPath = "src/test/resources/PropertiesFiles/locations.properties";
	private final String expectedPath = "src/test/resources/PropertiesFiles/expected.properties";
	
	//Basically anything that interacts with a data transfer objects must be autowired, which alerts 
	//Spring of it's existence.
	@Autowired
	private DBInitializationController dbic;
	
	//Allow properties files, webdrivers, and page objects to be used in the tests
	static Properties inputs;
	static Properties locations;
	static Properties expected;
	static WebDriver webDriver;
	static EventFiringWebDriver driver;
	static EventListener eventListener; 
	private LoginPage lp;
	private AssociatePage asp;
	private AdminPage adp;
	private SuperAdminPage sap;
	
	
	@Override
	public void beforeClassSetup() {
	    if (System.getProperty("os.name").equalsIgnoreCase("Windows 10")) {  //If you are a tester with a different windows os, add it to this if statement.
	    	if (browser.equals("Chrome")) {
	    		webDriver = TestSetup.getChrome();
	    	}
	    	if (browser.equals("Internet Explorer")) {
	    		webDriver = TestSetup.getIE();
	    	}
	    } else {
	    	System.out.println("This line should be printed in Jenkins.");
	    	webDriver = new PhantomJSDriver();
	    }
	    	
	    	
		
		//Allows the driver to take advantage of an event listener
		driver = new EventFiringWebDriver(webDriver);
		eventListener = new EventListener();
		driver.register(eventListener);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		//Initialize properties files
		inputs = TestSetup.getProperties(inputsPath);
		locations = TestSetup.getProperties(locationsPath);
		expected = TestSetup.getProperties(expectedPath);
		
		dbic.initializeUsers();
		dbic.initializeUserObjects();
	}
	
	//More browser preparation
	@Before
	public void before() {
		driver.get(inputs.getProperty("url"));
		lp = new LoginPage(driver);
		asp = new AssociatePage(driver);
		adp = new AdminPage(driver);
		sap = new SuperAdminPage(driver);
		
		//Make sure the login page is loaded correctly 
		Assert.assertEquals(locations.getProperty("siteName"), driver.getTitle());
		Assert.assertTrue(lp.verify());
		Assert.assertEquals(locations.getProperty("loginPg"), lp.header.getText());
	}
	
	//Makes sure the current week is shown on the associate page when you log in.
	@Test
	public void testDefaultWeek() {
		lp.login(inputs.getProperty("javaUN"), inputs.getProperty("javaPW"));
		Assert.assertTrue(asp.verify());
		
		ArrayList<String> expectedMonthDays = new ArrayList<String>();
		expectedMonthDays.add(expected.getProperty("Mon"));
		expectedMonthDays.add(expected.getProperty("Tue"));
		expectedMonthDays.add(expected.getProperty("Wed"));
		expectedMonthDays.add(expected.getProperty("Thu"));
		expectedMonthDays.add(expected.getProperty("Fri"));
		
		ArrayList<String> actualMonthDays = asp.goThroughWeek();
		Assert.assertEquals(expectedMonthDays, actualMonthDays);
		asp.logoutIcon.click();
	}
	
	
	//ERROR??? When I run this test, and an associate is set as checked in and not verified for a 
	//certain date, the associate is shown as verified for that date on the website (there is a double
	//checkmark), even though the attendance is correctly identified as not verified in the database.
	//Also, an extra junk attendance associated with the user is added to the database, and is also 
	//incorrectly displayed on the website.
	@Ignore
	@Test
	public void testAssociateAttendanceView() {
		try {
			//Login as a test associate.
			ExcelHelper userGetter = new ExcelHelper("NewUsers");
			ArrayList<String> usernames = userGetter.getValues("username");
			ArrayList<String> passwords = userGetter.getValues("unhashedPassword");
			String ericUN = usernames.get(1);
			String ericPW = passwords.get(1);
			lp.login(ericUN, ericPW);
			Assert.assertTrue(asp.verify());
			
			//Determine what that associate's attendance is supposed to be using the Excel sheet
			//as a reference.
			ExcelHelper attendanceGetter = new ExcelHelper(ericUN);
			ArrayList<String> expectedDates = attendanceGetter.getValues("attendanceDate");
			ArrayList<String> checkIns = attendanceGetter.getValues("checkedIn");
			ArrayList<String> verifications = attendanceGetter.getValues("verified");
			//ArrayList<String> notes = attendanceGetter.getValues("attendanceNote");
			
			//Convert the associate's dates of attendance to the same format that is used
			//by the web application.
			ArrayList<String> expectedMonthDays = new ArrayList<String>();
			for (String s:expectedDates) {
				String monthDay;
				if (s.charAt(0) == '0') {
					monthDay = s.substring(1, 5);
				} else {
					monthDay = s.substring(0, 5);
				}
				expectedMonthDays.add(monthDay);
			}
			
			//ERROR??? The website says you can't go back more than 3 weeks after trying to go back more than
			//four weeks.
			String week;
			String weekBefore;
			//This do-while loop iterates through each available week on the associate page by 
			//clicking the arrow icons
			do {
				week = asp.weekOf.getText();
				ArrayList<String> actualMonthDays = asp.goThroughWeek();
				ArrayList<String> icons = asp.goThroughWeekIcons();
				int aCount = 0;  
				//The outer loop goes through each day of the week.
				for (String a:actualMonthDays) {
					int eCount = 0;
					//The inner loop compares the date on the website with the attendance date from the excel file.
					for (String e:expectedMonthDays) {
						//If the two dates are equal, then we check whether the website displays the attendance information correctly.
						if (a.equals(e)) {
							Boolean ci = Boolean.parseBoolean(checkIns.get(eCount));
							Boolean v = Boolean.parseBoolean(verifications.get(eCount));
							String icon = icons.get(aCount);
							if (ci && v) { //If the associate is checked in and verified, the double check icon should be displayed, which is represented by the string done_all in the html.
								Assert.assertEquals("done_all", icon);
							} else if (ci && !v) {
								Assert.assertEquals("done", icon);
							} else if (!ci && !v) {
								Assert.assertEquals("close", icon);
							} else {
								System.out.println("You did not fill out the associate attendance spreadsheet correctly.");
							}						
						} 
						eCount++;
					}
					aCount++;
					//These count variables ensure that this test compares the data from the excel sheet with the appropriate data from the web page.
				}
				asp.prevWeek.click();
				weekBefore = asp.weekOf.getText();
			} while (!week.equals(weekBefore));
		} catch (FilloException e) {}
		finally {
			asp.logoutIcon.click();
		}
	}
	
	//Tests that when different types of users login and logout, they are navigated to the correct pages
	@Ignore
	@Test
	public void testLoginHeaderLogout() {
		lp.login(inputs.getProperty("sdetUN"), inputs.getProperty("sdetPW"));
		Assert.assertTrue(asp.verify());
		Assert.assertEquals(locations.getProperty("associatePg"), asp.header.getText());  //Asserts that the title given in the blue bar towards the top of the page is the same as expected.
		asp.logoutIcon.click();
		Assert.assertTrue(lp.verify());
		
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		Assert.assertTrue(adp.verify());
		Assert.assertEquals(locations.getProperty("adminPg"), adp.header.getText());
		adp.logoutIcon.click();
		Assert.assertTrue(lp.verify());
		
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW"));
		Assert.assertTrue(sap.verify());
		Assert.assertEquals(locations.getProperty("superAdminPg"), sap.header.getText());
		sap.logoutIcon.click();
		Assert.assertTrue(lp.verify());
	}
	
	//Corey's Test ideas
	public void testCertificationScheduling() {
		
	}

	public void testBatchCreation() {
		
	}
	
	public void testAdminAttendanceView() {
		
	}
	
	public void testSearchBar() {
		
	}
	
	public void negativeTestAssociateAttendanceView() {
		//Make sure icons are not displayed under dates when there is no associated attendance record.
		//Maybe this can be integrated into testAssociateAttendanceView.
	}
	
	public void testLoginPageToastContainer() {
		
	}
	
	public void testAssociatePageToastContainer() {
		
	}
	
	public void testAdminPageToastContainer() {
		
	}
	
	public void testSuperAdminPageToastContainer() {
	
	}
	
	public void testAssociateCalendarNavigation() {
		//This is already done indirectly in testAssociateAttendanceView but maybe the navigation 
		//buttons should be directly tested too.
	}
	
	public void testAdminCalendarNavigation() {
		
	}
	//If you are having trouble coming up with a test to make, try translating one of the user stories
	//in Jira into a test case.
	
	//It could be a good idea to separate some of these test cases into different suites as well.
	
	//Close webdriver and clear database
	@Override
	public void afterClassSetup() {
		driver.close();
		dbic.clearData();
	}
	
	
	
	
}
