
package com.revature.sms;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.revature.sms.util.InstanceTestClassListener;
import com.revature.sms.util.SpringInstanceTestClassRunner;
import com.revature.sms.util.EnterData;
import com.revature.sms.util.TestSetup;

@Service
@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest
public class ViewAssociatesTest implements InstanceTestClassListener{

	private final String homepage = "localhost:81";
	
	@Autowired
	private EnterData ed;
	
	static WebDriver driver;
	
	
	@Override
	public void beforeClassSetup() {
		int columnNumber = 2;
		driver = TestSetup.getChrome();
		ed.initializeUsers(columnNumber);
		ed.initializeAttendance(columnNumber);
		 
	}
	
	@Before
	public void before() {
		driver.get(homepage);
	}
	
	
	@Test
	public void viewAsAdmin() {
		System.out.println("Viewing as Admin");
	}

	
	@Override
	public void afterClassSetup() {
		
	}
	
	
	
	
}
