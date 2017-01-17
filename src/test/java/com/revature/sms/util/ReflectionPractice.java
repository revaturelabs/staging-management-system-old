package com.revature.sms.util;

import org.openqa.selenium.WebDriver;
import com.revature.sms.pagefactory.LoginPage;

public class ReflectionPractice {

	public static void main(String[] args) {
		WebDriver driver = TestSetup.getChrome("src/test/resources/chromedriver.exe");
		driver.get("http://localhost:81/");
		LoginPage lp = new LoginPage(driver);
		boolean verified = lp.verify();
		System.out.println("The login page is verified: "+verified);
		
	}
	
}
