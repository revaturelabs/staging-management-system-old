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

public class AssociatePage extends HomePage {
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/md-card/md-toolbar/div/button[1]/md-icon")
	public WebElement checkincheckout;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/md-card/md-toolbar/div/button[2]/md-icon")
	public WebElement events;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/md-card/md-toolbar/div/button[3]/md-icon")
	public WebElement certification;
	
	@FindBy(tagName="tbody")
	public WebElement weekTable;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/ui-view/div/div/div[2]/button[1]/md-icon")
	public WebElement prevWeek;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/ui-view/div/div/div[2]/button[2]/md-icon")
	public WebElement nextWeek;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/ui-view/div/div/div[2]/div")
	public WebElement weekOf;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/ui-view/div/md-table-container/table/thead/tr/th")
	public List<WebElement> attendanceCells;
	
	public AssociatePage(WebDriver driver) {
		super(driver);
	}
	
	public ArrayList<MonthDay> goThroughWeek() {
		ArrayList<MonthDay> monthDays = new ArrayList<MonthDay>();
		for (WebElement e:attendanceCells) {
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
					text = "0"+text;
				}
				text = "--"+text;
				MonthDay md = MonthDay.parse(text);
				monthDays.add(md);
			}
			
		}
		return monthDays;
	}
	
	
	//no icon = no string
	//checkmark = "done"
	//double checkmark = "done_all"
	//x = "close"
	
	public ArrayList<String> goThroughWeekIcons() {
		ArrayList<String> icons = new ArrayList<String>();
		for (int i=1; i<=5; i++) {
			WebElement e = driver.findElement(By.xpath("//tbody/tr/td["+i+"]/div/md-icon"));
			String text = e.getText();
			icons.add(text.trim());
		}
		return icons;	
	}

}
