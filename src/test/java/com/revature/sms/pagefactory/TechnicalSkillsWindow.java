package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TechnicalSkillsWindow extends SMSPage{

	
	@FindBy(xpath="//*[@aria-label=\"New skill\"]")
	public WebElement newSkill;
	
	@FindBy(xpath="//*[@type=\"submit\"]")
	public WebElement submit;
	
	@FindBy(xpath="//*[@type=\"button\"]")
	public WebElement cancel;
	
	public TechnicalSkillsWindow(WebDriver driver) {
		super(driver);
	}

}
