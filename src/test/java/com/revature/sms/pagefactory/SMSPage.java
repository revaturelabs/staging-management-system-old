package com.revature.sms.pagefactory;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public abstract class SMSPage {

	protected EventFiringWebDriver driver;
	
	public SMSPage(EventFiringWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
}
