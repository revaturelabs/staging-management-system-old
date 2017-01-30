package com.revature.sms;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.AssociateTaskType;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.JobEvent;
import com.revature.sms.domain.JobEventType;
import com.revature.sms.domain.MarketingStatus;
import com.revature.sms.domain.TechnicalSkills;
import com.revature.sms.domain.User;
import com.revature.sms.domain.UserRole;
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
	/**
	 * A password hashing algorithm used for testing.
	 * @param inputPassword
	 * @return The hashed password
	 */

	@Test
	public void makeData(){
		urr.save(new UserRole("associate"));
		urr.save(new UserRole("admin"));
		urr.save(new UserRole("superAdmin"));
		btr.save(new BatchType("SDET"));
		btr.save(new BatchType("Java"));
		btr.save(new BatchType(".NET"));
		jetr.save(new JobEventType("Selected"));
		jetr.save(new JobEventType("Interviewed"));
		msr.save(new MarketingStatus("Staging"));
		msr.save(new MarketingStatus("Placed"));
		tsr.save(new TechnicalSkills(""));
		ur.save(new User("admin","Admin","Admin",hashPassword("password"),urr.findByName("admin")));
		ur.save(new User("superadmin","Super","Admin",hashPassword("password"),urr.findByName("superAdmin")));
		ur.save(new User("java","Java","Johnny",hashPassword("password"),btr.findByType("Java"),new ArrayList<AssociateAttendance>(), new ArrayList<AssociateTask>(),urr.findByName("associate"), new Timestamp(2017, 1, 1, 0, 0, 0, 0), new HashSet<TechnicalSkills>(), new ArrayList<JobEvent>(), msr.findByStatus("")));
		ur.save(new User("dotnet","DotNet","Dave",hashPassword("password"),btr.findByType(".NET"),new ArrayList<AssociateAttendance>(), new ArrayList<AssociateTask>(),urr.findByName("associate"), new Timestamp(2017, 1, 1, 0, 0, 0, 0), new HashSet<TechnicalSkills>(), new ArrayList<JobEvent>(), msr.findByStatus("")));
		ur.save(new User("sdet","SDET","Sally",hashPassword("password"),btr.findByType("SDET"),new ArrayList<AssociateAttendance>(), new ArrayList<AssociateTask>(),urr.findByName("associate"), new Timestamp(2017, 1, 1, 0, 0, 0, 0), new HashSet<TechnicalSkills>(), new ArrayList<JobEvent>(), msr.findByStatus("")));
		jetr.save(new JobEventType("Selected"));
		jetr.save(new JobEventType("Interviewed"));
		jetr.save(new JobEventType("On Location"));
		attr.save(new AssociateTaskType("Certification"));
		attr.save(new AssociateTaskType("Panel"));		
	}
	
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