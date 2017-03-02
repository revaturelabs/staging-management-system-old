package com.revature.sms.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProjectStatusDialog extends SMSPage {

	//The xpath for this header is different than for most of the other dialogs
	@FindBy(xpath="//md-dialog/md-toolbar/div/div")
	public WebElement header;
	
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
	
	@FindBy(id="cancel")
	public WebElement cancel;
	
	
	public ProjectStatusDialog(WebDriver driver) {
		super(driver);
	}

}
