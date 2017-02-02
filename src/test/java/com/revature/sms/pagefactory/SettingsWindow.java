package com.revature.sms.pagefactory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SettingsWindow extends SMSPage {

	@FindBy(id="oldPass")
	public WebElement oldPass;
	
	@FindBy(id="newPass")
	public WebElement newPass;
	
	@FindBy(id="confirmPass")
	public WebElement confirmPass;
	
	@FindBy(xpath="//*[@type=\"submit\"]")
	public WebElement submit;
	
	@FindBy(xpath="//*[@id=\"updateCancel\"]/span")
	public WebElement cancel;
	
	@FindBy(xpath="//*[@ng-model=\"skillToAdd\"]")
	public WebElement getSkills;
	
	@FindBy(xpath="//*[@class=\"changePassword _md md-transition-in\"]/div[2]/form/button")
	public WebElement addSkill;
	
	@FindBy(xpath="//*[@class=\"changePassword _md md-transition-in\"]/div[2]/button")
	public WebElement saveSkills;
	
	
	public SettingsWindow(WebDriver driver) {
		super(driver);
	}
	
	public void chooseSkill(String choice)  {
		getSkills.click();
		List<WebElement> skillList = driver.findElements(By.xpath("//*[@class=\"md-select-menu-container md-active md-clickable\"]/md-select-menu/md-content/md-option/div[1]"));
		for (WebElement skill:skillList) {
			if (skill.getText().equals(choice)) {
				skill.click();
			}
		}
	}
	
	public List<WebElement> getAddedSkills() {
		List<WebElement> addedSkillList = driver.findElements(By.xpath("//ul/li"));
		return addedSkillList;
	}
	
	public List<WebElement> getDeletionIcons() {
		List<WebElement> deletionIcons = driver.findElements(By.xpath("//ul/li/button/md-icon"));
		return deletionIcons;
	}
	
	
	

}
