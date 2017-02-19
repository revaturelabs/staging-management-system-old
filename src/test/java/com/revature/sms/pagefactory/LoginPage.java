package com.revature.sms.pagefactory;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.revature.sms.util.TestSetup;

public class LoginPage extends SMSPage {
	protected final static String expectedPath = "src/test/resources/PropertiesFiles/expected.properties";
	static Properties expected = TestSetup.getProperties(expectedPath);
	
	@FindBy(xpath="/html/head/title")
	public WebElement title;
	
	@FindBy(tagName="span")
	public WebElement header;
	
	@FindBy(xpath="//*[@ng-model=\"logCtrl.username\"]")
	public WebElement unField;
	
	@FindBy(xpath="//*[@ng-model=\"logCtrl.inputPass\"]")
	public WebElement pwField;
	
	@FindBy(xpath="/html/body/div[1]/div/ui-view[2]/div/md-card/form/div[2]/button/span")
	public WebElement submit;
	
	
	
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public void login(String username, String password) {
		unField.sendKeys(username);
		pwField.sendKeys(password);
		submit.click();
	}
	
	
}
