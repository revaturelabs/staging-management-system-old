
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
import com.revature.sms.testlibs.TestController;
import com.revature.sms.util.EventListener;

@Service
@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest
public class ViewAssociatesTest implements InstanceTestClassListener{
	
	private final String browser = "Chrome"; 
	private final String chromeDriverPath = "src/test/resources/chromedriver.exe";
	private final String ieDriverPath = "src/test/resources/IEDriverServer.exe";
	private final String inputsPath = "src/test/resources/inputs.properties";
	private final String locationsPath = "src/test/resources/locations.properties";
	
	@Autowired
	private TestController tc;
	
	static Properties inputs;
	static Properties locations;
	static WebDriver webDriver;
	static EventFiringWebDriver driver;
	static EventListener eventListener; 
	
	
	@Override
	public void beforeClassSetup() {
		if (browser.equals("Chrome")) {
			webDriver = TestSetup.getChrome(chromeDriverPath);
		}
		if (browser.equals("Internet Explorer")) {
			webDriver = TestSetup.getIE(ieDriverPath);
		}	
		driver = new EventFiringWebDriver(webDriver);
		eventListener = new EventListener();
		driver.register(eventListener);
		
		inputs = TestSetup.getProperties(inputsPath);
		locations = TestSetup.getProperties(locationsPath);
		
		int columnNumber = 2;
		tc.initializeUsers(columnNumber);
		tc.initializeAttendance(columnNumber);
		 
	}
	
	@Before
	public void before() {
		driver.get(inputs.getProperty("url"));
	}
	
	
	@Test
	public void viewAsAdmin() {
		LoginPage lp = new LoginPage(driver);
		//Assert.assertTrue(lp.verify());
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		AdminPage ap = new AdminPage(driver);
		//Assert.assertTrue(ap.verify());
		ap.logout();
		System.out.println("Logged Out");
		
	}

	
	@Override
	public void afterClassSetup() {
		System.out.println("HHHHHHHHEEEEEEERRRRRRRREEEEEEEEE!!!!!!!!");
		tc.clearData();
	}
	
	
	
	
}
