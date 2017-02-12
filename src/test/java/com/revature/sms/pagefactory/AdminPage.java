package com.revature.sms.pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends HomePage {

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/button[1]/md-icon")
	public WebElement prevWeekTop;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/button[2]/md-icon")
	public WebElement nextWeekTop;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/div")
	public WebElement weekOfTop;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/button[1]/md-icon")
	public WebElement prevWeekBottom;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/button[2]/md-icon")
	public WebElement nextWeekBottom;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/div")
	public WebElement weekOfBottom;
	
	@FindBy(xpath = "//*[@class=\"attendanceTable\"]/table/tbody/tr[1]/td[2]")
	public WebElement sampleAttendanceTableCell;

	@FindBy(id = "closeIcon")
	public WebElement closeIcon;

	@FindBy(id = "associateInformationDiv")
	public WebElement verifyInfoDiv;

	public boolean infoDisplayed() {
		return verifyInfoDiv.isDisplayed();
	}

	public AdminPage(WebDriver driver) {
		super(driver);
	}
}