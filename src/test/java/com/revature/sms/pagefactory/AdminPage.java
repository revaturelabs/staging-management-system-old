package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends HomePage {

	@FindBy(id="associateInformationDiv")
	public WebElement panelDiv;
	
	@FindBy(id="attendanceTableDiv")
	public WebElement attendanceDiv;
	
	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/button[1]")
	public WebElement prevWeekTop;

	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/button[2]")
	public WebElement nextWeekTop;

	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/div")
	public WebElement weekOfTop;

	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/button[1]")
	public WebElement prevWeekBottom;

	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/button[2]")
	public WebElement nextWeekBottom;

	@FindBy(xpath="//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/div")
	public WebElement weekOfBottom;
	
	@FindBy(xpath="//*[@class=\"attendanceTable\"]/table/tbody/tr[1]/td[2]")
	public WebElement sampleAttendanceTableCell;

	
	public AdminPage(WebDriver driver) {
		super(driver);
	}
	
	
	/*
	public ArrayList<String> getAssociateNames(String infoType) {
		ArrayList<String> info = new ArrayList<String>();
		List<WebElement> rows = attendanceBody.findElements(By.tagName("tr"));
		if ("username".equals(infoType)) {
			for (WebElement row:rows) {
				info.add(row.findElement(By.xpath("//td[1]/div/div[2]/p")).getText());
			}
		}
		if ("fullName".equals(infoType)) {
			for (WebElement row:rows) {
				info.add(row.findElement(By.xpath("//td[1]/div/div[2]/h3")).getText());
			}
		}
		return info;
	}
	*/
	
}