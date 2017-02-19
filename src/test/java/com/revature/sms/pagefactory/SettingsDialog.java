package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SettingsDialog extends SMSPage {
	
	//The xpath for this header is different than for most of the other dialogs
	@FindBy(xpath="//md-dialog/md-toolbar/div/h2")
	public WebElement header;
	
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
	
	
	public SettingsDialog(WebDriver driver) {
		super(driver);
	}
	
	
	/*
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
	*/
	
	

}
