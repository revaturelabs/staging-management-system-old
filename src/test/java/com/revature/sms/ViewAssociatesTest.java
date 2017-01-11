package com.revature.sms;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.User;
import com.revature.sms.domain.UserRole;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.testLibs.UserDataManager;
import com.revature.sms.util.Initializer;
import com.revature.sms.util.TestSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewAssociatesTest {

	private Properties input;
	private WebDriver driver;
	
	@Autowired
	UserRepo ur;
	
	@Ignore
	@BeforeClass
	public void beforeClass() {
		input = TestSetup.getProperties("input.properties");
		driver = TestSetup.getChrome();
		udm = Initializer.initializeUsers(input);
		for (User user:udm.createdUsers) {
			
			user.attendance()
		}
		
	}
	
	@Ignore
	@Before
	public void before() {
		driver.get("localhost:81");
	}
	
	
	@Ignore
	@Test
	public void viewAsAdmin() {
		fail("Not yet implemented");
	}

	
	
}
