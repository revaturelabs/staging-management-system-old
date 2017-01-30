package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends SMSPage {
	
	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/button[1]/md-icon")
	public WebElement prevWeekTop;
	
	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/button[2]/md-icon")
	public WebElement nextWeekTop;
	
	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/div")
	public WebElement weekOfTop;
	
	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/button[1]/md-icon")
	public WebElement prevWeekBottom;
	
	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/button[2]/md-icon")
	public WebElement nextWeekBottom;
	
	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/div")
	public WebElement weekOfBottom;
	
	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/md-table-container/table")
	public WebElement attendanceTable;
	
	
	public AdminPage(WebDriver driver) {
		super(driver);
	}
	
	//Someone should add a method to find web elements in the attendance table 
}
