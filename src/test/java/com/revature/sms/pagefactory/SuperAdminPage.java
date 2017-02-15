package com.revature.sms.pagefactory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SuperAdminPage extends AdminPage {
	
	@FindBy(tagName="md-switch")
	public WebElement switchView;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/md-card/md-toolbar/div/button[1]/md-icon")
	public WebElement addBatch;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/md-card/md-toolbar/div/button[2]/md-icon")
	public WebElement editSkills;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/md-card/md-toolbar/div/button[3]/md-icon")
	public WebElement updateProject;
	
	
	public SuperAdminPage(WebDriver driver) {
		super(driver);
	}
	
	public List<WebElement> getAssociateInfoRows() {
		List<WebElement> rows = driver.findElements(By.tagName("tr"));
		return rows;
	}
	
	
}
