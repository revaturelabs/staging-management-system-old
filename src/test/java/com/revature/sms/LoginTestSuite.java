package com.revature.sms;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.sms.domain.User;
import com.revature.sms.testlibs.TestUserProvider;
import com.revature.sms.testlibs.UserDataManager;

/**
 * LoginTestSuite is a test suite class that tests login functionality.
 * @author Sage
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTestSuite {

	UserDataManager udm = new UserDataManager();
	
	@Test
	void testAssociateLogin(){
		User testUser = TestUserProvider.getAssociate();
		udm.createTestUser(testUser);
		Assert.assertTrue(true);
		//TODO: Write a test case that succeeds when a test logs in using valid Associate credentials
	}
	
	@Test
	void testAdminLogin(){
		User testUser = TestUserProvider.getAdmin();
		udm.createTestUser(testUser);
		//TODO: Write a test case that succeeds when a test logs in using valid Admin credentials
	}
	
	@Test
	void testSuperAdminLogin(){
		User testUser = TestUserProvider.getSuperAdmin();
		udm.createTestUser(testUser);
		//TODO: Write a test case that succeeds when a test logs in using valid Super Admin credentials
	}
	
	@Test
	void testWrongPasswordLogin(){
		User loginAttemptingUser = TestUserProvider.getAssociate();
		User otherUser = TestUserProvider.getSuperAdmin();
		udm.createTestUser(loginAttemptingUser);
		udm.createTestUser(otherUser);
		//TODO: Write a negative test case that fails when the test fails to log in with an invalid password, with another user's password, and without a password.
	}
	
	@Test
	void testWrongUserLogin(){
		
		//TODO:  Write a negative test case that succeeds when the test fails to log in with an invalid username and without entering a username.
	}
	
	@Test
	void testLogout(){
		User testUser = TestUserProvider.getSuperAdmin();
		udm.createTestUser(testUser);
		//TODO: Write a negative test case that succeeds when the test fails to access content after logging out.
	}
	
	//TODO: Write further negative test cases related to accessing forbidden content in various situations
	
	/**
	 * cleanup is an important method called after each test case, that ensures idempotency of test cases.
	 * Note that as it is called after each @test method, users created by one @test will not exist for other tests.
	 */
	
	
	
	@After
	void cleanup(){
		udm.removeAllTestUsers();
	}
	
}
