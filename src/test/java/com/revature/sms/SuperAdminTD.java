package com.revature.sms;

import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.revature.sms.domain.User;

import static com.revature.sms.database.DomainHelper.*;


public class SuperAdminTD extends AdminTD {

	@Test
	public void testAssociateInformationView() {
		lp.login(un, pw);
		Assert.assertTrue(sap.verify());
		sap.switchView.click();
		
		
		
		List<User> users = ur.findAll();
		//WebElement atb = driver.findElement(By.xpath("//*[@class=\"associateTableViewTableContainer\"]/table/tbody"));
		for (User user:users) {
			if ("associate".equals(user.getUserRole().getName())) {	
				HashMap<String, String> expectedInfo = getExpectedAssociateInfo(user);
				String fullName = user.getFirstName()+" "+user.getLastName();
				WebElement row = sap.getRowByIdentifier(sap.associateTableBody, fullName, "td[1]");
				Assert.assertTrue(row!=null);
				HashMap<String, String> actualInfo = sap.goThroughAssociateTable(row, expected);
				compareHashes(expectedInfo, actualInfo);
			}
		}
	}
	
}
