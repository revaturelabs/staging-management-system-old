package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends SMSPage {

	@FindBy(tagName="title")
	WebElement title;
	
	@FindBy(css="[md-svg-icon=\"img/icons/ic_menu_white_24px.svg\"]")
	WebElement menuIcon;
	
	@FindBy(css="[md-svg-icon=\"img/icons/ic_exit_to_app_white_24px.svg\"]")
	WebElement logoutIcon;
	
	@FindBy(name="mainAdminView")
	WebElement attendanceTable;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/div/ui-view/md-table-container/table/thead/tr[1]/th[4]/span/md-icon")
	WebElement prevWeek;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/div/ui-view/md-table-container/table/thead/tr[1]/th[6]/span/md-icon")
	WebElement nextWeek;
	
	
	public AdminPage(WebDriver driver) {
		super(driver);
	}

	public void logout() {
		logoutIcon.click();
	}
	
}
