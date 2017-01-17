package com.revature.sms.pagefactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class SuperAdminPage extends SMSPage {

	@FindBy(tagName="title")
	WebElement title;
	
	public SuperAdminPage(EventFiringWebDriver driver) {
		super(driver);
	}


}
