package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProjectStatusWindow extends SMSPage {

	@FindBy(xpath="//*[@layout-align=\"center space-around\"]")
	public WebElement currentProject;
	
	@FindBy(xpath="//*[@aria-label=\"Select a new project.\"]")
	public WebElement selectProject;
	
	@FindBy(xpath="//*[@aria-label=\"Update Project ...\"]/div[2]/div[1]/h3")
	public WebElement projectName;
	
	@FindBy(xpath="//*[@aria-label=\"Update Project ...\"]/div[2]/div[1]/p")
	public WebElement projectTimeframe;
	
	@FindBy(xpath="//*[@aria-label=\"Update Project ...\"]/div[2]/div[2]/p")
	public WebElement projectDescription;
	
	@FindBy(id="assignSubmit")
	public WebElement submit;
	
	@FindBy(id="assignCancel")
	public WebElement cancel;
	
	
	public ProjectStatusWindow(WebDriver driver) {
		super(driver);
	}

}
