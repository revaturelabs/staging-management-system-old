package com.revature.sms.pagefactory;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SuperAdminPage extends AdminPage {
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/md-card/md-toolbar/div/button[1]/md-icon")
	public WebElement addBatch;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/md-card/md-toolbar/div/button[2]/md-icon")
	public WebElement settings;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/md-card/md-toolbar/div/button[3]/md-icon")
	public WebElement logout;
	
	public SuperAdminPage(WebDriver driver) {
		super(driver);
	}
	
	
}
