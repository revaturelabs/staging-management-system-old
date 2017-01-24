package com.revature.sms.pagefactory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AssociatePage extends SMSPage {

	@FindBy(tagName="title")
	public WebElement title;
	
	@FindBy(css="[class=\"ng-binding flex\"]")
	public WebElement header;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/md-toolbar/div/button[1]/md-icon")
	public WebElement menuIcon;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/md-toolbar/div/button[3]/md-icon")
	public WebElement logoutIcon;
	
	@FindBy(tagName="tbody")
	public WebElement weekTable;
	
	@FindBy(css="[ng-click=\"assWeekAttCtrl.getPreviousWeek()\"]")
	public WebElement prevWeek;
	
	@FindBy(css="[ng-click=\"assWeekAttCtrl.getNextWeek()\"]")
	public WebElement nextWeek;
	
	@FindBy(xpath="/html/body/div/div/ui-view[2]/md-card/div/ui-view/div[3]/div[2]/p")
	public WebElement weekOf;
	
	@FindBy(className="md-material-icon menu-icon ng-binding ng-scope material-icons")
	public WebElement certification;
	
	@FindBy(className="md-material-icon menu-icon ng-scope material-icons")
	public WebElement settings;

	public AssociatePage(WebDriver driver) {
		super(driver);
	}
	
	public ArrayList<String> goThroughWeek() {
		ArrayList<String> monthDayStrings = new ArrayList<String>();
		for (int i=2; i<=6; i++) {
			WebElement e = driver.findElement(By.xpath("//tbody/tr[1]/td["+i+"]"));
			String text = e.getText();
			String mawd = text.replace("\n", "");
			String pattern;
			if (mawd.contains("10/") || mawd.contains("11/") || mawd.contains("12/")) {
				pattern = "\\d\\d/\\d\\d";
			} else {
				pattern = "\\d/\\d\\d";
			}	
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(mawd);
			if (m.find()) {
				monthDayStrings.add(m.group());
			}
		}
		return monthDayStrings;
	}
	
	
	//no icon = no string
	//checkmark = "done"
	//double checkmark = "done_all"
	//x = "close"
	
	//ERROR??? The icons are colored in the list but not in the table
	public ArrayList<String> goThroughWeekIcons() {
		ArrayList<String> icons = new ArrayList<String>();
		for (int i=2; i<=6; i++) {
			WebElement e = driver.findElement(By.xpath("//tbody/tr[2]/td["+i+"]/div/md-icon"));
			String text = e.getText();
			//System.out.println(text.trim());
			icons.add(text.trim());
		}
		return icons;	
	}

}
