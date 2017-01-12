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
import com.revature.sms.util.ExcelHelper;
import com.revature.sms.util.Initializer;
import com.revature.sms.util.TestSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewAssociatesTest {

	static WebDriver driver;
	//static UserDataManager udm;
	
	
	@BeforeClass
	public static void beforeClass() {
		System.out.println("here");
		int userNumber = 2;
		driver = TestSetup.getChrome();
		Initializer.initializeUsers(userNumber);
		 
		/*
		String key = "checkedIn";
		int columns = 10;
		ArrayList<String> values = ExcelHelper.getValues(file, sheet, key, columns);
		for(String value:values) {
			System.out.println(value);
		}
		
		Initializer.initializeAttendance(input);
		for (User user:udm.createdUsers) {
		}
		*/
	}
	
	@Before
	public void before() {
		driver.get("localhost:81");
	}
	
	

	@Test
	public void viewAsAdmin() {
		System.out.println("here3");
	}

	
	
}
