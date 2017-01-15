package com.revature.sms;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.sms.domain.User;
import com.revature.sms.testlibs.TestUserProvider;
import com.revature.sms.testlibs.UserDataManager;

/**
 * LoginTest is a test suite class that tests login functionality.
 * @author Sage
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest_IGNORE_FOR_NOW {
	
	
	@Autowired
	private UserDataManager udm;
	
	@Autowired
	private TestUserProvider users;
	
	
	
	@Test
	public void testAssociateLogin(){
		
		User testUser = users.getAssociate();
		udm.createTestUser(testUser);
		Assert.assertTrue(true);
		//TODO: Write a test case that succeeds when a test logs in using valid Associate credentials
	}
	
	@Test
	public void testAdminLogin(){
		User testUser = users.getAdmin();
		udm.createTestUser(testUser);
		//TODO: Write a test case that succeeds when a test logs in using valid Admin credentials
	}
	
	@Test
	public void testSuperAdminLogin(){
		User testUser = users.getSuperAdmin();
		udm.createTestUser(testUser);
		//TODO: Write a test case that succeeds when a test logs in using valid Super Admin credentials
	}
	
	@Test
	public void testWrongPasswordLogin(){
		User loginAttemptingUser = users.getAssociate();
		User otherUser = users.getSuperAdmin();
		udm.createTestUser(loginAttemptingUser);
		udm.createTestUser(otherUser);
		//TODO: Write a negative test case that fails when the test fails to log in with an invalid password, with another user's password, and without a password.
	}
	
	@Test
	public void testWrongUserLogin(){
		
		//TODO:  Write a negative test case that succeeds when the test fails to log in with an invalid username and without entering a username.
	}
	
	@Test
	public void testLogout(){
		User testUser = users.getSuperAdmin();
		udm.createTestUser(testUser);
		//TODO: Write a negative test case that succeeds when the test fails to access content after logging out.
	}
	
	//TODO: Write further negative test cases related to accessing forbidden content in various situations
	
	/**
	 * cleanup is an important method called after each test case, that ensures idempotency of test cases.
	 * Note that as it is called after each @test method, users created by one @test will not exist for other tests.
	 */
	
	
	
	@After
	public void cleanup(){
		udm.removeAllTestUsers();
	}
	
}