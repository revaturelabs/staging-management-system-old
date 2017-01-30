package com.revature.sms;

import org.junit.After;
import org.junit.Test;

public class AdminT extends AbstractT {
	

	//Tests that when different types of users login and logout, they are navigated to the correct pages
	@Test
	public void testLoginHeaderLogout() {
		String expectedValue = expected.getProperty("adminPg");
		LoginHeaderLogoutTemplate(adp, inputs.getProperty("adminUN"), inputs.getProperty("PW"), expectedValue);
	}
	
	@Test
	public void testPasswordChange() {
		PasswordChangeTemplate(adp, inputs.getProperty("adminUN"), inputs.getProperty("PW"), inputs.getProperty("PW2"));
	}
	
	@Test
	public void testCancelButtons() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("PW"));
		adp.carefulClick("settings");
		cpw.carefulClick("cancel");
	}
	
	
	
	
	
	
	
	// This test is temporarily unusable and irrelevant while the search bar is being fixed
	// Test to enter username in search box and verify correct associate name is returned
	/*
	@Test
	public void testSearchBar() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		Assert.assertTrue(adp.verify());
		Assert.assertEquals(expected.getProperty("adminPg"), adp.header.getText());
		
		adp.searchBox.sendKeys("Java");
		Assert.assertEquals(expected.getProperty("java"), adp.searchResult.getText());
		adp.searchBox.clear();
		
		adp.searchBox.sendKeys("DotNet");
		Assert.assertEquals(expected.getProperty("dotnet"), adp.searchResult.getText());
		adp.searchBox.clear();
		
		adp.searchBox.sendKeys("SDET");
		Assert.assertEquals(expected.getProperty("sdet"), adp.searchResult.getText());
		adp.searchBox.clear();
		
		adp.carefulClick("logout");
		Assert.assertTrue(lp.verify());
	}
	*/
	
	
	
	public void testAdminAttendanceView() {
		
	}
	
	public void testAdminPageToastContainer() {
		
	}
	
	public void testAdminCalendarNavigation() {
		
	}
	
	@After
	public void after() {
		if (adp.verify()) {
			adp.carefulClick("logout");
		} 
	}
	
}
