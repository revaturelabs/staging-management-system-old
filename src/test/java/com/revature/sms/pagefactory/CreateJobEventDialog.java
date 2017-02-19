package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


//Where did this dialog go? I can't find it anymore.
public class CreateJobEventDialog extends SMSPage {

	@FindBy(xpath="//md-dialog/div/md-toolbar/div")
	public WebElement header;
	
	@FindBy(xpath="//*[@ng-model=\"jACtrl.companyName\"]")
	public WebElement companyName;
	
	@FindBy(xpath="//*[@ng-model=\"jACtrl.companylocation\"]")
	public WebElement companyLocation;
	
	@FindBy(xpath="//*[@ng-model=\"jACtrl.jobTitle\"]")
	public WebElement jobTitle;
	
	@FindBy(xpath="//*[@ng-model=\"jACtrl.eventLocation\"]")
	public WebElement eventLocation;
	
	@FindBy(xpath="//*[@ng-model=\"jACtrl.selectedEventType\"]")
	public WebElement eventType;
	
	@FindBy(className="md-datepicker-input md-input")
	public WebElement eventDate;
	
	@FindBy(xpath="//*[@ng-model=\"jACtrl.note\"]")
	public WebElement eventNote;
	
	@FindBy(xpath="//*[@aria-label=\"Submit All\"]")
	public WebElement submit;
	
	@FindBy(xpath="//*[@aria-label=\"Cancel\"]")
	public WebElement cancel;
	
	
	
	public CreateJobEventDialog(WebDriver driver) {
		super(driver);
	}

}
