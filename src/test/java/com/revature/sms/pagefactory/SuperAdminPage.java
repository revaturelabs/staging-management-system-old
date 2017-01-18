package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SuperAdminPage extends SMSPage {

	@FindBy(tagName="title")
	WebElement title;
	
	@FindBy(css="[md-svg-icon=\"img/icons/ic_menu_white_24px.svg\"]")
	WebElement menuIcon;
	
	@FindBy(css="[md-svg-icon=\"img/icons/ic_exit_to_app_white_24px.svg\"]")
	WebElement logoutIcon;
	
	
	public SuperAdminPage(WebDriver driver) {
		super(driver);
	}

	public void logout() {
		logoutIcon.click();
	}
	
}
