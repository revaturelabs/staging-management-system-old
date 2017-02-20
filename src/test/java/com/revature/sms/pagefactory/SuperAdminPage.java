package com.revature.sms.pagefactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	
	//The following fields are hidden by default upon the page loading. The switchView element should
	//be clicked before any methods involving the following fields are used.
	@FindBy(xpath="//*[@id=\"associateTableViewTableContainer\"]/table/thead/tr")
	public WebElement associateTableHeaders;
	
	@FindBy(xpath="//*[@id=\"associateTableViewTableContainer\"]/table/tbody")
	public WebElement associateTableBody;
	
	
	public SuperAdminPage(WebDriver driver) {
		super(driver);
	}
	
	
	//Inserts the text of header cells into an ArrayList.
	public ArrayList<String> getAssociateTableHeaders() {
		List<WebElement> atHeaderCells = associateTableHeaders.findElements(By.tagName("th"));
		ArrayList<String> atHeaders = new ArrayList<String>();
		for (WebElement headerCell:atHeaderCells) {
			if (!("".equals(headerCell.getText()))) {
				atHeaders.add(headerCell.getText());
			}
		}
		return atHeaders;
	}
	
	//Organizes a single row of information from the Associate Information Table
	public HashMap<String, String> goThroughAssociateTable(WebElement row, Properties expected) {
		HashMap<String, String> hm = new HashMap<String, String>();
		
		ArrayList<String> avHeaders = getAssociateTableHeaders();
		int i = 1;
		for (String avHeader:avHeaders) {
			//The information in each cell is identified by the header above it.
			WebElement cell = row.findElement(By.xpath("td["+i+"]")); 
			
			//Every header should correspond to exactly one cell. This means that each iteration of the
			//"for loop" should result in the next condition in this "if else" construct until
			//every possible outcome has happened in order, exactly once, except for the final outcome,
			//which throws an exception. This is the best way I could think of to coordinate the iteration
			//headers and cells.
			if (avHeader.equals(expected.getProperty("header1"))) {
				hm.put("fullName", cell.getText());
			} else if (avHeader.equals(expected.getProperty("header2"))) {
				hm.put("batchType", cell.getText());
			} else if (avHeader.equals(expected.getProperty("header3"))) {
				hm.put("trainer", cell.getText());
			} else if (avHeader.equals(expected.getProperty("header4"))) {
				String dateText = cell.getText();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy");
				LocalDate parsedDate = LocalDate.parse(dateText, dtf);
				hm.put("marketingStartDate", parsedDate.toString());
			} else if (avHeader.equals(expected.getProperty("header5"))) {
				hm.put("daysOnMarket", cell.getText());
			} else if (avHeader.equals(expected.getProperty("header6"))) {
				hm.put("marketingStatus", cell.getText());
			} else if (avHeader.equals(expected.getProperty("header7"))) {
				List<WebElement> possibleIcons = cell.findElements(By.tagName("md-icon"));
				WebElement passed = possibleIcons.get(0);
				WebElement notPassed = possibleIcons.get(1);
				if ("false".equals(passed.getAttribute("aria-hidden")) && "true".equals(notPassed.getAttribute("aria-hidden"))) {
					hm.put("panelStatus", passed.getText());
				} else if ("true".equals(passed.getAttribute("aria-hidden")) && "false".equals(notPassed.getAttribute("aria-hidden"))) {
					hm.put("panelStatus", notPassed.getText());
				}
				hm.put("panelDate", cell.findElement(By.xpath("span/span[1]")).getText());
			} else if (avHeader.equals(expected.getProperty("header8"))) {
				hm.put("project", cell.getText());
			} else if (avHeader.equals(expected.getProperty("header9"))) {
				String allCertText = cell.getText();
				if (!("".equals(allCertText))) {
					String[] split = allCertText.split(" | ");
					hm.put("certDate", split[0]);
					hm.put("certName", split[2]);
				}
			} else {
				try {
					Exception e = new Exception("Header with this text doesn't exist");
					throw e;
				} catch (Exception e) {
					Logger.getRootLogger().debug(e);
					Thread.currentThread().interrupt();
				}
			}
			i++;
		}
		return hm;
	}
	
}
