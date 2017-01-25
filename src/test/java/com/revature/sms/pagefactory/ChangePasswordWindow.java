package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChangePasswordWindow extends SMSPage {

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
	
	
	public ChangePasswordWindow(WebDriver driver) {
		super(driver);
	}

}
