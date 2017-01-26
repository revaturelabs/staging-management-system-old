package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateBatchWindow extends SMSPage {
	
	@FindBy(id="firstName")
	public WebElement firstName;
	
	@FindBy(id="input_4")
	public WebElement lastName;
	
	@FindBy(id="select_5")
	public WebElement curriculum;
	
	@FindBy(id="input_8")
	public WebElement enterDate;
	
	@FindBy(xpath="//div/div[2]/form/div/button")
	public WebElement addToList;
	
	@FindBy(xpath="//div/div[2]/div/form/button[1]")
	public WebElement submit;
	
	@FindBy(xpath="//div/div[2]/div/form/button[2]")
	public WebElement cancel;
	
	
	public CreateBatchWindow(WebDriver driver) {
		super(driver);
	}

	
	
	
}
