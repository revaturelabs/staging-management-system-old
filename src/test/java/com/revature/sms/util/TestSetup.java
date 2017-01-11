package com.revature.sms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.UserRole;
import com.revature.sms.testLibs.UserDataManager;

public class TestSetup {
	
	public static Properties getProperties(String fileName) {
		Properties prop = null;
		File file = null;
		FileInputStream fis = null;
		
		try {
			prop = new Properties();
			file = new File(fileName);
			fis = new FileInputStream(file);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		return prop;
	}
	
	public static WebDriver getChrome() {
		File file = new File("C:/selenium-2.47.1/chromedriver.exe");
		//File file = new File("C://seleniumDrivers/chromedriver_win32/chromedriver.exe/");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		return new ChromeDriver();
	}
	
	public static WebDriver getFirefox() {
		File file = new File("C://seleniumDrivers/geckodriver-v0.11.1-win32/geckodriver.exe/");
		System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
		return new FirefoxDriver();
	}
	
	
	public static void initializeDatabase(Properties input, UserDataManager udm) {
		udm = new UserDataManager();
		String username = input.getProperty("username");
		String firstName = input.getProperty("firstName");
		String lastName = input.getProperty("lastName");
		String unhashedPassword = input.getProperty("unhashedPassword");
		String batchTypeString = input.getProperty("batchType");
		BatchType batchType = new BatchType(batchTypeString);
		List<AssociateAttendance> attendance = new ArrayList<AssociateAttendance>();
		List<AssociateTask> tasks = new ArrayList<AssociateTask>();
		String userRoleString = input.getProperty("userRole");
		UserRole userRole = new UserRole(userRoleString);
		udm.createTestAssociate(username, firstName, lastName, unhashedPassword, batchType, attendance, tasks, userRole);
		
		String username1 = input.getProperty("username1");
		String firstName1 = input.getProperty("firstName1");
		String lastName1 = input.getProperty("lastName1");
		String unhashedPassword1 = input.getProperty("unhashedPassword1");
		
		String batchTypeString1 = input.getProperty("batchType1");
		BatchType batchType1 = new BatchType(batchTypeString1);
		List<AssociateAttendance> attendance1 = new ArrayList<AssociateAttendance>();
		List<AssociateTask> tasks1 = new ArrayList<AssociateTask>();
		String userRoleString1 = input.getProperty("userRole1");
		UserRole userRole1 = new UserRole(userRoleString1);
		udm.createTestAssociate(username1, firstName1, lastName1, unhashedPassword1, batchType1, attendance1, tasks1, userRole1);
		
		String username2 = input.getProperty("username2");
		String firstName2 = input.getProperty("firstName2");
		String lastName2 = input.getProperty("lastName2");
		String unhashedPassword2 = input.getProperty("unhashedPassword2");
		String batchTypeString2 = input.getProperty("batchType2");
		BatchType batchType2 = new BatchType(batchTypeString2);
		List<AssociateAttendance> attendance2 = new ArrayList<AssociateAttendance>();
		List<AssociateTask> tasks2 = new ArrayList<AssociateTask>();
		String userRoleString2 = input.getProperty("userRole2");
		UserRole userRole2 = new UserRole(userRoleString2);
		udm.createTestAssociate(username2, firstName2, lastName2, unhashedPassword2, batchType2, attendance2, tasks2, userRole2);
		
		String username3 = input.getProperty("username3");
		String firstName3 = input.getProperty("firstName3");
		String lastName3 = input.getProperty("lastName3");
		String unhashedPassword3 = input.getProperty("unhashedPassword3");
		String batchTypeString3 = input.getProperty("batchType3");
		BatchType batchType3 = new BatchType(batchTypeString3);
		List<AssociateAttendance> attendance3 = new ArrayList<AssociateAttendance>();
		List<AssociateTask> tasks3 = new ArrayList<AssociateTask>();
		String userRoleString3 = input.getProperty("userRole3");
		UserRole userRole3 = new UserRole(userRoleString3);
		udm.createTestAssociate(username3, firstName3, lastName3, unhashedPassword3, batchType3, attendance3, tasks3, userRole3);
		
		String username4 = input.getProperty("username4");
		String firstName4 = input.getProperty("firstName4");
		String lastName4 = input.getProperty("lastName4");
		String unhashedPassword4 = input.getProperty("unhashedPassword4");
		String batchTypeString4 = input.getProperty("batchType4");
		BatchType batchType4 = new BatchType(batchTypeString4);
		List<AssociateAttendance> attendance4 = new ArrayList<AssociateAttendance>();
		List<AssociateTask> tasks4 = new ArrayList<AssociateTask>();
		String userRoleString4 = input.getProperty("userRole4");
		UserRole userRole4 = new UserRole(userRoleString4);
		udm.createTestAssociate(username4, firstName4, lastName4, unhashedPassword4, batchType4, attendance4, tasks4, userRole4);
		
		
	}
	
	
	
}