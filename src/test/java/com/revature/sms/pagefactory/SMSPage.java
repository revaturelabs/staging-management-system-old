package com.revature.sms.pagefactory;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

//Defines a constructor and methods that should be implemented by all page objects in the testing framework
public abstract class SMSPage {
	protected WebDriver driver;
	
	@FindBy(tagName="title")
	public WebElement title;
	
	@FindBy(css="[class=\"ng-binding flex\"]")
	public WebElement header;
	
	@FindBy(xpath="//*[@aria-label=\"Account settings\"]")
	public WebElement settings;
	
	@FindBy(xpath="//*[@aria-label=\"Logout\"]")
	public WebElement logout;
	
	public SMSPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//Verifies that the web driver is currently on the page specified by this page object
	@SuppressWarnings("unchecked")
	public boolean verify() {
		Class<? extends SMSPage> thisClass = this.getClass();
		Field[] fields = thisClass.getDeclaredFields();
		//System.out.println(thisClass.getName());
		WebElement fieldValue = null;
		List<WebElement> fieldValues = null;
		boolean result = true;
		int i=0;
		while (i<fields.length && result) {
			try {	
				try {
					//System.out.println(fields[i].getName());
					fieldValue = (WebElement) fields[i].get(this);
					if (verifyField(fieldValue) == false) {
						result = false;
					}
				} catch (ClassCastException e) {
					fieldValues = (List<WebElement>) fields[i].get(this);
					for (WebElement f:fieldValues) {
						if (verifyField(f) == false) {
							result = false;
						}
					}	
				}
			} catch (IllegalAccessException e) {
				Logger.getRootLogger().debug(e);
			}
			i++;
		}
		return result;
	}
	
	private boolean verifyField(WebElement fieldValue) {
		try {
			fieldValue.isDisplayed();
		} catch (NoSuchElementException e) {
			Logger.getRootLogger().debug(e);
			return false;
		}
		return true;
	}
		
	public void carefulClick(String fieldName) {
		Class<? extends SMSPage> thisClass = this.getClass();
		Field field = null;
		WebElement fieldValue = null;
		try {
			field = thisClass.getField(fieldName);
			fieldValue = (WebElement) field.get(this);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			//System.out.println("Why would I get one of these exceptions?");
			Logger.getRootLogger().debug(e);
		}
		
		try {
			try {
				fieldValue.click();
			} catch (WebDriverException e) {
				Logger.getRootLogger().debug(e);
				Thread.sleep(500);
				fieldValue.click();
			}
		} catch (InterruptedException | NullPointerException e1) {
			e1.printStackTrace();
			Thread.currentThread().interrupt();
		} 
	}
	
	public String getHeader() {
		return header.getText();
	}
	
	
	
	public Select makeSelection(String fieldName, String selection) {
		Class<? extends SMSPage> thisClass = null;
		thisClass = this.getClass();
		Select select = null;
		try {
			Field field = thisClass.getDeclaredField(fieldName);
			WebElement fieldValue = (WebElement) field.get(this);
			select = new Select(fieldValue);
			select.selectByVisibleText(selection);
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
			Logger.getRootLogger().debug(e);
		}
		return select;
	}
	
	
}
