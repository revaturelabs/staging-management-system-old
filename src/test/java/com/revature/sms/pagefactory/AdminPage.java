package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends SMSPage {

	@FindBy(tagName="title")
	public WebElement title;
	
	@FindBy(css="[class=\"ng-binding flex\"]")
	public WebElement header;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/md-toolbar/div/button[1]/md-icon")
	public WebElement settings;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/md-toolbar/div/button[2]/md-icon")
	public WebElement logout;
	
	@FindBy(css="[ng-model=\"searchBox\"]")
	public WebElement searchBox;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/ui-view/section/button[1]/span/md-icon")
	public WebElement prevWeek;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/ui-view/section/button[2]/span/md-icon")
	public WebElement nextWeek;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/ui-view/md-table-container/table")
	public WebElement attendanceTable;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/div/ui-view/md-table-container/table[2]/tbody/tr/td[1]")
	public WebElement searchResult;
	
	
	public AdminPage(WebDriver driver) {
		super(driver);
	}
	
	//Someone should add a method to find web elements in the attendance table 
}
