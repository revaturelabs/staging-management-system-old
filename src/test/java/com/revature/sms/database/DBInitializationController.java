package com.revature.sms.database;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.codoid.products.exception.FilloException;
import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.AssociateTask;
import com.revature.sms.domain.AssociateTaskType;
import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.JobEvent;
import com.revature.sms.domain.MarketingStatus;
import com.revature.sms.domain.ProjectUser;
import com.revature.sms.domain.TechnicalSkills;
import com.revature.sms.domain.Trainer;
import com.revature.sms.domain.User;
import com.revature.sms.domain.UserRole;
import com.revature.sms.domain.dao.AssociateTaskTypeRepo;
import com.revature.sms.domain.dao.BatchTypeRepo;
import com.revature.sms.domain.dao.MarketingStatusRepo;
import com.revature.sms.domain.dao.TrainerRepo;
import com.revature.sms.domain.dao.UserRoleRepo;
import com.revature.sms.util.ExcelHelper;
import com.revature.sms.util.Utils;

//This class can be used before tests to conveniently populate the database with test data from Excel.  
@Component
@Service
public class DBInitializationController {

	@Autowired
	private UserDataManager udm;
	
	@Autowired
	private BatchTypeRepo btr;
	
	@Autowired
	private UserRoleRepo urr;
	
	@Autowired
	private AssociateTaskTypeRepo attr;
	
	@Autowired
	private MarketingStatusRepo msr;
	
	@Autowired
	private TrainerRepo tr;
	
	
	DBInitializationController() {
		super();
	}
	
	public void initializeUsers() {
		try {
			ExcelHelper eh = new ExcelHelper("NewUsers");
			ArrayList<String> usernames = eh.getValues("username");
			ArrayList<String> firstNames = eh.getValues("firstName");
			ArrayList<String> lastNames = eh.getValues("lastName");
			ArrayList<String> unhashedPasswords = eh.getValues("unhashedPassword");
			ArrayList<String> batchTypes = eh.getValues("batchType");
			ArrayList<String> userRoles = eh.getValues("userRole");
			ArrayList<String> graduationDates = eh.getValues("graduationDate");
			ArrayList<String> marketingStatuses = eh.getValues("marketingStatus");
			//ArrayList<String> trainers = eh.getValues("trainer");
			
			
			List<AssociateAttendance> attendance = new ArrayList<AssociateAttendance>();
			List<AssociateTask> tasks = new ArrayList<AssociateTask>();
			List<JobEvent> events = new ArrayList<JobEvent>();
			Set<TechnicalSkills> skills = new HashSet<TechnicalSkills>();
			List<ProjectUser> projects = new ArrayList<ProjectUser>();
			
			
			
			//Each iteration of the loop corresponds to a new user that is added
			int i = 0;
			while (i < usernames.size()) {
				//Finds already existing batch types and user role types, which are added to the new user
				
				BatchType batchType = btr.findByType(batchTypes.get(i));
				UserRole userRole = urr.findByName(userRoles.get(i));
				String graduationDate = graduationDates.get(i);
				Timestamp gts = Utils.convertDateStringToTimestamp(graduationDate);
				MarketingStatus marketingStatus = msr.findByStatus(marketingStatuses.get(i));
				Trainer trainer = tr.findOne(0);  //This line is a placeholder until someone creates a method in the TrainerRepo that lets me find Trainers
				
				udm.createTestUser(usernames.get(i), firstNames.get(i), lastNames.get(i), 
								   unhashedPasswords.get(i), batchType, attendance, tasks, userRole, 
								   gts, skills, events, marketingStatus, projects, trainer);
				i++;
			}
		} catch (FilloException e) {Logger.getRootLogger().debug(e);}
	}
	

	//Calling this method with an object that has not called the initializeUsers method yet may cause
	//problems
	public void initializeUserObjects() {
		//This createdUsers array must be copied to avoid a ConcurrentModificationException
		ArrayList<User> arrayCopy = new ArrayList<User>();
		for (User user: udm.getCreatedUsers()) {
			arrayCopy.add(user);
		}
		
		//The outer loop looks through every new user.
		int i = 0;
		for (User u: arrayCopy) {
			String username = u.getUsername();
			ExcelHelper eh;
			ArrayList<String> dates = null; 
			ArrayList<String> checkIns = null; 
			ArrayList<String> verifications = null;
			ArrayList<String> notes = null;
			ArrayList<String> taskDates = null;
			ArrayList<String> taskNotes = null;
			ArrayList<String> taskTypes = null;
			try {
				eh = new ExcelHelper(username);
				dates = eh.getValues("attendanceDate");
				checkIns = eh.getValues("checkedIn");
				verifications = eh.getValues("verified");
				notes = eh.getValues("attendanceNote");
				
				taskTypes = eh.getValues("taskType");
				taskDates = eh.getValues("taskDate");
				taskNotes = eh.getValues("taskNote");
				
			} catch (FilloException e) {Logger.getRootLogger().debug(e);}
		
			//The inner loops create each user's attendance records, tasks, or job events 
			//(when they exist) and save them to the database.
			int j = 0;
			ArrayList<AssociateAttendance> attendanceList = new ArrayList<AssociateAttendance>();
			if (dates != null) {
				while (j < dates.size()) {
					String date = dates.get(j);
					Timestamp dts = Utils.convertDateStringToTimestamp(date);
					String checkedIn = checkIns.get(j);
					boolean ci = Boolean.parseBoolean(checkedIn);
					String verified = verifications.get(j);
					boolean v = Boolean.parseBoolean(verified);
					String note = notes.get(j);
					AssociateAttendance aa = new AssociateAttendance(dts, ci, v, note);
					attendanceList.add(aa);		
					j++;
				}
			}
			
			int k = 0;
			ArrayList<AssociateTask> taskList = new ArrayList<AssociateTask>();
			if (taskDates != null) {
				while (k < taskDates.size()) {
					String taskType = taskTypes.get(k);
					AssociateTaskType att = attr.findByType(taskType);
					String taskDate = taskDates.get(k);
					Timestamp tdts = Utils.convertDateStringToTimestamp(taskDate);
					String taskNote = taskNotes.get(k);
					AssociateTask at = new AssociateTask(att, tdts, taskNote);
					taskList.add(at);	
					k++;
				}
			}
			
			udm.editTestUser(i, "", "", "", "", new BatchType(), attendanceList, taskList, new UserRole(), new Timestamp(0));
			i++;
		}
	}	
	
	
	public void clearData() {
		udm.removeAllTestUsers();
	}
	
}
