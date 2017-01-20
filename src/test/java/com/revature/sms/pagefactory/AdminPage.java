package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends SMSPage {

	@FindBy(tagName="title")
	public WebElement title;
	
	@FindBy(css="[class=\"ng-binding flex\"]")
	public WebElement header;
	
	@FindBy(css="[md-svg-icon=\"img/icons/ic_menu_white_24px.svg\"]")
	public WebElement menuIcon;
	
	@FindBy(css="[md-svg-icon=\"img/icons/ic_exit_to_app_white_24px.svg\"]")
	public WebElement logoutIcon;
	
	@FindBy(css="[ng-model=\"searchBox\"]")
	public WebElement searchBox;
	
	@FindBy(css="[ng-click=\"goBackOneWeek()\"]")
	public WebElement prevWeek;
	
	@FindBy(css="[ng-click=\"goForwardOneWeek()\"]")
	public WebElement nextWeek;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/div/ui-view/md-table-container/table[1]/tbody/tr/th[4]")
	public WebElement weekOf;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/div/ui-view/md-table-container/table[2]")
	public WebElement attendanceTable;
	
	
	
	public AdminPage(WebDriver driver) {
		super(driver);
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
