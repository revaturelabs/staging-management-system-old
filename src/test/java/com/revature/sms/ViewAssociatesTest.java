/*
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
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.User;
import com.revature.sms.domain.UserRole;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.testlibs.UserDataManager;
import com.revature.sms.util.ExcelHelper;
import com.revature.sms.util.InstanceTestClassListener;
import com.revature.sms.util.SpringInstanceTestClassRunner;
import com.revature.sms.util.EnterData;
import com.revature.sms.util.TestSetup;

@Service
@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest
public class ViewAssociatesTest implements InstanceTestClassListener{

	@Autowired
	private EnterData ed;
	
	static WebDriver driver;
	//static UserDataManager udm;
	
	
	@Override
	public void beforeClassSetup() {
		int userNumber = 2;
		driver = TestSetup.getChrome();
		ed.initializeUsers(userNumber);
		 
	}
	
	@Before
	public void before() {
		driver.get("localhost:81");
	}
	
	
	@Test
	public void viewAsAdmin() {
		System.out.println("Viewing as Admin");
	}

	
	@Override
	public void afterClassSetup() {
		//driver.close();
	}
	
	
	
	
}
*/