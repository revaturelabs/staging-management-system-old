package com.revature.sms.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.*;

//Activates thread sleeping after certain WebDriver methods so that tests are easier to follow by humans
public class EventListener extends AbstractWebDriverEventListener {

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		try {
			Thread.sleep(750);
		} catch (InterruptedException e) {
			Logger.getRootLogger().debug(e);
			Thread.currentThread().interrupt();
		}
	}
	
	
	@Override
	public void afterNavigateBack(WebDriver driver) {
		try {
			Thread.sleep(750);
		} catch (InterruptedException e) {
			Logger.getRootLogger().debug(e);
			Thread.currentThread().interrupt();
		}
	}
	
	
	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		try {
			Thread.sleep(750);
		} catch (InterruptedException e) {
			Logger.getRootLogger().debug(e);
			Thread.currentThread().interrupt();
		}
	}
	
	
	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			Logger.getRootLogger().debug(e);
			Thread.currentThread().interrupt();
		}
	}
	
	
}

