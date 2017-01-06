package com.revature.sms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.sms.domain.UserRole;
import com.revature.sms.domain.dao.UserRepo;
import com.revature.sms.domain.dao.UserRoleRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StagingManagementSystemApplicationTests {

	@Autowired
	UserRoleRepo urr;

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
	}
}
