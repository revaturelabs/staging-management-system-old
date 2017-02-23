package com.revature.sms.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.*;

//Activates thread sleeping after certain WebDriver methods. This class can be utilized to make tests easier to 
//follow by humans or to give the browser extra time to catch up in specific types of situations.
public class EventListener extends AbstractWebDriverEventListener {

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		Utils.attemptWait(500);
	}
	
	
	@Override
	public void afterNavigateBack(WebDriver driver) {
		Utils.attemptWait(500);
	}
	
	
	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		Utils.attemptWait(500);
	}
	
	
	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		Utils.attemptWait(1000);
	}
	
	
}

