package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ScheduleCertificationWindow extends SMSPage {
	
	@FindBy(xpath="//*[@class=\"md-datepicker-input md-input\"]")
	public WebElement enterDate;
	
	@FindBy(xpath="//*[@id=\"note\"]")
	public WebElement enterNote;
	
	@FindBy(xpath="//div/form/div[4]/button[1]/span")
	public WebElement submit;
	
	@FindBy(xpath="//div/form/div[4]/button[2]/span")
	public WebElement cancel;
	
	public ScheduleCertificationWindow(WebDriver driver) {
		super(driver);
	}

}
