package com.revature.sms;

import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import com.revature.sms.domain.User;

import static com.revature.sms.database.DomainHelper.*;


public class SuperAdminTD extends AdminTD {

	@Test
	public void testAssociateInformationView() {
		lp.login(un, pw);
		Assert.assertTrue(sap.verify());
		sap.switchView.click();
		
		List<User> users = ur.findAll();
		for (User user:users) {
		
			HashMap<String, String> expectedInfo = getExpectedAssociateInfo(user);
		
		}
	}
	
}
