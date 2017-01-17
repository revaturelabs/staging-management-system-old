package com.revature.sms.testlibs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * WebElementStaticProvider is a class used to look up WebElements.
 * It is important that WebElements are not hard-coded in test cases, due to the fact that
 * WebElements and page design are frequently changed during development.
 * @author Sage
 *
 */

public class WebElementStaticProvider {

	
	/**
	 * Finds the input box for typing usernames.
	 * @param driver
	 * @return the input box WebElement
	 */
	public static WebElement getLoginUsernameInput(WebDriver driver){
		return driver.findElement(By.xpath("//input[contains(@ng-model, 'username')]"));
	}
	
	/**
	 * Finds the input box for typing passwords.
	 * @param driver
	 * @return the input box WebElement
	 */
	public static WebElement getLoginPasswordInput(WebDriver driver){
		return driver.findElement(By.xpath("//input[contains(@ng-model, 'logCtrl.inputPass')]"));
	}
	
	/**
	 * Finds the button to login.
	 * @param driver
	 * @return The login button
	 */
	
	public static WebElement getLoginSubmit(WebDriver driver){
		return driver.findElement(By.xpath("//span[contains(text(),'Login')]//parent::button"));
	}
	
	/**
	 * 
	 * Finds the button to log out
	 * @param driver
	 * @return the logout button, or null if the logout button doesn't exist.
	 * 
	 */
	
	public static WebElement getLogoutButton(WebDriver driver){
		if(driver.findElements(By.xpath("//button[contains(@ng-click, 'logout')]")).size()>0){
			return driver.findElement(By.xpath("//button[contains(@ng-click, 'logout')]"));
		}
		else{
			return null;
		}
	}
	
	
	
	
}
