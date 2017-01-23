
package com.revature.sms;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	//Spring of it's existence
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
		
		//The columnNumber variable should match the number of users that are being added to the database
		dbic.initializeUsers();
		dbic.initializeAttendance();
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
		Assert.assertTrue(lp.verify());
		Assert.assertEquals(locations.getProperty("siteName"), driver.getTitle());
	}
	
	@Ignore
	@Test
	public void verifyDefaultWeek() {
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
	}
	
	
	//ERROR??? When I run this test, and an associate is set as checked in and not verified for a 
	//certain date, the associate is shown as verified for that date on the website (there is a double
	//checkmark), even though the attendance is correctly identified as not verified in the database.
	//Also, an extra junk attendance associated with the user is added to the database, and is also 
	//incorrectly displayed on the website.
	@Test
	public void verifyAssociateAttendanceView() {
		try {
			ExcelHelper userGetter = new ExcelHelper("NewUsers");
			ArrayList<String> usernames = userGetter.getValues("username");
			ArrayList<String> passwords = userGetter.getValues("unhashedPassword");
			String ericUN = usernames.get(1);
			String ericPW = passwords.get(1);
			lp.login(ericUN, ericPW);
			Assert.assertTrue(asp.verify());
			
			ExcelHelper attendanceGetter = new ExcelHelper(ericUN);
			ArrayList<String> expectedDates = attendanceGetter.getValues("attendanceDate");
			ArrayList<String> checkIns = attendanceGetter.getValues("checkedIn");
			ArrayList<String> verifications = attendanceGetter.getValues("verified");
			//ArrayList<String> notes = attendanceGetter.getValues("attendanceNote");
			
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
			//four weeks
			String week;
			String weekBefore;
			do {
				week = asp.weekOf.getText();
				ArrayList<String> actualMonthDays = asp.goThroughWeek();
				ArrayList<String> icons = asp.goThroughWeekIcons();
				
				int aCount = 0;
				for (String a:actualMonthDays) {
					int eCount = 0;
					for (String e:expectedMonthDays) {
						if (a.equals(e)) {
							Boolean ci = Boolean.parseBoolean(checkIns.get(eCount));
							Boolean v = Boolean.parseBoolean(verifications.get(eCount));
							String icon = icons.get(aCount);
							if (ci && v) {
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
				}
				
				asp.prevWeek.click();
				weekBefore = asp.weekOf.getText();
			} while (!week.equals(weekBefore));
		} catch (FilloException e) {}
	}
	
	@Ignore
	@Test
	public void testAdminPage() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		Assert.assertTrue(adp.verify());
		adp.logoutIcon.click();
	}
	
	@Ignore
	@Test
	public void testSuperAdminPage() {
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW"));
		Assert.assertTrue(sap.verify());
		sap.logoutIcon.click();
	}

	
	//Clear database
	@Override
	public void afterClassSetup() {
		//driver.close();
		//dbic.clearData();
	}
	
	
	
	
}
