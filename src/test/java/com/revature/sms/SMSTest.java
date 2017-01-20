
package com.revature.sms;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
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
import com.revature.sms.pagefactory.AdminPage;
import com.revature.sms.pagefactory.AssociatePage;
import com.revature.sms.pagefactory.LoginPage;
import com.revature.sms.pagefactory.SuperAdminPage;
import com.revature.sms.testlibs.DBInitializationController;
import com.revature.sms.util.EventListener;

@Service
@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest
public class SMSTest implements InstanceTestClassListener {
	private final String browser = "Chrome"; 
	private final String inputsPath = "src/test/resources/PropertiesFiles/inputs.properties";
	private final String locationsPath = "src/test/resources/PropertiesFiles/locations.properties";
	
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
		Assert.assertTrue(lp.verify());
		Assert.assertEquals(locations.getProperty("siteName"), driver.getTitle());
	}
	
	@Test
	public void testAssociatePage() {
		lp.login(inputs.getProperty("javaUN"), inputs.getProperty("javaPW"));
		Assert.assertTrue(asp.verify());
		asp.logoutIcon.click();
	}
	
	@Test
	public void testAdminPage() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		Assert.assertTrue(adp.verify());
		adp.logoutIcon.click();
	}
	
	@Test
	public void testSuperAdminPage() {
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW"));
		Assert.assertTrue(sap.verify());
		sap.logoutIcon.click();
	}

	
	//Clear database
	@Override
	public void afterClassSetup() {
		dbic.clearData();
		driver.close();
	}
	
	
	
	
}
