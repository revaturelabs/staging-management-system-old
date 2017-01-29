package com.revature.sms.pagefactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

//Defines a constructor and methods that should be implemented by all page objects in the testing framework
public abstract class SMSPage {

	protected WebDriver driver;
	
	public SMSPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//Verifies that the web driver is currently on the page specified by this page object
	@SuppressWarnings("unchecked")
	public boolean verify() {
		Class<? extends SMSPage> thisClass = this.getClass();
		Field[] fields = thisClass.getDeclaredFields();
		WebElement fieldValue = null;
		List<WebElement> fieldValues = null;
		boolean result = true;
		int i=0;
		while (i<fields.length && result) {
			try {	
				try {
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
				e.printStackTrace();
			}
			i++;
		}
		return result;
	}
	
	private boolean verifyField(WebElement fieldValue) {
		try {
			fieldValue.isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
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
			System.out.println("Why would I get one of these exceptions?");
			e.printStackTrace();
		}
		
		try {
			try {
				fieldValue.click();
			} catch (WebDriverException e) {
				System.out.println(e.getMessage());
				Thread.sleep(500);
				fieldValue.click();
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} 
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
			e.printStackTrace();
		}
		return select;
	}
	
	
}
