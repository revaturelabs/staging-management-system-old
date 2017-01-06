package com.revature.sms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.sms.domain.User;
import com.revature.sms.domain.UserRole;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dao.UserRoleRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StagingManagementSystemApplicationTests {

	@Autowired
	UserRoleRepo urr;
	
	@Autowired
	UserRepo ur;
/*
	@Test 
	public void UserRoleClearIt(){
		System.out.println(urr.deleteByName("Janitor"));
	}
	
	@Test
	public void UserRoleAddIt() {
		urr.save(new UserRole("Janitor"));
	}
	
	@Test
	public void UserRoleGetIt(){
		System.out.println(urr.findByName("Janitor"));
	}

	@Test 
	public void UserRoleDeleteIt(){
		System.out.println(urr.deleteByName("Janitor"));
	}*/
	
	
	//Initialize Data:
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
	}
	*/
	@Test
	public void getUser(){
		System.out.println(ur.findByUsername("admin"));
	}
	
}
