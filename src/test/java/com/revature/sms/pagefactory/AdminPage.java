package com.revature.sms.pagefactory;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends HomePage {

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/button[1]")
	public WebElement prevWeekTop;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/button[2]")
	public WebElement nextWeekTop;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[1]/div[2]/div")
	public WebElement weekOfTop;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/button[1]")
	public WebElement prevWeekBottom;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/button[2]")
	public WebElement nextWeekBottom;

	@FindBy(xpath = "//*[@id=\"attendanceTableDiv\"]/md-card/div/div[2]/div[2]/div")
	public WebElement weekOfBottom;
	
	@FindBy(xpath = "//*[@class=\"attendanceTable\"]/table/tbody/tr[1]/td[2]")
	public WebElement sampleAttendanceTableCell;

	@FindBy(id = "closeIcon")
	public WebElement closeIcon;

	@FindBy(id = "associateInformationDiv")
	public WebElement verifyInfoDiv;

	public AdminPage(WebDriver driver) {
		super(driver);
	}
	
	
	public boolean infoDisplayed() {
		return verifyInfoDiv.isDisplayed();
	}
	
	public ArrayList<String> goThroughWeekIcons(int rowNumber) {
		// no icon = no string
		// checkmark = "done"
		// double checkmark = "done_all"
		// x = "close"
		ArrayList<String> icons = new ArrayList<String>();
		for (int i = 2; i <= 6; i++) {
			WebElement e = attendanceBody.findElement(By.xpath("//tr["+rowNumber+"]/td["+i+"]/div/md-icon"));
			String text = e.getText();
			icons.add(text.trim());
		}
		return icons;
	}
	
	
	public WebElement getRowByUsername(String expectedUN) {
		
		//*[@id="attendanceTableDiv"]/md-card/div/md-table-container/table/tbody/tr[1]/td[1]/div/div[2]/p
		
		
		
		ArrayList<WebElement> rows = new ArrayList<WebElement>();
		int i = 1;
		try {
			while (attendanceBody.findElement(By.xpath("tr["+i+"]")) != null) {
				System.out.println();
				System.out.println("Every Row Number:");
				System.out.println(attendanceBody.findElement(By.xpath("tr[1]/td[1]/div/h2")));
				System.out.println(attendanceBody.findElement(By.xpath("tr[1]/td[1]/div/h2")).getText());
				System.out.println(attendanceBody.findElement(By.xpath("tr[2]/td[1]/div/h2")));
				System.out.println(attendanceBody.findElement(By.xpath("tr[2]/td[1]/div/h2")).getText());
				System.out.println(attendanceBody.findElement(By.xpath("tr[3]/td[1]/div/h2")));
				System.out.println(attendanceBody.findElement(By.xpath("tr[3]/td[1]/div/h2")).getText());
				System.out.println(attendanceBody.findElement(By.xpath("tr[4]/td[1]/div/h2")));
				System.out.println(attendanceBody.findElement(By.xpath("tr[4]/td[1]/div/h2")).getText());
				
				//[[[[ChromeDriver: chrome on XP (78872c396dd27449e34412cbdca5b98d)] -> xpath: //*[@class="attendanceTable"]/table/tbody]] -> xpath: //tr[2]/td[1]/div/h2]
				//[[[[[[ChromeDriver: chrome on XP (78872c396dd27449e34412cbdca5b98d)] -> xpath: //*[@class="attendanceTable"]/table/tbody]] -> xpath: //tr[2]]] -> xpath: //td[1]/div/h2]
				
				
				WebElement row = attendanceBody.findElement(By.xpath("tr["+i+"]"));
				
				System.out.println();
				System.out.println("Table Row Number in Loop");
				System.out.println("tr["+i+"]");
				
				System.out.println();
				WebElement actualUNCell = row.findElement(By.xpath("td[1]/div/div[2]/p"));
				System.out.println(actualUNCell);
				System.out.println("Actual UN 1: "+actualUNCell.getText());
				
				WebElement rowNumber = row.findElement(By.xpath("td[1]/div/h2"));
				System.out.println(rowNumber);
				System.out.println("Row Number 1: "+rowNumber.getText());
				
				
				
				rows.add(attendanceBody.findElement(By.xpath("tr["+i+"]")));
				i++;
			}
		} catch (NoSuchElementException e) {
			System.out.println("Here");
		}
			
		
		
		for (WebElement row:rows) {
			
			System.out.println();
			WebElement actualUNCell = row.findElement(By.xpath("td[1]/div/div[2]/p"));
			System.out.println(actualUNCell);
			System.out.println("Actual UN 2: "+actualUNCell.getText());
			
			WebElement rowNumber = row.findElement(By.xpath("td[1]/div/h2"));
			System.out.println(rowNumber);
			System.out.println("Row Number 2: "+rowNumber.getText());
			
			if (expectedUN.equals(actualUNCell.getText())) {
				return row;
			}
		}
		return null;
	}
	
	
	
	
	
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
	
}