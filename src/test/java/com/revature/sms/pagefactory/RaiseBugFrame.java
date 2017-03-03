package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

//This class should be used after clicking the "Found a bug?" button on the bottom right of a home page.
public class RaiseBugFrame extends SMSPage {
	
	@FindBy(xpath="//*[@id=\"jic-collector-form\"]/div[1]/div[1]")
	public WebElement windowHeader;
	
	@FindBy(xpath="//*[@id=\"jic-collector-form\"]/div[1]/div[1]/p")
	public WebElement instructions;
	
	@FindBy(xpath="//*[@id=\"desc-group\"]/label")
	public WebElement messageBoxLabel;
	
	@FindBy(id="description")
	public WebElement messageBox;
	
	@FindBy(xpath="//*[@id=\"jic-collector-form\"]/div[1]/fieldset[2]/legend/span")
	public WebElement attachFileLabel;
	
	@FindBy(id="screenshot")
	public WebElement attachFileButton;
	
	@FindBy(id="screenshot")
	public WebElement chooseFile;
	
	@FindBy(xpath="//*[@id=\"name-group\"]/label")
	public WebElement nameLabel;
	
	@FindBy(id="fullname")
	public WebElement enterName;
	
	@FindBy(id="email")
	public WebElement enterEmail;
	
	@FindBy(id="recordWebInfoConsent")
	public WebElement webInfo;
	
	@FindBy(xpath="//*[@id=\"jic-collector-form\"]/div[2]/input")
	public WebElement submit;
	
	@FindBy(xpath="//*[@class=\"cancel\"]")
	public WebElement cancel;
	
	public RaiseBugFrame(WebDriver driver) {
		super(driver);
	}

}
