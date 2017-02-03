package com.revature.sms;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
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
import com.revature.sms.pagefactory.SettingsWindow;
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
	protected SettingsWindow sw;
	protected RaiseBugWindow rbw;

	protected HomePage hp;
	protected String un;
	protected String pw;
	
	
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

	@Before
	public void before() {
		lp = new LoginPage(driver);
		asp = new AssociatePage(driver);
		adp = new AdminPage(driver);
		sap = new SuperAdminPage(driver);
		scw = new ScheduleCertificationWindow(driver);
		cbw = new CreateBatchWindow(driver);
		sw = new SettingsWindow(driver);
		rbw = new RaiseBugWindow(driver);
		
		Class<? extends AbstractT> currentClass = this.getClass();
		String className = currentClass.getName();
		if (className.contains(".AssociateT")) {
			hp = asp;
			un = inputs.getProperty("javaUN");
		} else if (className.contains(".AdminT")) {
			hp = adp;
			un = inputs.getProperty("adminUN");
		} else {
			hp = sap;
			un = inputs.getProperty("superAdminUN");
		}
		pw = inputs.getProperty("PW");
		
		driver.get(inputs.getProperty("url"));
		// Make sure the login page is loaded correctly
		Assert.assertEquals(expected.getProperty("siteName"), driver.getTitle());
		Assert.assertTrue(lp.verify());
		Assert.assertEquals(expected.getProperty("loginPg"), lp.header.getText());
	}

	
	@Test
	public void testPasswordChange() {
		String pw2 = inputs.getProperty("PW2");
		
		lp.login(un, pw);
		hp.settings.click();
		Assert.assertTrue(sw.verify());
		sw.oldPass.sendKeys(pw);
		sw.newPass.sendKeys(pw2);
		sw.confirmPass.sendKeys(pw2);
		sw.submit.click();
		Assert.assertEquals(expected.getProperty("successfulPasswordSubmission"), hp.getToastMessage());
		hp.logout.click();

		lp.login(un, pw2);
		hp.settings.click();
		Assert.assertTrue(sw.verify());
		sw.oldPass.sendKeys(pw2);
		sw.newPass.sendKeys(pw);
		sw.confirmPass.sendKeys(pw);
		sw.submit.click();
		Assert.assertEquals(expected.getProperty("successfulPasswordSubmission"), hp.getToastMessage());
		hp.logout.click();
	}
	
	
	@Test
	public void testBugReport() {
		lp.login(un, pw);
		hp.reportBug.click();
		driver.switchTo().frame("atlwdg-frame");

		Assert.assertTrue(rbw.verify());
		rbw.messageBox.sendKeys(inputs.getProperty("bugReport"));
		rbw.enterName.sendKeys(inputs.getProperty("bugReportName"));
		rbw.enterEmail.sendKeys(inputs.getProperty("bugReportEmail"));
		rbw.webInfo.click();
		rbw.cancel.click();
		Assert.assertTrue(hp.verify());
	}
	
	@Test
	public void testLoginRejection() {
		WebElement submission = driver.findElement(By.xpath("/html/body/div[1]/div/ui-view[2]/div/md-card/form"));
		Assert.assertFalse(submission.getAttribute("class").contains("ng-submitted"));
		lp.submit.click();
		Assert.assertTrue(submission.getAttribute("class").contains("ng-submitted"));
		
		String fakeUN = inputs.getProperty("fakeUN");
		String fakePW = inputs.getProperty("fakePW");
		String usernameFail = expected.getProperty("usernameFail");
		String passwordFail = expected.getProperty("passwordFail");
		
		
		lp.login(fakeUN, fakePW);
		String message = lp.getToastMessage();
		Assert.assertEquals(usernameFail, message);
		lp.unField.clear();
		lp.pwField.clear();
		
		lp.login(fakeUN, pw);
		message = lp.getToastMessage();
		Assert.assertEquals(usernameFail, message);
		lp.unField.clear();
		lp.pwField.clear();
		
		lp.login(un, fakePW);
		message = lp.getToastMessage();
		Assert.assertEquals(passwordFail, message);
		lp.unField.clear();
		lp.pwField.clear();	
	}
	
	
	public void LoginHeaderLogoutTemplate(String username, String password, String ev) {
		lp.login(un, pw);
		Assert.assertTrue(hp.verify());
		Assert.assertEquals(expected.getProperty("loginSuccess"), hp.getToastMessage());
		Assert.assertEquals(ev, hp.header.getText());
		hp.logout.click();
		Assert.assertTrue(lp.verify());
	}
	
	
	@After
	public void after() {
		if (hp.verify()) {
			hp.logout.click();
			Assert.assertEquals(expected.getProperty("logoutSuccess"), lp.getToastMessage());
		}
	}
	
	@Override
	public void afterClassSetup() {
		driver.close();
	}
	
}