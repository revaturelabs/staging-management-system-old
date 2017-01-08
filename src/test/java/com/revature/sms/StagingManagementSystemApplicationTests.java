package com.revature.sms;

import java.sql.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dao.UserRoleRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StagingManagementSystemApplicationTests {

	@Autowired
	UserRoleRepo urr;
	
	@Autowired
	UserRepo ur;
	
	@Autowired
	AssociateAttendanceRepo aar;

	//Test associate attendance list retrieval
/*
	@Test
	public void testAttendance(){
		aar.save(new AssociateAttendance(ur.findByUsername("java"), new Date(2017, 1, 7), false, false, null));
		aar.save(new AssociateAttendance(ur.findByUsername("java"), new Date(2017, 1, 7), false, false, null));
		List<AssociateAttendance> list = aar.findByAssociate(ur.findByUsername("java"));
		for (AssociateAttendance associateAttendance : list) {
			System.out.println(associateAttendance);
		}
		
	}*/
	
	
	//Initialize Test Data:
	/*
	@Test
	public void makeData(){
		urr.save(new UserRole("associate"));
		urr.save(new UserRole("admin"));
		urr.save(new UserRole("superAdmin"));
		ur.save(new User("admin","Admin","Admin",urr.findByName("admin"),"password"));
		ur.save(new User("superadmin","Super","Admin",urr.findByName("superAdmin"),"password"));
		ur.save(new User("java","Java","Johnny",urr.findByName("associate"),"password", "Java"));
		ur.save(new User("dotnet","DotNet","Dave",urr.findByName("associate"),"password", ".NET"));
		ur.save(new User("setDET","SDET","Sally",urr.findByName("associate"),"password", ".NET"));
	}*/
	
	@Test
  	public void getUser(){
		System.out.println(ur.findByUsername("admin"));
  	}
	
}
