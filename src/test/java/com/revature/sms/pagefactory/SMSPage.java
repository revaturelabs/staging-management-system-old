package com.revature.sms.pagefactory;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//Defines a constructor and methods that should be implemented by all page objects in the testing framework
public abstract class SMSPage {
	protected WebDriver driver;
	
	@FindBy(tagName="title")
	public WebElement title;
	
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
		WebElement fieldValue;
		List<WebElement> fieldValues;
		boolean result = true;
		int i=0;
		while (i<fields.length) {
			try {
				//System.out.println(fields[i].getName());
				fieldValue = (WebElement) fields[i].get(this);
				result = verifyField(fieldValue);
				//System.out.println(result);
				if (!result) {
					return result;
				}
			} catch (ClassCastException e) {
				try {
					fieldValues = (List<WebElement>) fields[i].get(this);
					for (WebElement f:fieldValues) {
						result = verifyField(f);
						if (!result) {
							return result;
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					Logger.getRootLogger().debug(e1);
				}
				Logger.getRootLogger().debug(e);
			} catch (IllegalArgumentException | IllegalAccessException e) {
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
			//System.out.println(fieldValue.toString());
			Logger.getRootLogger().debug(e);
			return false;
		}
		return true;
	}
		
	
	public String getToastMessage() {
		try {
			WebElement toast = driver.findElement(By.tagName("md-toast"));
			String text = toast.getText();
			text = text.trim();
			String[] splitText = text.split("\n");
			//System.out.println(splitText[0]);
			//if (splitText[0].equals("Batch addition cancelled.")) {
			//	System.out.println("WHY?");
			//}
			return splitText[0];
			
		} catch (NoSuchElementException e) {
			System.out.println("EXCEPTION");
			return null;
		}
	}
	
}
