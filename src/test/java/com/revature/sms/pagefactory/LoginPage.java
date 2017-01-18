package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LoginPage extends SMSPage {
	
	@FindBy(xpath="/html/head/title")
	WebElement title;
	
	@FindBy(css="[type=\"text\"]")
	WebElement unField;
	
	@FindBy(css="[type=\"password\"]")
	WebElement pwField;
	
	@FindBy(css="[type=\"submit\"]")
	WebElement submit;
	
	
	
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public void login(String username, String password) {
		unField.sendKeys(username);
		pwField.sendKeys(password);
		submit.click();
	}
	
	/*
	public String getTitle() {
		System.out.println("In getTitle");
		System.out.println("Tag name: "+title.getTagName());
		System.out.println("Text: "+title.getText());
		return title.getText();
	}
	*/
}
