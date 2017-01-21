package com.revature.sms.pagefactory;

import java.util.ArrayList;

import org.openqa.selenium.By;
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
	public WebElement weekTable;
	
	@FindBy(css="[ng-click=\"assWeekAttCtrl.getPreviousWeek()\"]")
	public WebElement prevWeek;
	
	@FindBy(css="[ng-click=\"assWeekAttCtrl.getNextWeek()\"]")
	public WebElement nextWeek;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/div/ui-view/div[3]/div[2]/p")
	public WebElement weekOf;
	

	public AssociatePage(WebDriver driver) {
		super(driver);
	}
	
	public ArrayList<String> goThroughWeek() {
		ArrayList<String> weekdayStrings = new ArrayList<String>();
		for (int i=2; i<=6; i++) {
			WebElement weekday = driver.findElement(By.xpath("//tbody/tr[1]/td["+i+"]"));
			weekdayStrings.add(weekday.getText());
		}
		return weekdayStrings;
		
	}
	
	/*
	public void searchThroughTable() throws InterruptedException {
		page.getElement("homeLogin", "name").click();
		page.enterText("usernameField", "adminUsername");
		page.enterText("passwordField", "adminPassword");
		page.getElement("login", "name").click();
		page.getElement("adminDB", "name").click();
		
		List<WebElement> rows = page.getElements("table", "xpath");
		for (int i=1; i<=rows.size(); i++) {
			WebElement row = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[1]"));
			System.out.println(row.getText());
		}
	}
	*/

}
