package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends HomePage {

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/button[1]")
	public WebElement prevWeekTop;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/button[2]")
	public WebElement nextWeekTop;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/div")
	public WebElement weekOfTop;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/button[1]")
	public WebElement prevWeekBottom;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/button[2]")
	public WebElement nextWeekBottom;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/div")
	public WebElement weekOfBottom;
	
	@FindBy(xpath = "//*[@class=\"attendanceTable\"]/table/tbody/tr[1]/td[2]")
	public WebElement sampleAttendanceTableCell;

	@FindBy(id = "closeIcon")
	public WebElement closeIcon;

	@FindBy(id = "associateInformationDiv")
	public WebElement verifyInfoDiv;

	public AdminPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean infoDisplayed() {
		return verifyInfoDiv.isDisplayed();
	}
	
}