package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

//This class contains WebElements that are shared by the Associate, Admin, and Super Admin home pages.
public class HomePage extends SMSPage {

	@FindBy(css="[class=\"ng-binding flex\"]")
	public WebElement header;
	
	@FindBy(xpath="//*[@aria-label=\"Account settings\"]")
	public WebElement settings;
	
	@FindBy(xpath="//*[@aria-label=\"Logout\"]")
	public WebElement logout;
	
	@FindBy(id="atlwdg-trigger")
	public WebElement reportBug;
	
	
	public HomePage(WebDriver driver) {
		super(driver);
	}

	
}
