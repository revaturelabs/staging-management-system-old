package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends SMSPage {

	@FindBy(tagName="title")
	public WebElement title;
	
	@FindBy(css="[class=\"ng-binding flex\"]")
	public WebElement header;
	
	//@FindBy(css="[md-svg-icon=\"img/icons/ic_menu_white_24px.svg\"]")
	//public WebElement menuIcon;
	
	//@FindBy(css="[md-svg-icon=\"img/icons/ic_exit_to_app_white_24px.svg\"]")
	@FindBy(css="[ng-click=\"tempCtrl.logout()\"]")
	public WebElement logoutIcon;
	
	@FindBy(css="[ng-model=\"searchBox\"]")
	public WebElement searchBox;
	
	@FindBy(css="[ng-click=\"manAttCtrl.goBackOneWeek()\"]")
	public WebElement prevWeek;
	
	@FindBy(css="[ng-click=\"manAttCtrl.goForwardOneWeek()\"]")
	public WebElement nextWeek;
	

	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/div/ui-view/md-table-container/table[2]")
	public WebElement attendanceTable;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/div/ui-view/md-table-container/table[2]/tbody/tr/td[1]")
	public WebElement searchResult;
	
	
	
	public AdminPage(WebDriver driver) {
		super(driver);
	}
	
	//Someone should add a method to find web elements in the attendance table 
}
