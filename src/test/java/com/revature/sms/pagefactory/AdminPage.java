package com.revature.sms.pagefactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class AdminPage extends SMSPage {

	@FindBy(tagName="title")
	WebElement title;
	
	@FindBy(css="[md-svg-icon=\"img/icons/ic_menu_white_24px.svg\"]")
	WebElement menuIcon;
	
	@FindBy(css="[md-svg-icon=\"img/icons/ic_exit_to_app_white_24px.svg\"]")
	WebElement logoutIcon;
	
	@FindBy(name="mainAdminView")
	WebElement attendanceTable;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/div/ui-view/section/button[1]/span/md-icon")
	WebElement prevWeek;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/div/ui-view/section/button[2]/span/md-icon")
	WebElement nextWeek;
	
	
	public AdminPage(EventFiringWebDriver driver) {
		super(driver);
	}

	public void logout() {
		logoutIcon.click();
	}
	
}
