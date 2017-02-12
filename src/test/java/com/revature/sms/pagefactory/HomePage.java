package com.revature.sms.pagefactory;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public WebElement attendanceCells;
	
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	
	//This method is used to organize and return dates from the attendance table.
	public ArrayList<MonthDay> goThroughWeek() {
		ArrayList<MonthDay> monthDays = new ArrayList<MonthDay>();
		List<WebElement> allHeaders = attendanceHeaders.findElements(By.tagName("th"));
		for (WebElement e : allHeaders) {
			String text = e.getText();
			text = text.replace("\n", "");
			text = text.replace("/", "-");
			String pattern;
			boolean addZero = false;  

			//Regular expression classes are used to find date related information within a larger string. 
			if (text.contains("10-") || text.contains("11-") || text.contains("12-")) { //October through December
				pattern = "\\d\\d-\\d\\d";
			} else {
				pattern = "\\d-\\d\\d";
				addZero = true;  //example: 2-6 becomes 02-6
			}

			
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(text);
			if (m.find()) {
				text = m.group();
				if (addZero) {
					text = "0" + text;
				}
				text = "--" + text;  //The dashes are just to make MonthDay's parse method happy.
				MonthDay md = MonthDay.parse(text);
				monthDays.add(md);
			}
		}
		return monthDays;
	}
	
	
	
	

	
}
