
package com.revature.sms;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.revature.sms.util.InstanceTestClassListener;
import com.revature.sms.util.SpringInstanceTestClassRunner;
import com.revature.sms.util.TestSetup;
import com.revature.sms.pagefactory.ChangePasswordWindow;
import com.revature.sms.pagefactory.CreateBatchWindow;
import com.revature.sms.pagefactory.LoginPage;
import com.revature.sms.pagefactory.SuperAdminPage;
import com.revature.sms.util.EventListener;

@Service
@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest
public class SuperAdminT implements InstanceTestClassListener {
	private final String browser = "Chrome"; 
	private final String inputsPath = "src/test/resources/PropertiesFiles/inputs.properties";
	private final String expectedPath = "src/test/resources/PropertiesFiles/expected.properties";
	
	//Allow properties files, webdrivers, and page objects to be used in the tests
	static Properties inputs;
	static Properties expected;
	static WebDriver webDriver;
	static EventFiringWebDriver driver;
	static EventListener eventListener; 
	private LoginPage lp;
	private SuperAdminPage sap;
	private CreateBatchWindow cbw;
	private ChangePasswordWindow cpw;
	
	
	@Override
	public void beforeClassSetup() {
	    if (browser.equals("Chrome")) {
	    	webDriver = TestSetup.getChrome();
	    }
	    if (browser.equals("Internet Explorer")) {
	    	webDriver = TestSetup.getIE();
	    }
	    	
		//Allows the driver to take advantage of an event listener
		driver = new EventFiringWebDriver(webDriver);
		eventListener = new EventListener();
		driver.register(eventListener);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		//Initialize properties files
		inputs = TestSetup.getProperties(inputsPath);
		expected = TestSetup.getProperties(expectedPath);
	}
	
	//More browser preparation
	@Before
	public void before() {
		driver.get(inputs.getProperty("url"));
		lp = new LoginPage(driver);
		sap = new SuperAdminPage(driver);
		cbw = new CreateBatchWindow(driver);
		cpw = new ChangePasswordWindow(driver);
		
		//Make sure the login page is loaded correctly 
		Assert.assertEquals(expected.getProperty("siteName"), driver.getTitle());
		Assert.assertTrue(lp.verify());
		Assert.assertEquals(expected.getProperty("loginPg"), lp.header.getText());
	}
	
	
	//Tests that when different types of users login and logout, they are navigated to the correct pages
	@Ignore
	@Test
	public void testLoginHeaderLogout() {
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW"));
		Assert.assertTrue(sap.verify());
		Assert.assertEquals(expected.getProperty("superAdminPg"), sap.header.getText());
		sap.logout.click();
		Assert.assertTrue(lp.verify());
	}
	
	@Ignore
	@Test
	public void testSearchBar() {
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW"));
		Assert.assertTrue(sap.verify());
		Assert.assertEquals(expected.getProperty("adminPg"), sap.header.getText());
		
		sap.searchBox.sendKeys("Java");
		Assert.assertEquals(expected.getProperty("java"), sap.searchResult.getText());
		sap.searchBox.clear();
		
		sap.searchBox.sendKeys("DotNet");
		Assert.assertEquals(expected.getProperty("dotnet"), sap.searchResult.getText());
		sap.searchBox.clear();
		
		sap.searchBox.sendKeys("SDET");
		Assert.assertEquals(expected.getProperty("sdet"), sap.searchResult.getText());
		sap.searchBox.clear();
		
		sap.logout.click();
		Assert.assertTrue(lp.verify());
	}
	
	@Ignore
	@Test
	public void testPasswordChange() {
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW"));
		sap.settings.click();
		Assert.assertTrue(cpw.verify());
		cpw.oldPass.sendKeys(inputs.getProperty("superAdminPW"));
		cpw.newPass.sendKeys(inputs.getProperty("superAdminPW2"));
		cpw.confirmPass.sendKeys(inputs.getProperty("superAdminPW2"));
		cpw.submit.click();
		sap.logout.click();
		
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW2"));
		sap.settings.click();
		Assert.assertTrue(cpw.verify());
		cpw.oldPass.sendKeys(inputs.getProperty("superAdminPW2"));
		cpw.newPass.sendKeys(inputs.getProperty("superAdminPW"));
		cpw.confirmPass.sendKeys(inputs.getProperty("superAdminPW"));
		cpw.submit.click();
		sap.logout.click();
	}
	
	@Ignore
	@Test
	public void testCancelButtons() {
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW"));
		sap.addBatch.click();
		cbw.cancel.click();
		sap.settings.click();
		cpw.cancel.click();
		sap.logout.click();
	}
	
	
	@Test
	public void testBatchCreation() {
		lp.login(inputs.getProperty("superAdminUN"), inputs.getProperty("superAdminPW"));
		
		sap.addBatch.click();
		Assert.assertTrue(cbw.verify());
		cbw.firstName.sendKeys(inputs.getProperty("firstName"));
		cbw.lastName.sendKeys(inputs.getProperty("lastName"));
		cbw.addToList.click();
		cbw.firstName.sendKeys(inputs.getProperty("firstName2"));
		cbw.lastName.sendKeys(inputs.getProperty("lastName2"));
		cbw.addToList.click();
		
		cbw.curriculum.click();
		ArrayList<WebElement> elements = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class=\"md-text ng-binding\"]"));
		for (WebElement e:elements) {
			String text = e.getText();
			if (text.equals(inputs.getProperty("curriculum"))) {
				e.click();
			}
		}
		
		cbw.enterDate.clear();
		cbw.enterDate.sendKeys(inputs.getProperty("batchStartDate"));
		cbw.submit.click();
		driver.findElement(By.xpath("//*[@id=\"dialogContent_13\"]/div/div/button")).click();
		sap.logout.click();
	}
	
	
	@Override
	public void afterClassSetup() {
		driver.close();
	}
	
}
