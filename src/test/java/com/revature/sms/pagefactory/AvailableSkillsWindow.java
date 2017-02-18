package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AvailableSkillsWindow extends SMSPage{

	@FindBy(xpath="//md-dialog/div/md-toolbar/div")
	public WebElement header;
	
	@FindBy(xpath="//*[@aria-label=\"New skill\"]")
	public WebElement newSkill;
	
	@FindBy(xpath="//*[@name=\"updateAll\"]/button[1]")
	public WebElement submit;
	
	@FindBy(xpath="//*[@name=\"updateAll\"]/button[2]")
	public WebElement cancel;
	
	public AvailableSkillsWindow(WebDriver driver) {
		super(driver);
	}

}
