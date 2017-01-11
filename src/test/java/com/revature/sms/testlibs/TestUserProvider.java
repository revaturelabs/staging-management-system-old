package com.revature.sms.testlibs;

import static com.revature.sms.StagingManagementSystemApplicationTests.hashPassword;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.BatchTypeRepo;
import com.revature.sms.domain.dao.UserRoleRepo;

/**
 * TestUserProvider is a class that provides static factory methods that provide well-defined users to be used in test cases.
 * @author Sage
 *
 */

public class TestUserProvider {
	
	@Autowired
	static BatchTypeRepo btr;
	
	@Autowired
	static UserRoleRepo urr;
	
	/**
	 * getAssociate creates and returns a specific associate-role User
	 * @return The associate user
	 * username - TestAssociateOne
	 * firstname - Albert
	 * lastname - Einstein
	 * password - E=MC^2
	 * BatchType - Java
	 * UserRole - Associate
	 */
	
	public static User getAssociate(){
		return new User("TestAssociateOne", "Albert", "Einstein", hashPassword("E=MC^2"), btr.findByType("Java"), new ArrayList<AssociateAttendance>(), new ArrayList<AssociateTask>(), urr.findByName("associate"));
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

	public static User getAdmin(){
		return new User("TestAdminOne", "Alan", "Turing", hashPassword("DaisyDaisy"), urr.findByName("admin"));
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
	
	public static User getSuperAdmin(){
		return new User("TestSuperAdminOne", "John", "Conway", hashPassword("LifeIsButAGame"), urr.findByName("superAdmin"));
	}
	
	
}
