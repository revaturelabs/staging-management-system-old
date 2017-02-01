package com.revature.sms;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.sms.util.InstanceTestClassListener;
import com.revature.sms.util.SpringInstanceTestClassRunner;
import com.revature.sms.util.TestSetup;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.pagefactory.AdminPage;
import com.revature.sms.pagefactory.AssociatePage;
import com.revature.sms.pagefactory.ChangePasswordWindow;
import com.revature.sms.pagefactory.CreateBatchWindow;
import com.revature.sms.pagefactory.HomePage;
import com.revature.sms.pagefactory.LoginPage;
import com.revature.sms.pagefactory.RaiseBugWindow;
import com.revature.sms.pagefactory.ScheduleCertificationWindow;
import com.revature.sms.pagefactory.SuperAdminPage;
import com.revature.sms.util.EventListener;

@Transactional
@Service
@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest
public abstract class AbstractT implements InstanceTestClassListener {
	protected final static String browser = "Chrome";
	protected final static String inputsPath = "src/test/resources/PropertiesFiles/inputs.properties";
	protected final static String expectedPath = "src/test/resources/PropertiesFiles/expected.properties";

	// Allow properties files, webdrivers, and page objects to be used in the
	// tests
	static Properties inputs;
	static Properties expected;
	static WebDriver webDriver;
	static EventFiringWebDriver driver;
	static EventListener eventListener;
	protected LoginPage lp;
	protected AssociatePage asp;
	protected AdminPage adp;
	protected SuperAdminPage sap;
	protected ScheduleCertificationWindow scw;
	protected CreateBatchWindow cbw;
	protected ChangePasswordWindow cpw;
	protected RaiseBugWindow rbw;

	@Autowired
	UserRepo ur;

	@Override
	public void beforeClassSetup() {
		if ("Chrome".equals(browser)) {
			webDriver = TestSetup.getChrome();
		}
		if ("Internet Explorer".equals(browser)) {
			webDriver = TestSetup.getIE();
		}
		if ("Firefox".equals(browser)) {
			webDriver = new FirefoxDriver();
		}

		// Allows the driver to take advantage of an event listener
		driver = new EventFiringWebDriver(webDriver);
		eventListener = new EventListener();
		driver.register(eventListener);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Initialize properties files
		inputs = TestSetup.getProperties(inputsPath);
		expected = TestSetup.getProperties(expectedPath);

	}

	// More browser preparation
	@Before
	public void before() {
		driver.get(inputs.getProperty("url"));
		lp = new LoginPage(driver);
		asp = new AssociatePage(driver);
		adp = new AdminPage(driver);
		sap = new SuperAdminPage(driver);
		scw = new ScheduleCertificationWindow(driver);
		cbw = new CreateBatchWindow(driver);
		cpw = new ChangePasswordWindow(driver);
		rbw = new RaiseBugWindow(driver);
		// Make sure the login page is loaded correctly
		Assert.assertEquals(expected.getProperty("siteName"), driver.getTitle());
		Assert.assertTrue(lp.verify());
		Assert.assertEquals(expected.getProperty("loginPg"), lp.header.getText());
	}

	@Override
	public void afterClassSetup() {
		driver.close();
	}

	public void LoginHeaderLogoutTemplate(HomePage hp, String un, String pw, String ev) {
		lp.login(un, pw);
		Assert.assertTrue(hp.verify());
		Assert.assertEquals(ev, hp.header.getText());
		hp.carefulClick("logout");
		Assert.assertTrue(lp.verify());
	}

	public void PasswordChangeTemplate(HomePage hp, String un, String pw, String pw2) {
		lp.login(un, pw);
		hp.carefulClick("settings");
		Assert.assertTrue(cpw.verify());
		cpw.oldPass.sendKeys(pw);
		cpw.newPass.sendKeys(pw2);
		cpw.confirmPass.sendKeys(pw2);
		cpw.carefulClick("submit");
		hp.carefulClick("logout");

		lp.login(un, pw2);
		hp.carefulClick("settings");
		Assert.assertTrue(cpw.verify());
		cpw.oldPass.sendKeys(pw2);
		cpw.newPass.sendKeys(pw);
		cpw.confirmPass.sendKeys(pw);
		cpw.carefulClick("submit");
		hp.carefulClick("logout");
	}

	public void adminAttendenceViewTemplate(String un, String pw) {
		lp.login(un, pw);
		List<WebElement> allRows = adp.attendanceTable.findElements(By.tagName("tr"));
		int count = 0;
		for (WebElement row : allRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for (WebElement cell : cells) {
				if (count % 6 == 0) {
					cell.click();
					Assert.assertTrue(adp.verifyAssoc.isDisplayed());
					adp.carefulClick("closeIcon");
					
				} else {
					cell.click();
					cell.click();
				}
				count++;
			}
		}
	}

	public void adminCalenderNavigation(String un, String pw) {
		lp.login(un, pw);
		adp.carefulClick("prevWeekTop");
		adp.carefulClick("nextWeekTop");
		adp.carefulClick("prevWeekBottom");
		adp.carefulClick("nextWeekBottom");
	}
}