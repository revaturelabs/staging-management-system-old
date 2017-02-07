package com.revature.sms.pagefactory;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

	@FindBy(xpath = "//*[@md-component-id=\"userInfoPanel\"]")
	public WebElement userInfoPanel;
	
	@FindBy(xpath = "//*[@md-component-id=\"skillPanel\"]")
	public WebElement skillsPanel;
	
	@FindBy(xpath = "//*[@md-component-id=\"eventPanel\"]")
	public WebElement eventsPanel;
	
	@FindBy(xpath = "//*[@md-component-id=\"taskPanel\"]")
	public WebElement tasksPanel;
	
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
	
	
	
	
	public void openPanel(WebElement panel) {
		Utils.attemptWait(500);
		WebElement open = panel.findElement(By.xpath("md-expansion-panel-collapsed/md-icon"));
		open.click();
	}
	
	public void closePanel(WebElement panel) {
		WebElement close = panel.findElement(By.xpath("md-expansion-panel-expanded/md-expansion-panel-header/div[1]/md-icon"));
		close.click();
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
	
	
	public ArrayList<String> goThroughSkills() {
		ArrayList<String> skills = new ArrayList<String>();
		String skillText = skillsPanel.findElement(By.xpath("md-expansion-panel-expanded/md-expansion-panel-content")).getText();
		String[] splitSkillText = skillText.split(",");
		int i=0;
		while (i<splitSkillText.length) {
			String skill = splitSkillText[i];
			skill = skill.trim();
			skills.add(skill);
			i++;
		}
		return skills;
	}
	
	
	
	public HashMap<String, String> goThroughEvent(int eventNumber) {
		HashMap<String, String> hm = new HashMap<String, String>();
		WebElement button = eventsPanel.findElement(By.xpath("md-expansion-panel-expanded/md-expansion-panel-content/md-list/md-list-item["+eventNumber+"]/div/button"));
		String eventText = button.getAttribute("aria-label");
		String[] textSplit = eventText.split("\n");
		
		ArrayList<String> eventData = new ArrayList<String>();
		int i=0;
		while (i<textSplit.length) {
			String textPiece = textSplit[i];
			textPiece = textPiece.trim();
			if (!"".equals(textPiece)) {
				eventData.add(textPiece);
			}
			i++;
		}
		
		String line1 = eventData.get(0);
		String[] split1 = line1.split("-");
		hm.put("companyName", split1[0].trim());
		hm.put("companyLocation", split1[1].trim());
		
		String line2 = eventData.get(1);
		String[] split2 = line2.split("-");
		hm.put("eventType", split2[0].trim());
		hm.put("jobTitle", split2[1].trim());
		
		hm.put("eventNote", eventData.get(2));
		
		String date = eventData.get(3);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
		LocalDate dateObject = LocalDate.parse(date, formatter);
		hm.put("eventDate", dateObject.toString());
		
		hm.put("eventLocation", eventData.get(4));
		
		return hm;
	}
	
	
	public HashMap<String, String> goThroughTasks() {
		HashMap<String, String> hm = new HashMap<String, String>();
		try {
			List<WebElement> allTasks = tasksPanel.findElements(By.xpath("//*[@class=\"expansionPanelList\"]/*"));
			boolean c = false;
			boolean p = false;
			int i=0;
			for (WebElement task:allTasks) {
				if ("div".equals(task.getTagName())) {
					if ("Certifications".equals(task.getText())) {
						c = true;
						p = false;
					}
					if ("Panels".equals(task.getText())) {
						c = false;
						p = true;
					}
				}
				
				if ("md-list-item".equals(task.getTagName()) && "listItem".equals(task.getAttribute("role"))) {
					if (c) {
						hm.put("taskType", "Certification");
						hm.put("taskNote", task.findElement(By.tagName("h3")).getText().trim());
					}
					if (p) {
						hm.put("taskType", "Panel");
					}
					
					WebElement element = task.findElement(By.tagName("p"));
					String date = task.findElement(By.tagName("p")).getText().trim();
					
					date = date.replace("On ", "");
					date = date.replace("Scheduled for ", "");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
					LocalDate dateObject = LocalDate.parse(date, formatter);
					hm.put("taskDate", dateObject.toString());
					
					String icon = task.findElement(By.tagName("md-icon")).getText().trim();
					if ("done".equals(icon)) {
						hm.put("taskStatus", "True");
					}
					if ("close".equals(icon)) {
						hm.put("taskStatus", "False");
					}	
				}
			}
		} catch (NoSuchElementException e) {}
		return hm;
	}
	
	
	
	
	
	
}