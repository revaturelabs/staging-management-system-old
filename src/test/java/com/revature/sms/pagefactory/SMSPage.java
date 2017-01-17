package com.revature.sms.pagefactory;

import java.lang.reflect.Field;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

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
		System.out.println("Class: "+thisClass.getName());
		Field[] fields = thisClass.getDeclaredFields();
		int i=0;
		while (i<fields.length) {
			WebElement fieldValue = null;
			try {
				fieldValue = (WebElement) fields[i].get(this);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			try {
				System.out.println("Field Name: "+fields[i].getName());
				System.out.println("Tag Name: "+fieldValue.getTagName());
				System.out.println();
				fieldValue.isDisplayed();
			} catch (NoSuchElementException e) {
				System.out.println(e.getMessage());
				return false;
			}
			i++;
		}
		
		return true;
	}
	
}
