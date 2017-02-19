package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ScheduleCertificationDialog extends SMSPage {
	
	@FindBy(xpath="//md-dialog/div/md-toolbar/div")
	public WebElement header;
	
	@FindBy(xpath="//*[@class=\"md-datepicker-input md-input\"]")
	public WebElement enterDate;
	
	@FindBy(xpath="//*[@id=\"note\"]")
	public WebElement enterNote;
	
	@FindBy(xpath="//div/form/div[4]/button[1]")
	public WebElement submit;
	
	@FindBy(xpath="//div/form/div[4]/button[2]")
	public WebElement cancel;
	
	public ScheduleCertificationDialog(WebDriver driver) {
		super(driver);
	}

}
