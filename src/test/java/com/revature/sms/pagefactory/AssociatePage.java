package com.revature.sms.pagefactory;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.revature.sms.util.Utils;

public class AssociatePage extends HomePage {

	@FindBy(xpath = "//*[@name=\"contentView\"]/md-card/md-toolbar/div/button[1]/md-icon")
	public WebElement checkincheckout;

	@FindBy(xpath = "//*[@name=\"contentView\"]/md-card/md-toolbar/div/button[2]/md-icon")
	public WebElement events;

	@FindBy(xpath = "//*[@name=\"contentView\"]/md-card/md-toolbar/div/button[3]/md-icon")
	public WebElement certification;

	@FindBy(xpath = "//*[@md-component-id=\"userInfoPanel\"]/md-expansion-panel-collapsed/md-icon")
	public WebElement userInfoPanel;
	
	@FindBy(xpath = "//*[@md-component-id=\"skillPanel\"]/md-expansion-panel-collapsed/md-icon")
	public WebElement skillPanel;
	
	@FindBy(xpath = "//*[@md-component-id=\"eventPanel\"]/md-expansion-panel-collapsed/md-icon")
	public WebElement eventPanel;
	
	@FindBy(xpath = "//*[@md-component-id=\"taskPanel\"]/md-expansion-panel-collapsed/md-icon")
	public WebElement taskPanel;
	
	@FindBy(tagName = "tbody")
	public WebElement weekTable;
	
	@FindBy(xpath = "//*[@name=\"mainView\"]/div/md-card/div/div[2]/button[1]/md-icon")
	public WebElement prevWeek;

	@FindBy(xpath = "//*[@name=\"mainView\"]/div/md-card/div/div[2]/button[2]/md-icon")
	public WebElement nextWeek;

	@FindBy(xpath = "//*[@name=\"mainView\"]/div/md-card/div/div[2]/div")
	public WebElement weekOf;

	@FindBy(xpath = "//*[@name=\"mainView\"]/div/md-card/md-table-container/table/thead/tr/th")
	public List<WebElement> attendanceCells;

	public AssociatePage(WebDriver driver) {
		super(driver);
	}

	public ArrayList<MonthDay> goThroughWeek() {
		ArrayList<MonthDay> monthDays = new ArrayList<MonthDay>();
		for (WebElement e : attendanceCells) {
			String text = e.getText();
			text = text.replace("\n", "");
			text = text.replace("/", "-");
			String pattern;
			boolean addZero = false;

			if (text.contains("10-") || text.contains("11-") || text.contains("12-")) {
				pattern = "\\d\\d-\\d\\d";
			} else {
				pattern = "\\d-\\d\\d";
				addZero = true;
			}

			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(text);
			if (m.find()) {
				text = m.group();
				if (addZero) {
					text = "0" + text;
				}
				text = "--" + text;
				MonthDay md = MonthDay.parse(text);
				monthDays.add(md);
			}
		}
		return monthDays;
	}

	
	// no icon = no string
	// checkmark = "done"
	// double checkmark = "done_all"
	// x = "close"
	public ArrayList<String> goThroughWeekIcons() {
		ArrayList<String> icons = new ArrayList<String>();
		for (int i = 1; i <= 5; i++) {
			WebElement e = driver.findElement(By.xpath("//tbody/tr/td[" + i + "]/div/md-icon"));
			String text = e.getText();
			icons.add(text.trim());
		}
		return icons;
	}
	
	
	public ArrayList<String> goThroughUserInfo() {
		ArrayList<String> userInfo = new ArrayList<String>();
		List<WebElement> rows = driver.findElements(By.xpath("//md-expansion-panel[1]/md-expansion-panel-expanded/md-expansion-panel-content/md-list/md-list-item"));
		for (WebElement row:rows) {
			WebElement button = row.findElement(By.tagName("button"));
			String text = button.getAttribute("aria-label");
			text = text.replace("\n"," ");
			String[] splitText = text.split("  ");
			String rowTitle = splitText[0];
			String rowValue = splitText[15].trim();
			String reformedText = rowTitle+": "+rowValue;
			
			if ("Graduation date".equals(rowTitle)) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
				LocalDate dateObject = LocalDate.parse(rowValue, formatter);
				reformedText = rowTitle+": "+dateObject.toString();
			}

			userInfo.add(reformedText);
		}
		return userInfo;
	}
	
	
}