package com.revature.sms;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
		//TODO: Write a test case that succeeds when a test logs in using valid Associate credentials
	}
	
	@Test
	void testAdminLogin(){
		//TODO: Write a test case that succeeds when a test logs in using valid Admin credentials
	}
	
	@Test
	void testSuperAdminLogin(){
		//TODO: Write a test case that succeeds when a test logs in using valid Super Admin credentials
	}
	
	@Test
	void testWrongPasswordLogin(){
		//TODO: Write a negative test case that fails when the test fails to log in with an invalid password and without a password.
	}
	
	@Test
	void testWrongUserLogin(){
		//TODO:  Write a negative test case that succeeds when the test fails to log in with an invalid username and without entering a username.
	}
	
	@Test
	void testLogout(){
		//TODO: Write a negative test case that succeeds when the test fails to access content after logging out.
	}
	
	//TODO: Write further negative test cases related to accessing forbidden content in various situations
	
	@AfterClass
	void getUserDataManager(){
		udm.removeAllTestUsers();
	}
	
}
