package com.revature.sms;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.revature.sms.pagefactory.AdminPage;

public class AdminT2 extends AbstractT {
	AdminPage mp;
	
	@Before
	public void setPageType() {
		mp = new AdminPage(driver);
	}


	//Tests that when different types of users login and logout, they are navigated to the correct pages
	@Test
	public void testLoginHeaderLogout() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		Assert.assertTrue(mp.verify());
		Assert.assertEquals(expected.getProperty("adminPg"), mp.header.getText());
		mp.carefulClick("logout");
		Assert.assertTrue(lp.verify());
	}
	
	@Test
	public void testPasswordChange() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		mp.carefulClick("settings");
		Assert.assertTrue(cpw.verify());
		cpw.oldPass.sendKeys(inputs.getProperty("adminPW"));
		cpw.newPass.sendKeys(inputs.getProperty("adminPW2"));
		cpw.confirmPass.sendKeys(inputs.getProperty("adminPW2"));
		cpw.carefulClick("submit");
		mp.carefulClick("logout");
		
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW2"));
		mp.carefulClick("settings");
		Assert.assertTrue(cpw.verify());
		cpw.oldPass.sendKeys(inputs.getProperty("adminPW2"));
		cpw.newPass.sendKeys(inputs.getProperty("adminPW"));
		cpw.confirmPass.sendKeys(inputs.getProperty("adminPW"));
		cpw.carefulClick("submit");
		mp.carefulClick("logout");
						
	}
	
	
	@Test
	public void testCancelButtons() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		mp.carefulClick("settings");
		cpw.carefulClick("cancel");
		mp.carefulClick("logout");
	}
	
	
	
	
	// This test is temporarily unusable and irrelevant while the search bar is being fixed
	// Test to enter username in search box and verify correct associate name is returned
	/*
	@Test
	public void testSearchBar() {
		lp.login(inputs.getProperty("adminUN"), inputs.getProperty("adminPW"));
		Assert.assertTrue(mp.verify());
		Assert.assertEquals(expected.getProperty("adminPg"), mp.header.getText());
		
		mp.searchBox.sendKeys("Java");
		Assert.assertEquals(expected.getProperty("java"), mp.searchResult.getText());
		mp.searchBox.clear();
		
		mp.searchBox.sendKeys("DotNet");
		Assert.assertEquals(expected.getProperty("dotnet"), mp.searchResult.getText());
		mp.searchBox.clear();
		
		mp.searchBox.sendKeys("SDET");
		Assert.assertEquals(expected.getProperty("sdet"), mp.searchResult.getText());
		mp.searchBox.clear();
		
		mp.carefulClick("logout");
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
		mp.carefulClick("logout");
	}
	
}
