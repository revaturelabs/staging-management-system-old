package com.revature.sms.pagefactory;

import java.lang.reflect.Field;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
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
	public boolean verify() {
		Class<? extends SMSPage> thisClass = null;
		thisClass = this.getClass();
		Field[] fields = thisClass.getDeclaredFields();
		WebElement fieldValue = null;
		int i=0;
		while (i<fields.length) {
			try {
				fieldValue = (WebElement) fields[i].get(this);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			try {
				fieldValue.isDisplayed();
			} catch (NoSuchElementException e) {
				System.out.println(e.getMessage());
				return false;
			}
			i++;
		}
		
		return true;
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
