package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

//This class should be used after you click the plus icon on the dashboard (which is only on the Super Admin page).
public class CreateBatchWindow extends SMSPage {
	
	@FindBy(xpath="//md-dialog/div/md-toolbar/div")
	public WebElement header;
	
	@FindBy(id="firstName")
	public WebElement firstName;
	
	@FindBy(xpath="//*[@ng-model=\"bACtrl.lastName\"]")
	public WebElement lastName;
	
	@FindBy(xpath="//div/div[2]/form/div/button")
	public WebElement addToList;
	
	@FindBy(xpath="//*[@ng-model=\"bACtrl.selectedBatchType\"]")
	public WebElement curriculum;
	
	@FindBy(xpath="//*[@ng-model=\"bACtrl.trainer\"]")
	public WebElement trainer;
	
	@FindBy(xpath="//*[@class=\"md-datepicker-input md-input\"]")
	public WebElement enterDate;
	
	@FindBy(xpath="//*[@aria-label=\"Submit All\"]")
	public WebElement submit;
	
	@FindBy(xpath="//*[@aria-label=\"Cancel\"]")
	public WebElement cancel;
	
	
	public CreateBatchWindow(WebDriver driver) {
		super(driver);
	}
	
}
