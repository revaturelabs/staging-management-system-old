package com.revature.sms;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.revature.sms.pagefactory.AdminPage;

public class AdminT2 extends AbstractT {
	

	//Tests that when different types of users login and logout, they are navigated to the correct pages
	@Test
	public void testLoginHeaderLogout() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		Assert.assertTrue(adp.verify());
		Assert.assertEquals(expected.getProperty("adminPg"), adp.header.getText());
		adp.carefulClick("logout");
		Assert.assertTrue(lp.verify());
	}
	
	@Test
	public void testPasswordChange() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		adp.carefulClick("settings");
		Assert.assertTrue(cpw.verify());
		cpw.oldPass.sendKeys(inputs.getProperty("adminPW"));
		cpw.newPass.sendKeys(inputs.getProperty("adminPW2"));
		cpw.confirmPass.sendKeys(inputs.getProperty("adminPW2"));
		cpw.carefulClick("submit");
		adp.carefulClick("logout");
		
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW2"));
		adp.carefulClick("settings");
		Assert.assertTrue(cpw.verify());
		cpw.oldPass.sendKeys(inputs.getProperty("adminPW2"));
		cpw.newPass.sendKeys(inputs.getProperty("adminPW"));
		cpw.confirmPass.sendKeys(inputs.getProperty("adminPW"));
		cpw.carefulClick("submit");
		adp.carefulClick("logout");
						
	}
	
	
	@Test
	public void testCancelButtons() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		adp.carefulClick("settings");
		cpw.carefulClick("cancel");
		adp.carefulClick("logout");
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
