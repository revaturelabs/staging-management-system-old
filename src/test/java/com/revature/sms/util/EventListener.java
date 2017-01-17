package com.revature.sms.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.*;

//Activates thread sleeping after certain WebDriver methods so that tests are easier to follow by humans
public class EventListener extends AbstractWebDriverEventListener {

	
	@Override
	public void afterNavigateBack(WebDriver driver) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

