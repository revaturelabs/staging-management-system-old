package com.revature.sms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


//This class contains convenience methods for creating connections to properties files and web driver files.
public class TestSetup {
	
	//List of paths to browser drivers
	private final static String windowsChromeDriverPath = "src/test/resources/testBrowserDrivers/chromedriver.exe";
	private final static String ieDriverPath = "src/test/resources/testBrowserDrivers/IEDriverServer.exe";
	
	public static Properties getProperties(String pathname) {
		Properties prop = null;
		File file = null;
		FileInputStream fis = null;
		
		try {
			prop = new Properties();
			file = new File(pathname);
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
		System.setProperty("webdriver.chrome.driver", windowsChromeDriverPath);
		return new ChromeDriver();
		//Inconsequential change
	}
	
	public static WebDriver getIE() {
		System.setProperty("webdriver.ie.driver", ieDriverPath);
		return new InternetExplorerDriver();
	}
	
}