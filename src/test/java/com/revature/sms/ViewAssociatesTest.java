
package com.revature.sms;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.revature.sms.util.InstanceTestClassListener;
import com.revature.sms.util.SpringInstanceTestClassRunner;
import com.revature.sms.util.TestController;
import com.revature.sms.util.TestSetup;
import com.revature.sms.util.EventListener;

@Service
@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest
public class ViewAssociatesTest implements InstanceTestClassListener{

	private final String homepage = "localhost:81";
	//private final String homepage = "http://dev.revature.pro";
	
	@Autowired
	private TestController tc;
	
	static WebDriver webDriver;
	static EventFiringWebDriver driver;
	static EventListener eventListener; 
	
	
	@Override
	public void beforeClassSetup() {
		int columnNumber = 2;
		webDriver = TestSetup.getChrome();
		driver = new EventFiringWebDriver(webDriver);
		eventListener = new EventListener();
		driver.register(eventListener);
		
		tc.initializeUsers(columnNumber);
		tc.initializeAttendance(columnNumber);
		 
	}
	
	@Before
	public void before() {
		driver.get(homepage);
	}
	
	
	@Test
	public void viewAsAdmin() {
		//System.out.println("Page Title: "+driver.getTitle());
		driver.findElement(By.id("input_0")).sendKeys("admin");
		driver.findElement(By.id("input_1")).sendKeys("password");
		driver.findElement(By.cssSelector("[type=\"submit\"]")).click();
		//System.out.println("Viewing as Admin");
	}

	
	@Override
	public void afterClassSetup() {
		tc.clearData();
	}
	
	
	
	
}
