package com.revature.sms;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.revature.sms.domain.User;
import com.revature.sms.util.Utils;

public class AdminTD extends AbstractT {

	@Test
	public void testAdminAttendanceView() {
		lp.login(un, pw);
		adp.verify();
		
		List<User> users = ur.findAll();
		for (User user:users) {
			HashMap<MonthDay, String> expectedStatuses = Utils.getExpectedAttendanceStatuses(user);
			String expectedUsername = user.getUsername();
			String expectedFullName = user.getFirstName()+" "+user.getLastName();
			boolean usernameFound = false;
			
			ArrayList<String> actualUsernames = adp.getAssociateNames("username");
			ArrayList<String> actualFullnames = adp.getAssociateNames("fullName");
			HashMap<MonthDay, String> actualStatuses = new HashMap<MonthDay, String>();
		}
		
		
	}
	
	
	
	
	
	
}
