package com.revature.sms;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.sms.util.InstanceTestClassListener;
import com.revature.sms.util.SpringInstanceTestClassRunner;
import com.revature.sms.util.TestSetup;
import com.revature.sms.util.Utils;
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
import com.revature.sms.pagefactory.AvailableSkillsWindow;
import com.revature.sms.util.EventListener;

//This test class can't be directly run, but it provides set up and tear down for various other tests that are
//all prepared in similar ways using inheritance. 
@Transactional
@Service
@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest
public abstract class AbstractT implements InstanceTestClassListener {
	protected final static String browser = "Chrome";
	protected final static String inputsPath = "src/test/resources/PropertiesFiles/inputs.properties";
	protected final static String expectedPath = "src/test/resources/PropertiesFiles/expected.properties";

	//Allow properties files, webdrivers, and page objects to be used in the tests
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
	protected AvailableSkillsWindow tsw;
	protected SettingsWindow sw;
	protected RaiseBugWindow rbw;

	protected HomePage hp;
	protected String un;
	protected String pw;
	
	
	@Autowired
	UserRepo ur;

	@Override
	public void beforeClassSetup() {
		//Pick your browser
		if ("Chrome".equals(browser)) {
			webDriver = TestSetup.getChrome();
		}
		if ("Internet Explorer".equals(browser)) {
			webDriver = TestSetup.getIE();
		}
		if ("Firefox".equals(browser)) {
			webDriver = new FirefoxDriver();
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

	@Before
	public void before() {
		//Initialize page objects
		lp = new LoginPage(driver);
		asp = new AssociatePage(driver);
		adp = new AdminPage(driver);
		sap = new SuperAdminPage(driver);
		scw = new ScheduleCertificationWindow(driver);
		cbw = new CreateBatchWindow(driver);
		tsw = new AvailableSkillsWindow(driver);
		sw = new SettingsWindow(driver);
		rbw = new RaiseBugWindow(driver);
		
		//The home page and username variables are different depending on which test class is being used.
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
	
	//Logs out if the test is still on the home page when it ends
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
	
	
	//Below are convenience methods that are used by similar tests on different pages:
	
	//Allows a single test class to log in multiple times, each time as a different user
	public void loginHeaderLogoutTemplate(String username, String password, String ev) {
		lp.login(username, password);
		Assert.assertEquals(expected.getProperty("loginSuccess"), hp.getToastMessage());
		Assert.assertTrue(hp.verify());
		Assert.assertEquals(ev, hp.header.getText());
		hp.logout.click();
		Assert.assertEquals(expected.getProperty("logoutSuccess"), lp.getToastMessage());
		Assert.assertTrue(lp.verify());
	}
	
	public void cancelCommonButtons() {
		hp.settings.click();
		sw.verify();
		sw.cancel.click();
		hp.reportBug.click();
		driver.switchTo().frame("atlwdg-frame");
		rbw.verify();
		rbw.cancel.click();
	}
	
	public String getThisMondayFromHeader() {
		String monday = hp.attendanceHeaders.findElement(By.tagName("p")).getText();
		if (monday.contains("/0")) {
			monday = monday.replace("0", "");
		}
		return monday;
	}
	
		
	
}