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
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.UserRole;
import com.revature.sms.testlibs.UserDataManager;

public class TestSetup {
	
	public static Properties getProperties(String pathName) {
		Properties prop = null;
		File file = null;
		FileInputStream fis = null;
		
		try {
			prop = new Properties();
			file = new File(pathName);
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
	
	public static WebDriver getChrome(String pathname) {
		File file = new File(pathname);
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		return new ChromeDriver();
	}
	
	public static WebDriver getIE(String pathname) {
		File file = new File(pathname);
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		return new InternetExplorerDriver();
	}
	
	
	
	
}