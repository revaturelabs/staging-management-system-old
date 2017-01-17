package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SuperAdminPage extends SMSPage {

	@FindBy(tagName="title")
	WebElement title;
	
	public SuperAdminPage(WebDriver driver) {
		super(driver);
	}


}
