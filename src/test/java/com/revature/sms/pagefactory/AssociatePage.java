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
	
	@FindBy(xpath = "//*[@name=\"mainView\"]/div/md-card/div/div[2]/button[1]")
	public WebElement prevWeek;

	@FindBy(xpath = "//*[@name=\"mainView\"]/div/md-card/div/div[2]/button[2]")
	public WebElement nextWeek;

	@FindBy(xpath = "//*[@name=\"mainView\"]/div/md-card/div/div[2]/div")
	public WebElement weekOf;

	public AssociatePage(WebDriver driver) {
		super(driver);
	}

	
	//This method is used to return check-in and verification data from the attendance table on the Associate page.
	public ArrayList<String> goThroughWeekIcons() {
		// no icon = no string
		// checkmark = "done"
		// double checkmark = "done_all"
		// x = "close"
		ArrayList<String> icons = new ArrayList<String>();
		for (int i = 1; i <= 5; i++) {
			WebElement e = driver.findElement(By.xpath("//tbody/tr/td[" + i + "]/div/md-icon"));
			String text = e.getText();
			icons.add(text.trim());
		}
		return icons;
	}
	
	
	//The following methods were designed for AssociateTP:
	public void openPanel(WebElement panel) {
		Utils.attemptWait(500);  //Waits half a second for the panels to load
		WebElement open = panel.findElement(By.xpath("md-expansion-panel-collapsed/md-icon"));
		open.click();
	}
	
	public void closePanel(WebElement panel) {
		WebElement close = panel.findElement(By.xpath("md-expansion-panel-expanded/md-expansion-panel-header/div[1]/md-icon"));
		close.click();
	}
	
	
	public ArrayList<String> goThroughUserInfo() {
		ArrayList<String> userInfo = new ArrayList<String>();
		List<WebElement> rows = userInfoPanel.findElements(By.xpath("//*[@class=\"expansionPanelList\"]/md-list-item"));
		for (WebElement row:rows) {
			if ("_md-button-wrap _md md-clickable".equals(row.getAttribute("class"))) {  //Because apparently, there are empty md-list-items that are of a different class 
				WebElement button = row.findElement(By.tagName("button"));
				//Most of this just gets rid of extraneous whitespace in the current roe
				String text = button.getAttribute("aria-label");
				text = text.replace("\n"," ");
				String[] splitText = text.split("  ");
				String rowTitle = splitText[0];
				String rowValue = splitText[15].trim();  
				String reformedText = rowTitle+": "+rowValue;
				
				//Special formatting is needed for dates
				if ("Graduation date".equals(rowTitle)) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
					LocalDate dateObject = LocalDate.parse(rowValue, formatter);
					reformedText = rowTitle+": "+dateObject.toString();
				}
	
				userInfo.add(reformedText);
			}
		}
		return userInfo;
	}
	
	
	public ArrayList<String> goThroughSkills() {
		ArrayList<String> skills = new ArrayList<String>();
		String skillText = skillsPanel.findElement(By.xpath("md-expansion-panel-expanded/md-expansion-panel-content")).getText();
		//Parses through the skills, which are seperated by commas
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
		
		//Splits the text on the button by line
		WebElement button = eventsPanel.findElement(By.xpath("//*[@class=\"expansionPanelList md-dense\"]/md-list-item["+eventNumber+"]/div/button"));
		String eventText = button.getAttribute("aria-label");
		System.out.println(eventText);
		String[] textSplit = eventText.split("\n");
		
		//Puts each line (that actually has text) into an ArrayList
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
		
		//Each line is further split, trimmed, and given a key to identify the type of information it contains
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
	
	
	//Given the type and date of a task, this method can find all the information about that task on 
	//the associate page and return it
	public HashMap<String, String> findTask(String taskType, LocalDate expectedDate) {
		HashMap<String, String> hm = new HashMap<String, String>();
		try {
			List<WebElement> elements =  tasksPanel.findElements(By.xpath("//*[@class=\"expansionPanelList\"]/*"));
			//Shorthand for identifying what type of task to search for
			boolean certSearch = "Certification".equals(taskType);
			boolean panelSearch = "Panel".equals(taskType);
			
			boolean isTask = false;
			boolean taskMatches = false;
			//This loop goes through all the WebElements in the the Tasks panel.
			for (WebElement e:elements) {
				String tagName = e.getTagName();
				//We should add to the hash when the element has information specific to the task.
				//Only md-list-item elements with a role attribute of listitem fit these requirements.
				isTask = "md-list-item".equals(tagName) && e.getText()!=null;
				
				//The div elements in the task panel have text that reveal the types of the following tasks.
				if ("div".equals(tagName)) {
					String header = e.getText();
					String[] splitHeader = header.split("\n");  //We only need the text before the first new line.
					if (certSearch) {
						taskMatches = "Certifications".equals(splitHeader[0]);
					}
					if (panelSearch) {
						System.out.println("Task Type 2: "+taskType);
						taskMatches = "Panels".equals(splitHeader[0]);
					}
				} 
				
				//This runs when we know that the current element has information about the type of task
				//we're looking for
				if (isTask && taskMatches) {
					//Formats the date so it can be compared to the expected date
					String date = e.findElement(By.tagName("p")).getText().trim();
					date = date.replace("On ", "");
					date = date.replace("Scheduled for ", "");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
					LocalDate dateObject = LocalDate.parse(date, formatter);
					
					//We only add information to the hash when the task has the date we expect
					if (expectedDate.compareTo(dateObject) == 0) {
						hm.put("taskDate", dateObject.toString());
						String icon = e.findElement(By.tagName("md-icon")).getText().trim();
						if ("done".equals(icon)) {
							hm.put("taskStatus", "true");
						} else if ("close".equals(icon)) {
							hm.put("taskStatus", "false");
						} else {
							hm.put("taskStatus", "???");
						}
						
						//These if statements get information that is only relevant for specific types of tasks.
						if (certSearch) {
							hm.put("taskType", "Certification");
							hm.put("taskNote", e.findElement(By.tagName("h3")).getText().trim());
						}
						
						if (panelSearch) {
							hm.put("taskType", "Panel");
						}	
					}
				}
			}
		} catch (NoSuchElementException exception) {}
		return hm;	
	}
	
	
}