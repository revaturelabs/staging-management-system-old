package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateBatchWindow extends SMSPage {
	
	@FindBy(id="firstName")
	public WebElement firstName;
	
	@FindBy(xpath="//*[@ng-model=\"bACtrl.lastName\"]")
	public WebElement lastName;
	
	@FindBy(xpath="//*[@ng-model=\"bACtrl.selectedBatchType\"]")
	public WebElement curriculum;
	
	@FindBy(xpath="//*[@class=\"md-datepicker-input md-input\"]")
	public WebElement enterDate;
	
	@FindBy(xpath="//div/div[2]/form/div/button")
	public WebElement addToList;
	
	@FindBy(xpath="//div/div[2]/div/form/div[2]/button[1]")
	public WebElement submit;
	
	@FindBy(xpath="//div/div[2]/div/form/div[2]/button[2]")
	public WebElement cancel;
	
	
	public CreateBatchWindow(WebDriver driver) {
		super(driver);
	}

	
	
	
}
