package com.revature.sms;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.sms.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.domain.dao.AssociateTaskTypeRepo;
import com.revature.sms.domain.dao.BatchTypeRepo;
import com.revature.sms.domain.dao.JobEventTypeRepo;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dao.UserRoleRepo;

/**
 * StagingManagementSystemApplicationTests is a static class used for very basic testing functionality
 * 
 * @author Sage
 * @author Chris Nellis
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class StagingManagementSystemApplicationTests {
	
	
	@Autowired
	UserRoleRepo urr;
	
	@Autowired
	UserRepo ur;
	
	@Autowired
	AssociateAttendanceRepo aar;
	
	@Autowired
	JobEventTypeRepo jetr;
	
	@Autowired
	AssociateTaskTypeRepo attr;
	
	@Autowired
	BatchTypeRepo btr;
	
	/**
	 * everythingsGood is a test method that is executed every time the pipeline checks the code.
	 * it is used to ensure that testing is being applied.  If no tests are executed, Jenkins fails the project.
	 */
	
	@Test
	public void everythingsGood(){
		Assert.assertTrue("Test executed", true);
		
	}
	
	
	

	
	
	/**
	 * A password hashing algorithm used for testing.
	 * @param inputPassword
	 * @return The hashed password
	 */
	public static String hashPassword(String inputPassword) {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA");
			md.update(inputPassword.getBytes());
			byte[] byteData = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			Logger.getRootLogger().error("No such Algorithm", e);
			return null;
		}
	}
	

	
}
