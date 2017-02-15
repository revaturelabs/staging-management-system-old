package com.revature.sms;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.revature.sms.domain.User;
import com.revature.sms.util.Utils;

public class AdminTD extends AbstractT {

	@Test
	public void testAdminAttendanceView() {
		lp.login(un, pw);
		Assert.assertTrue(adp.verify());
		
		List<User> users = ur.findAll();
		while (!expected.getProperty("tooFarBack").equals(adp.getToastMessage())) {
			for (User user:users) {
				if ("associate".equals(user.getUserRole().getName())) {	
					HashMap<MonthDay, String> expectedStatuses = Utils.getExpectedAttendanceStatuses(user);
					String expectedUsername = user.getUsername();
					String expectedFullName = user.getFirstName()+" "+user.getLastName();
					
					ArrayList<MonthDay> monthDays = adp.goThroughWeek();
					ArrayList<String> icons = new ArrayList<String>();
					WebElement row = adp.getRowByIdentifier(expectedUsername, "td[1]/div/div[2]/p");
					Assert.assertTrue(row!=null);
					
					WebElement fullNameCell = row.findElement(By.xpath("td[1]/div/div[2]/h3"));
					String actualFullName = fullNameCell.getText();
					Assert.assertEquals(expectedFullName, actualFullName);
					
					WebElement rowNumberCell = row.findElement(By.xpath("td[1]/div/h2"));
					int rowNumber = Integer.parseInt(rowNumberCell.getText());
					icons = adp.goThroughWeekIcons(rowNumber);
					
					HashMap<MonthDay, String> actualStatuses = new HashMap<MonthDay, String>();
					int j = 0;
					while (j < 5) {
						actualStatuses.put(monthDays.get(j), icons.get(j));
						j++;
					}
					compareAttendanceStatuses(monthDays, expectedStatuses, actualStatuses);
				}	
			}
			adp.prevWeekTop.click();
		}
	}
	
	
}
