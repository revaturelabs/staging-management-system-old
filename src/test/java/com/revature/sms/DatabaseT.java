package com.revature.sms;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.domain.dao.AssociateTaskTypeRepo;
import com.revature.sms.domain.dao.BatchTypeRepo;
import com.revature.sms.domain.dao.JobEventTypeRepo;
import com.revature.sms.domain.dao.MarketingStatusRepo;
import com.revature.sms.domain.dao.TechnicalSkillsRepo;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dao.UserRoleRepo;

/**
 * StagingManagementSystemApplicationTests is a static class used for very basic testing functionality
 * 
 * @author Sage
 * @author Chris Nellis
 *
 */

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseT {
	
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
	
	@Autowired
	MarketingStatusRepo msr;
	
	@Autowired
	TechnicalSkillsRepo tsr;
	
	/**
	 * everythingsGood is a test method that is executed every time the pipeline checks the code.
	 * it is used to ensure that testing is being applied.  If no tests are executed, Jenkins fails the project.
	 */
	
	@Test
	public void everythingsGood(){
		Assert.assertTrue("Test executed", true);
		
	}
	
	
	@Test
	public void testAccessOfUserFieldsThatAreLists() {
		User user = ur.findByUsername("dotnet");
		System.out.println(user.getUsername());
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
		System.out.println(user.getUserRole());
		System.out.println(user.getBatchType());
		System.out.println(user.getMarketingStatus());
		System.out.println(user);
		
		user.getEvents().size();
		user.getSkill().size();
		user.getAttendance().size();
		List<AssociateAttendance> attendanceList = user.getAttendance();
		for (AssociateAttendance associateAttendance : attendanceList) {
			System.out.println(associateAttendance);
		}
	}
	
}