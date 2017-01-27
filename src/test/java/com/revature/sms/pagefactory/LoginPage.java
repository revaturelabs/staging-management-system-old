package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LoginPage extends SMSPage {
	
	@FindBy(xpath="/html/head/title")
	public WebElement title;
	
	@FindBy(tagName="span")
	public WebElement header;
	
	@FindBy(xpath="//*[@ng-model=\"logCtrl.username\"]")
	public WebElement unField;
	
	@FindBy(xpath="//*[@ng-model=\"logCtrl.inputPass\"]")
	public WebElement pwField;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/div/md-card/form/div[2]/button/span")
	public WebElement submit;
	
	
	
	
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
