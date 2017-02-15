package com.revature.sms.pagefactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SuperAdminPage extends AdminPage {
	
	@FindBy(tagName="md-switch")
	public WebElement switchView;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/md-card/md-toolbar/div/button[1]/md-icon")
	public WebElement addBatch;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/md-card/md-toolbar/div/button[2]/md-icon")
	public WebElement editSkills;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/md-card/md-toolbar/div/button[3]/md-icon")
	public WebElement updateProject;
	
	
	public SuperAdminPage(WebDriver driver) {
		super(driver);
	}
	
	
	public ArrayList<String> getAssociateViewHeaders() {
		List<WebElement> avHeaderCells = driver.findElements(By.xpath("thead/tr/th"));
		ArrayList<String> avHeaders = new ArrayList<String>();
		for (WebElement headerCell:avHeaderCells) {
			avHeaders.add(headerCell.getText());
		}
		return avHeaders;
	}
	
	
	public HashMap<String, String> goThroughAssociateView(WebElement row, Properties expected) {
		HashMap<String, String> hm = new HashMap<String, String>();
		
		ArrayList<String> avHeaders = getAssociateViewHeaders();
		int i = 1;
		for (String header: avHeaders) {
			WebElement cell = row.findElement(By.xpath("td["+i+"]"));
			
			if (header.equals(expected.getProperty("header1"))) {
				hm.put("fullName", cell.getText());
			} else if (header.equals(expected.getProperty("header1"))) {
				hm.put("batchType", cell.getText());
			} else if (header.equals(expected.getProperty("header1"))) {
				hm.put("trainer", cell.getText());
			} else if	(header.equals(expected.getProperty("header1"))) {
				hm.put("marketingStartDate", cell.getText());
			} else if	(header.equals(expected.getProperty("header1"))) {
				hm.put("daysOnMarket", cell.getText());
			} else if 	(header.equals(expected.getProperty("header1"))) {
				hm.put("marketingStatus", cell.getText());
			} else if	(header.equals(expected.getProperty("header1"))) {
				List<WebElement> possibleIcons = cell.findElements(By.tagName("md-icon"));
				WebElement passed = possibleIcons.get(0);
				WebElement notPassed = possibleIcons.get(1);
				if ("false".equals(passed.getAttribute("aria-hidden")) && "true".equals(notPassed.getAttribute("aria-hidden"))) {
					hm.put("panelStatus", passed.getText());
				} else if ("true".equals(passed.getAttribute("aria-hidden")) && "false".equals(notPassed.getAttribute("aria-hidden"))) {
					hm.put("panelStatus", notPassed.getText());
				}
				hm.put("panelDate", cell.findElement(By.xpath("span/span[1]")).getText());
			} else if	(header.equals(expected.getProperty("header1"))) {
				hm.put("project", cell.getText());
			} else if	(header.equals(expected.getProperty("header1"))) {
				String allCertText = cell.getText();
				String[] split = allCertText.split(" | ");
				hm.put("certDate", split[0]);
				hm.put("certName", split[1]);
			} else {
				try {
					Exception e = new Exception("Header with this text doesn't exist");
					throw e;
				} catch (Exception e) {
					Logger.getRootLogger().debug(e);
					Thread.currentThread().interrupt();
				}
			}
			
		}
		return hm;
	}
	
}
