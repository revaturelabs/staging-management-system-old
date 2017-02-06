package com.revature.sms.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.*;

//Activates thread sleeping after certain WebDriver methods so that tests are easier to follow by humans
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

