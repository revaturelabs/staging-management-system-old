
package com.revature.sms;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.revature.sms.util.InstanceTestClassListener;
import com.revature.sms.util.SpringInstanceTestClassRunner;
import com.revature.sms.util.TestSetup;
import com.revature.sms.pagefactory.AdminPage;
import com.revature.sms.pagefactory.LoginPage;
import com.revature.sms.pagefactory.SuperAdminPage;
import com.revature.sms.testlibs.DBInitializationController;
import com.revature.sms.util.EventListener;

@Service
@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest
public class ViewAssociatesTest implements InstanceTestClassListener{
	
	 
	private final String browser = "Chrome"; 
	//List of paths to browser drivers
	private final String chromeDriverPath = "src/test/resources/chromedriver.exe";
	private final String ieDriverPath = "src/test/resources/IEDriverServer.exe";
	private final String inputsPath = "src/test/resources/inputs.properties";
	private final String locationsPath = "src/test/resources/locations.properties";
	
	//Basically anything that interacts with a data transfer objects must be autowired, which alerts 
	//Spring of it's existence
	@Autowired
	private DBInitializationController dbic;
	
	//Allow properties files, webdrivers, and page objects to be used in the tests
	static Properties inputs;
	static Properties locations;
	static WebDriver webDriver;
	static EventFiringWebDriver driver;
	static EventListener eventListener; 
	private LoginPage lp;
	private AdminPage ap;
	private SuperAdminPage sap;
	
	
	@Override
	public void beforeClassSetup() {
		if (browser.equals("Chrome")) {
			webDriver = TestSetup.getChrome(chromeDriverPath);
		}
		if (browser.equals("Internet Explorer")) {
			webDriver = TestSetup.getIE(ieDriverPath);
		}	
		
		//Allows the driver to take advantage of an event listener
		driver = new EventFiringWebDriver(webDriver);
		eventListener = new EventListener();
		driver.register(eventListener);
		
		//Initialize properties files
		inputs = TestSetup.getProperties(inputsPath);
		locations = TestSetup.getProperties(locationsPath);
		
		//The columnNumber variable should match the number of users that are being added to the database
		int columnNumber = 2;
		dbic.initializeUsers(columnNumber);
		dbic.initializeAttendance(columnNumber);
	}
	
	//More browser preparation
	@Before
	public void before() {
		driver.get(inputs.getProperty("url"));
		lp = new LoginPage(driver);
		ap = new AdminPage(driver);
		
	}
	
	//Login and logout as admin
	@Test
	public void viewAsAdmin() {
		
		System.out.println("Viewing as admin");
		Assert.assertTrue(lp.verify());
		Assert.assertEquals(locations.getProperty("loginPg"), driver.getTitle());
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		
		Assert.assertTrue(ap.verify());
		ap.logout();
		//Assert.assertTrue(lp.verify());
		
	}
	
	//Login and logout as superadmin
	@Test
	public void viewAsSuperAdmin() {
		
		System.out.println("Viewing as superadmin");
		Assert.assertTrue(lp.verify());
		Assert.assertEquals(locations.getProperty("loginPg"), driver.getTitle());
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW"));
		//Assert.assertTrue(sap.verify());
	}

	//Clear database
	@Override
	public void afterClassSetup() {
		//dbic.clearData();
		//driver.close();
	}
	
	
	
	
}
