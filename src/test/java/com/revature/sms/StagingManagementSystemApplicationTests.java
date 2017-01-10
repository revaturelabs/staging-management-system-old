package com.revature.sms;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StagingManagementSystemApplicationTests {
	
	@Test
	public void everythingsGood(){
		Logger.getRootLogger().debug("No tests right now");
	}
	//This class runs on *EVERY* commit to a feature branch!! Make sure code here doesn't create needless database objects
	/*
	
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
*/
	//Test associate attendance list retrieval

	/*@Test
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
		btr.save(new BatchType("SDET"));
		btr.save(new BatchType("Java"));
		btr.save(new BatchType(".NET"));
		ur.save(new User("admin","Admin","Admin",hashPassword("password"),urr.findByName("admin")));
		ur.save(new User("superadmin","Super","Admin",hashPassword("password"),urr.findByName("superAdmin")));
		ur.save(new User("java","Java","Johnny",hashPassword("password"),btr.findByType("java"),new ArrayList<AssociateAttendance>(), new ArrayList<AssociateTask>(),urr.findByName("associate")));
		ur.save(new User("dotnet","DotNet","Dave",hashPassword("password"),btr.findByType(".NET"),new ArrayList<AssociateAttendance>(), new ArrayList<AssociateTask>(),urr.findByName("associate")));
		ur.save(new User("sdet","SDET","Sally",hashPassword("password"),btr.findByType("SDET"),new ArrayList<AssociateAttendance>(), new ArrayList<AssociateTask>(),urr.findByName("associate")));
		jetr.save(new JobEventType("Selected"));
		jetr.save(new JobEventType("Interviewed"));
		jetr.save(new JobEventType("On Location"));
		attr.save(new AssociateTaskType("Certification"));
		attr.save(new AssociateTaskType("Panel"));		
	}
	*/
	
	// Defunct hashPassword script. While used to create initial hashed passwords here, actual hashing will take place on the client-side javascript
	public static String hashPassword(String inputPassword) {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA");
			md.update(inputPassword.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	
}
