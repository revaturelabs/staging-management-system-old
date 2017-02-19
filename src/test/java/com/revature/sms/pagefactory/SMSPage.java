package com.revature.sms.pagefactory;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.revature.sms.util.Utils;

//Defines a constructor and methods that should be implemented by all page objects in the SMS testing framework.
//NOTE: Page objects (especially windows) should only be used and verified when their WebElement fields are visible 
//and clickable in the browser. To retrieve WebElements that only appear in very specific situations, use an 
//additional method that belongs to the page object where the WebElement appears.
//
public abstract class SMSPage {
	protected WebDriver driver;
	
	@FindBy(tagName="title")
	public WebElement title;
	
	public SMSPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//Verifies that the web driver is currently on the page specified by this page object.
	@SuppressWarnings("unchecked")
	public boolean verify() {
		Class<? extends SMSPage> thisClass = this.getClass();  //Isn't the reflection API neat?
		Field[] fields = thisClass.getDeclaredFields();
		//System.out.println(thisClass.getName());
		WebElement fieldValue;
		List<WebElement> fieldValues;
		boolean result = true;
		int i=0;
		while (i<fields.length) {
			try {
				//When a field is a WebElement
				//System.out.println(fields[i].getName());
				fieldValue = (WebElement) fields[i].get(this);
				result = verifyField(fieldValue);
				//System.out.println(result);
				if (!result) {
					return result;
				}
			} catch (ClassCastException e) {
				try {
					//When a field is a list of WebElements
					fieldValues = (List<WebElement>) fields[i].get(this);
					//Each WebElement must be verified individually.
					for (WebElement f:fieldValues) {
						result = verifyField(f);
						if (!result) {
							return result;
						}
					}
				} catch (ClassCastException e1) { 
					//When a field is not related to WebElements
				} catch (IllegalArgumentException | IllegalAccessException e1) { //These exceptions should never happen
					Logger.getRootLogger().debug(e1);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.getRootLogger().debug(e);
			} 
			i++;
		}
		return result;
	}
	
	private boolean verifyField(WebElement fieldValue) {
		try {
			fieldValue.isDisplayed();  //Dummy method to test whether the WebElement exists
		} catch (NoSuchElementException e) {
			//System.out.println(fieldValue.toString());
			Logger.getRootLogger().debug(e);
			return false;
		}
		return true;
	}
		
	
	//This method should be able to return any toast message that appears on screen, given that all toast 
	//notifications are laid out in a similar way.
	public String getToastMessage() {
		try {
			Utils.attemptWait(500);
			WebElement toast = driver.findElement(By.tagName("md-toast"));
			String text = toast.getText();
			if (text.equals("")) {
				return null;
			}
			text = text.trim();
			String[] splitText = text.split("\n");
			
			try {
				//This method has the side effect of clicking the ok button on the toast notification window.
				//This could theoretically be undesirable at some point, but for now, it helps to prevent
				//Selenium from getting confused when there are multiple toast notifications on the web
				//page at the same time.
				WebElement ok = driver.findElement(By.xpath("//md-toast/div/button"));
				ok.click();
			} catch (WebDriverException e1) {}
			
			Utils.attemptWait(400);
			return splitText[0];
			
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
}
