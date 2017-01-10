package com.revature.sms;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.AssociateTaskType;
import com.revature.sms.domain.JobEventType;
import com.revature.sms.domain.User;
import com.revature.sms.domain.UserRole;
import com.revature.sms.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.domain.dao.AssociateTaskTypeRepo;
import com.revature.sms.domain.dao.BatchTypeRepo;
import com.revature.sms.domain.dao.JobEventTypeRepo;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dao.UserRoleRepo;

import com.revature.sms.util.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabasePopulationTest {
	
	@Ignore
	@Test
	public void everythingsGood(){
		Logger.getRootLogger().debug("No tests right now");
	}
	
	
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
	
	//Initialize Test Data:

	@Ignore
	@Test
	public void makeData(){
		urr.save(new UserRole("associate"));
		urr.save(new UserRole("admin"));
		urr.save(new UserRole("superAdmin"));
		
		ur.save(new User("admin","Admin","Admin",Utils.hashPassword("password"),urr.findByName("admin")));
		ur.save(new User("superadmin","Super","Admin",Utils.hashPassword("password"),urr.findByName("superAdmin")));
		
		ur.save(new User("java","Java","Johnny",Utils.hashPassword("password"),btr.findByType("Java"),new ArrayList<AssociateAttendance>(),new ArrayList<AssociateTask>(),urr.findByName("associate")));
		ur.save(new User("dotnet","DotNet","Dave",Utils.hashPassword("password"),btr.findByType(".NET"),new ArrayList<AssociateAttendance>(),new ArrayList<AssociateTask>(),urr.findByName("associate")));
		ur.save(new User("sdet","SDET","Sally",Utils.hashPassword("password"),btr.findByType("SDET"), new ArrayList<AssociateAttendance>(),new ArrayList<AssociateTask>(),urr.findByName("associate")));
		
		jetr.save(new JobEventType("Selected"));
		jetr.save(new JobEventType("Interviewed"));
		jetr.save(new JobEventType("On Location"));
		attr.save(new AssociateTaskType("Certification"));
		attr.save(new AssociateTaskType("Panel"));		
	}
	
	
	//Test associate attendance list retrieval
	@Ignore
	@Test
	public void testAttendance(){
		aar.save(new AssociateAttendance(new Date(1483765200000L), false, false, null));   //The long passed to the Date constructor is the number of milliseconds since Jan. 1 1970.
		List<AssociateAttendance> list = aar.findByDate(ur.findByUsername("java"));
		for (AssociateAttendance associateAttendance : list) {
			System.out.println(associateAttendance);
		}
	}
	
	
}
