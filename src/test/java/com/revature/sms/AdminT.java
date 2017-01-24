
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
import com.revature.sms.database.DBInitializationController;
import com.revature.sms.pagefactory.AdminPage;
import com.revature.sms.pagefactory.AssociatePage;
import com.revature.sms.pagefactory.LoginPage;
import com.revature.sms.pagefactory.SuperAdminPage;
import com.revature.sms.util.EventListener;
import com.revature.sms.util.ExcelHelper;

@Service
@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest
public class AdminT implements InstanceTestClassListener {
	private final String browser = "Chrome"; 
	private final String inputsPath = "src/test/resources/PropertiesFiles/inputs.properties";
	private final String expectedPath = "src/test/resources/PropertiesFiles/expected.properties";
	
	//Allow properties files, webdrivers, and page objects to be used in the tests
	static Properties inputs;
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
	    	if (browser.equals("Chrome")) {
	    		webDriver = TestSetup.getChrome();
	    	}
	    	if (browser.equals("Internet Explorer")) {
	    		webDriver = TestSetup.getIE();
	    	}
	    	
		//Allows the driver to take advantage of an event listener
		driver = new EventFiringWebDriver(webDriver);
		eventListener = new EventListener();
		driver.register(eventListener);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		//Initialize properties files
		inputs = TestSetup.getProperties(inputsPath);
		expected = TestSetup.getProperties(expectedPath);
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
		Assert.assertEquals(expected.getProperty("siteName"), driver.getTitle());
		Assert.assertTrue(lp.verify());
		Assert.assertEquals(expected.getProperty("loginPg"), lp.header.getText());
	}
	
	//Tests that when different types of users login and logout, they are navigated to the correct pages
	@Test
	public void testLoginHeaderLogout() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		Assert.assertTrue(adp.verify());
		Assert.assertEquals(expected.getProperty("adminPg"), adp.header.getText());
		adp.logout.click();
		Assert.assertTrue(lp.verify());
	}
	
	
	
	//Corey's Test ideas
	public void testSearchBar() {
		
	}
	
	public void testAdminAttendanceView() {
		
	}
	
	public void testAdminPageToastContainer() {
		
	}
	
	public void testAdminCalendarNavigation() {
		
	}

	@Override
	public void afterClassSetup() {
		driver.close();
	}
	
	
	
	
}
