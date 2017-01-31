package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends HomePage {
	
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
	
	@FindBy(xpath="//*[@id='attendanceTableDiv']/md-card/div/md-table-container/table/tbody/tr[1]/td[1]")
	public WebElement javaAssocClick;
	
	@FindBy(xpath="//*[@id='attendanceTableDiv']/md-card/div/md-table-container/table/tbody/tr[2]/td[1]")
	public WebElement dotNetAssocClick;
	
	@FindBy(xpath="//*[@id='attendanceTableDiv']/md-card/div/md-table-container/table/tbody/tr[3]/td[1]")
	public WebElement sdetAssocClick;
	
	@FindBy(xpath="//*[@id='closeIcon']")
	public WebElement closeIcon;
	
	@FindBy(xpath="//*[@id='attendanceTableDiv']/md-card/div/md-table-container/table/tbody/tr[1]/td[2]")
	public WebElement verifyAssoc;
	
	@FindBy(id="associateInformationDiv")
	public WebElement verifyInfoDiv;
	
	public boolean infoDisplayed(){
		return verifyInfoDiv.isDisplayed();
	}
		
	public AdminPage(WebDriver driver) {
		super(driver);
	}
	
	//Someone should add a method to find web elements in the attendance table 
}
