package com.revature.sms.pagefactory;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;


public class LoginPage extends SMSPage {
	
	@FindBy(tagName="title")
	WebElement title;
	
	@FindBy(id="input_0")
	WebElement unField;
	
	@FindBy(id="input_1")
	WebElement pwField;
	
	@FindBy(css="[type=\"submit\"]")
	WebElement submit;
	
	
	
	public LoginPage(EventFiringWebDriver driver) {
		super(driver);
	}
	
	public void login(String username, String password) {
		unField.sendKeys(username);
		pwField.sendKeys(password);
		submit.click();
	}
	
	public String getTitle() {
		return title.getText();
	}
	
	/*
	public boolean verify() {
		Class thisClass = null;
		try {
			thisClass = (Class<String>) Class.forName("LoginPage");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Logger.getRootLogger().debug("You got a ClassNotFoundException", e);
		}
		Field[] fields = thisClass.getDeclaredFields();
		int i = 0;
		
		
		while (i < fields.length) {
			if (fields[i].getAnnotation(thisClass) != null) {
				try {
					 
				}
			}
		}
		return true;
	}
	*/
}
