package com.revature.sms.pagefactory;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

//This class contains WebElements that are shared by the Associate, Admin, and Super Admin home pages.
public class HomePage extends SMSPage {

	@FindBy(css="[class=\"ng-binding flex\"]")
	public WebElement header;
	
	@FindBy(xpath="//*[@aria-label=\"Account settings\"]")
	public WebElement settings;
	
	@FindBy(xpath="//*[@aria-label=\"Logout\"]")
	public WebElement logout;
	
	@FindBy(id="atlwdg-trigger")
	public WebElement reportBug;
	
	@FindBy(xpath = "//*[@class=\"attendanceTable\"]/table/thead/tr")
	public WebElement attendanceHeaders;
	
	@FindBy(xpath = "//*[@class=\"attendanceTable\"]/table/tbody")
	public WebElement attendanceBody;
	
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	
	public String getThisMondayFromHeader() {
		String monday = attendanceHeaders.findElement(By.tagName("p")).getText();
		if (monday.contains("/0")) {
			monday = monday.replace("0", "");
		}
		return monday;
	}
	
	//This method is used to organize and return dates from the attendance table.
	public ArrayList<MonthDay> goThroughWeek() {
		ArrayList<MonthDay> monthDays = new ArrayList<MonthDay>();
		List<WebElement> allHeaders = attendanceHeaders.findElements(By.tagName("p"));
		for (WebElement e : allHeaders) {
			String text = e.getText();
			text = text.replace("/", "-");
			String[] split = text.split("-");	
			//Add dashes and possibly a zero to make MonthDay's parse method happy.
			if (split[0].length()<2) {   
				text = "0" + text;
			}
			text = "--" + text;  
			MonthDay md = MonthDay.parse(text);
			monthDays.add(md);
		}
		return monthDays;
	}
	
	//This method is used to return check-in and verification data from the attendance table.
	public ArrayList<String> goThroughWeekIcons(WebElement row) {
		// no icon = no string
		// checkmark = "done"
		// double checkmark = "done_all"
		// x = "close"
		ArrayList<String> icons = new ArrayList<String>();
		List<WebElement> iconCells = row.findElements(By.tagName("md-icon"));
		for (WebElement e: iconCells) {
			if (e.isDisplayed()) {  //Because on the admin page, the Associate Name column has an md-icon that is not displayed or relevant to this program.
				String text = e.getText();
				icons.add(text.trim());
			}
		}
		return icons;
	}
	
	public ArrayList<String> goThroughWeekIcons() {
		ArrayList<String> icons = goThroughWeekIcons(attendanceBody.findElement(By.tagName("tr")));
		return icons;
	}
	
	
	public WebElement getRowByIdentifier(WebElement tableBody, String id, String idLocation) {
		List<WebElement> rows = tableBody.findElements(By.tagName("tr"));
		for (WebElement row:rows) {
			WebElement cell = row.findElement(By.xpath(idLocation));
			if (id.equals(cell.getText())) {
				return row;
			}
		}
		return null;
	}
	
	
}
