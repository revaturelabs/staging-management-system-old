package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LoginPage extends SMSPage {
	
	@FindBy(xpath="/html/head/title")
	public WebElement title;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/div/md-card/md-toolbar/div/span")
	public WebElement header;
	
	@FindBy(css="[type=\"text\"]")
	public WebElement unField;
	
	@FindBy(css="[type=\"password\"]")
	public WebElement pwField;
	
	@FindBy(css="[type=\"submit\"]")
	public WebElement submit;
	
	
	
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public void login(String username, String password) {
		System.out.println(driver.getTitle());
		unField.sendKeys(username);
		System.out.println("Username: "+username);
		pwField.sendKeys(password);
		System.out.println("Password: "+password);
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
