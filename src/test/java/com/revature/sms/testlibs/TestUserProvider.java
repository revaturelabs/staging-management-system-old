package com.revature.sms.testlibs;

import static com.revature.sms.StagingManagementSystemApplicationTests.hashPassword;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.BatchTypeRepo;
import com.revature.sms.domain.dao.UserRoleRepo;

/**
 * TestUserProvider is a class that provides factory methods that provide well-defined users to be used in test cases.
 * @author Sage
 *
 */

@Service
public class TestUserProvider {
	
	
	
	@Autowired
	private UserRoleRepo urr;
	@Autowired
	private BatchTypeRepo btr;
	
	
	/**
	 * This nullary constructor does little
	 */
	
	public TestUserProvider(){
		super();
	}

	/**
	 * getAdmin creates and returns a specific admin-role User
	 * @return The admin user
	 * username - TestAdminOne
	 * firstname - Alan
	 * lastname - Turing
	 * password - DaisyDaisy
	 * UserRole - Admin
	 */

	public User getAdmin(){
		return new User("TestAdminOne", "Alan", "Turing", hashPassword("DaisyDaisy"), urr.findByName("admin"), null);
	}
	
	/**
	 * getSuperAdmin returns a specific admin-role User
	 * @return The super admin user
	 * username - TestSuperAdminOne
	 * firstname - John
	 * lastname - Conway
	 * password - LifeIsButAGame
	 * UserRole - Super Admin
	 */
	
	public User getSuperAdmin(){
		return new User("TestSuperAdminOne", "John", "Conway", hashPassword("LifeIsButAGame"), urr.findByName("superAdmin"), null);
	}
	
	
}
