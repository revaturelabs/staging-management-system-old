package com.revature.sms;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
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
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.pagefactory.AdminPage;
import com.revature.sms.pagefactory.AssociatePage;
import com.revature.sms.pagefactory.SettingsDialog;
import com.revature.sms.pagefactory.CreateBatchDialog;
import com.revature.sms.pagefactory.HomePage;
import com.revature.sms.pagefactory.LoginPage;
import com.revature.sms.pagefactory.RaiseBugFrame;
import com.revature.sms.pagefactory.ScheduleCertificationDialog;
import com.revature.sms.pagefactory.SuperAdminPage;
import com.revature.sms.pagefactory.AvailableSkillsDialog;
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
	protected ScheduleCertificationDialog scw;
	protected CreateBatchDialog cbw;
	protected AvailableSkillsDialog asw;
	protected SettingsDialog sw;
	protected RaiseBugFrame rbw;

	protected HomePage hp;
	protected String un;
	protected String pw;
	protected List<User> users;
	
	
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
		scw = new ScheduleCertificationDialog(driver);
		cbw = new CreateBatchDialog(driver);
		asw = new AvailableSkillsDialog(driver);
		sw = new SettingsDialog(driver);
		rbw = new RaiseBugFrame(driver);
		
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
		users = ur.findAll();
		
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
		Assert.assertTrue(sw.verify());
		sw.cancel.click();
		hp.reportBug.click();
		driver.switchTo().frame("atlwdg-frame");
		Assert.assertTrue(rbw.verify());
		rbw.cancel.click();
	}
	
	
	public void compareHashes(HashMap<String, String> expectedInfo, HashMap<String, String> actualInfo) {
		Set<String> keys = expectedInfo.keySet();
		Iterator<String> itr = keys.iterator();
		//By using the same key to get info from both Hashes, we know they're both referring to 
		//the same event
		while (itr.hasNext()) {
			String key = itr.next();
			System.out.println(key);
			//This if statement is here because task notes are not displayed in any way on the web
			//page for panels
			if (!("taskNote".equals(key) && "Panel".equals(expectedInfo.get("taskType")))) {
				Assert.assertEquals(expectedInfo.get(key), actualInfo.get(key));
			}
		}
	}
	
	
	public void compareAttendanceStatuses(ArrayList<MonthDay> monthDays, HashMap<MonthDay, String> expectedStatuses, 
										  HashMap<MonthDay, String> actualStatuses) {
		//This for-each loop iterates through both HashMaps using every matching monthDay key, and asserts
		//that the values are equal.
		for (MonthDay md : monthDays) {
			String es = expectedStatuses.get(md);
			String as = actualStatuses.get(md);
			//This if statement is for when there is no attendance data for the current date in the database
			if (es == null) {
				if (md.compareTo(MonthDay.now()) < 0) {
					es = "close";
				} else {
					es = "";
				}
			}
			Assert.assertEquals(es, as);
		}
	}
		
	
	public void revealCollapsedPanelsForUser(String username) {
		WebElement row = adp.getRowByIdentifier(adp.attendanceBody, username, "td[1]/div/div[2]/p");
		Assert.assertTrue(row!=null);
		WebElement fullNameCell = row.findElement(By.xpath("td[1]"));
		fullNameCell.click();
		Assert.assertTrue("false".equals(adp.panelDiv.getAttribute("aria-hidden")));
		Assert.assertTrue("resizeAttendanceDiv".equals(adp.attendanceDiv.getAttribute("class")));
	}
	
}