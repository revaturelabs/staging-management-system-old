package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AssociatePage extends SMSPage {

	@FindBy(tagName="title")
	public WebElement title;
	
	@FindBy(css="[class=\"ng-binding flex\"]")
	public WebElement header;
	
	@FindBy(css="[md-svg-icon=\"img/icons/ic_menu_white_24px.svg\"]")
	public WebElement menuIcon;
	
	@FindBy(css="[md-svg-icon=\"img/icons/ic_exit_to_app_white_24px.svg\"]")
	public WebElement logoutIcon;
	
	@FindBy(tagName="tbody")
	public WebElement weekDisplay;
	
	@FindBy(css="[ng-click=\"assWeekAttCtrl.getPreviousWeek()\"]")
	public WebElement prevWeek;
	
	@FindBy(css="[ng-click=\"assWeekAttCtrl.getNextWeek()\"]")
	public WebElement nextWeek;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/div/ui-view/div[3]/div[2]/p")
	public WebElement weekOf;
	

	public AssociatePage(WebDriver driver) {
		super(driver);
	}
	
	

}
