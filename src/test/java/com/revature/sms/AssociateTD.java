package com.revature.sms;

import java.sql.Timestamp;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.User;
import com.revature.sms.util.Utils;

public class AssociateTD extends AbstractT {

	//Tests that the user's attendance data that is displayed on the website matches the attendance data contained
	//in the database.
	@Test
	public void testAssociateAttendanceView() {
		lp.login(un, pw);
		asp.verify();

		User user = ur.findByUsername(un);
		HashMap<MonthDay, String> expectedStatuses = Utils.getExpectedAttendanceStatuses(user);
		
		boolean flag = true;
		//This do-while loop uses the navigation buttons on the calendar to go through all of the weeks in 
		//reverse order until it receives the toast notification that says you can't go back any further.
		while (!expected.getProperty("tooFarBack").equals(asp.getToastMessage())) {
			ArrayList<MonthDay> monthDays = asp.goThroughWeek();
			ArrayList<String> icons = asp.goThroughWeekIcons();
			HashMap<MonthDay, String> actualStatuses = new HashMap<MonthDay, String>();  //Website data
			
			int i = 0;
			//This while loop organizes the dates, check-ins, and verifications from the website into a HashMap.
			while (i < 5) {
				actualStatuses.put(monthDays.get(i), icons.get(i));
				i++;
			}

			compareAttendanceStatuses(monthDays, expectedStatuses, actualStatuses);
			asp.prevWeek.click();
		} 
	}
		
		
	/*
	//This test adds skills and confirms that these added skills are reflected in the database.
	@Test
	public void testAddSkill() {
		lp.login(un, pw);
		asp.settings.click();
		Assert.assertTrue(sw.verify());
		
		ArrayList<String> expectedSkills = new ArrayList<String>();
		expectedSkills.add(inputs.getProperty("javaSkill"));
		expectedSkills.add(inputs.getProperty("jdbcSkill"));
		expectedSkills.add(inputs.getProperty("pythonSkill"));
		
		sw.chooseSkill(expectedSkills.get(0));
		sw.addSkill.click();
		sw.chooseSkill(expectedSkills.get(1));
		sw.addSkill.click();
		sw.chooseSkill(expectedSkills.get(2));
		sw.addSkill.click();
		
		List<WebElement> addedSkillList = sw.getAddedSkills();
		int i=0;
		for (WebElement addedSkill:addedSkillList) {
			String skill = addedSkill.getText().trim();
			System.out.println(skill);
			String[] skillPieces = skill.split("\n");
			Assert.assertEquals(expectedSkills.get(i), skillPieces[0]);
			i++;
		}
		
		sw.saveSkills.click();
		Assert.assertEquals(expected.getProperty("skillUpdate"), asp.getToastMessage());
		sw.cancel.click();
		Assert.assertTrue(asp.verify());
		
		User user = ur.findByUsername(un);
		ArrayList<String> actualSkills = new ArrayList<String>();
		Set<TechnicalSkills> skillset = user.getSkill();
		Iterator<TechnicalSkills> itr = skillset.iterator();
		while (itr.hasNext()) {
			TechnicalSkills ts = itr.next();
			String actualSkill = ts.getSkill();
			actualSkills.add(actualSkill);
		}
		Collections.sort(expectedSkills);
		Collections.sort(actualSkills);
		Assert.assertEquals(expectedSkills, actualSkills);
		
		asp.settings.click();
		Assert.assertTrue(sw.verify());
		
		
		List<WebElement> deletionIcons = sw.getDeletionIcons();
		for (WebElement icon:deletionIcons) {
			icon.click();
		}
		sw.saveSkills.click();
		Assert.assertEquals(expected.getProperty("skillUpdate"), asp.getToastMessage());
		sw.cancel.click();
		Assert.assertTrue(asp.verify());
		
		
		//I'd like to check and make sure that the database has been emptied, but when I try to
		//do that, I get a StackOverflowError.
		
		System.out.println("Here1");
		User userAgain = ur.findByUsername(un);
		System.out.println(userAgain.getLastName());
		System.out.println("Here2");
		Set<TechnicalSkills> nullset = userAgain.getSkill();
		System.out.println(nullset);
		System.out.println("Here3");
		Assert.assertNull(nullset);
		System.out.println("Here4");
		
	}
	*/
	
		
	// Maybe we should wait until there is a way to unschedule certifications on
	// the website before completing this test.
	/*
	//This test schedules a certification.
	@Test
	public void testCertificationScheduling() {
	 	lp.login(inputs.getProperty("javaUN"), inputs.getProperty("PW"));
		asp.click("certification"); Assert.assertTrue(scw.verify());
		scw.enterDate.sendKeys(inputs.getProperty("certDate"));
		scw.enterNote.sendKeys(inputs.getProperty("certNote"));
		scw.click("submit"); }
	*/
}
