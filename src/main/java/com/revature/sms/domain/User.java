package com.revature.sms.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.log4j.Logger;

//
/**
 * Object that represents the users on the application.
 *
 */
@Entity
@Table(name = "USERS")
public class User {
	/**
	 * int value that represents the primary key of the table, ID is used to
	 * identify a specific User object by the unique int value. Set up to
	 * automatically generate a value so that it doesn't have to be specified in
	 * a constructor or set manually.
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(allocationSize = 1, name = "userSeq", sequenceName = "USER_SEQ")
	@GeneratedValue(generator = "userSeq", strategy = GenerationType.SEQUENCE)
	private int ID;
	
	/**
	 * String that represents the username of the User object.
	 */
	@Column(name = "USERNAME", unique = true, nullable = false)
	private String username;
	
	/**
	 * String that represents the first name of the User object.
	 */
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	
	/**
	 * String that represents the last name of the User object.
	 */
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	/**
	 * String that represents the hashedPassword of the User object. All
	 * passwords are hashed in the database.
	 */
	@Column(name = "HASHED_PASSWORD", nullable = false)
	private String hashedPassword;
	
	/**
	 * BatchType object that represents the specific BatchType that the User
	 * belongs to. Different BatchTypes would imply different skills that the
	 * User would have. A user can only belong to a single BatchType.
	 */
	@ManyToOne
	@JoinColumn(name = "BATCH_TYPE")
	private BatchType batchType;

	/**
	 * List containing AssociateAttendence objects that keeps track of the user's attendance.
	 */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="ASSOCIATE")
	private List<AssociateAttendance> attendance;

	/**
	 * List containing AssociateTask objects that keeps track of the user's tasks.
	 */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="ASSOCIATE")
	private List<AssociateTask> tasks;

	/**
	 * List containing JobEvent objects that keeps track of the user's events.
	 */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="ASSOCIATE")
	private List<JobEvent> events;

	/**
	 * UserRole object that keeps track of the user's specific role.
	 */
	@ManyToOne
	@JoinColumn(name = "user_role")
	private UserRole userRole;
	
	/**
	 * Graduation date to track an associate's graduation date
	 */
	@Column(name = "GRADUATION_DATE")
	private Timestamp graduationDate;
	
	/**
	 * List of skills that a user has
	 */
	@ManyToMany(mappedBy="users", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<TechnicalSkills> skill;
	
	/**
	 * MarketingStatus object that keeps track of the user's marketing status
	 */
	@ManyToOne
	@JoinColumn(name = "marketing_status")
	private MarketingStatus marketingStatus;
	
	/**
	 * List containing Project objects that keeps track of the user's project.
	 */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="ASSOCIATE")
	private List<ProjectUser> project;
	
	/**
	 * Null args constructor. Doesn't initialize any of the User instance variables.
	 */
	public User() {
		super();
	}

	// constructor for associate
	/**
	 * Constructor for User object. This constructor is specifically designed to
	 * be used for creating a User who is an associate. Initializes all instance variables except for ID, as that is
	 * automatically generated on creation. 
	 * @param username String that represents the username of the User object.
	 * @param firstName String that represents the first name of the User object.
	 * @param lastName String that represents the last name of the User object.
	 * @param hashedPassword String that represents the hashedPassword of the User object.
	 * @param batchType BatchType object that represents the specific BatchType that the User belongs to.
	 * @param attendance List containing AssociateAttendence objects that keeps track of the user's attendance.
	 * @param tasks List containing AssociateTask objects that keeps track of the user's tasks.
	 * @param userRole UserRole object that keeps track of the user's specific role.
	 * @param graduationDate Graduation date tracks when an associate graduates from a batch
	 * @param skills gets a list of technical skills that an associate has
	 * @param project projects that a user is currently working on.
	 */
	public User(String username, String firstName, String lastName, String hashedPassword, BatchType batchType,
			List<AssociateAttendance> attendance, List<AssociateTask> tasks, UserRole userRole, Timestamp graduationDate, 
			Set<TechnicalSkills> skills, List<JobEvent> events, MarketingStatus marketingStatus,List<ProjectUser> project) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword = hashedPassword;
		this.batchType = batchType;
		this.attendance = attendance;
		this.tasks = tasks;
		this.userRole = userRole;
		this.graduationDate = graduationDate;
		this.skill = skills;
		this.events = events;
		this.marketingStatus = marketingStatus;
		this.project = project;
	}
	public List<ProjectUser> getProject() {
		return project;
	} 
 
	public void setProject(List<ProjectUser> project) {
		this.project = project;
	}

	// constructor for non-associate
	/**
	 * Constructor for User object. This constructor is meant to be used to create
	 * users who aren't associates. Initializes the username, firsName, lastName, hashedPassword,
	 * and userRole variables of the user object based on the supplied values.
	 * @param username String that represents the username of the User object.
	 * @param firstName String that represents the first name of the User object.
	 * @param lastName String that represents the last name of the User object.
	 * @param hashedPassword String that represents the hashedPassword of the User object.
	 * @param userRole UserRole object that keeps track of the user's specific role.
	 */
	public User(String username, String firstName, String lastName, String hashedPassword, UserRole userRole) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword = hashedPassword;
		this.userRole = userRole;
	}
	
	/**
	 * Method to retrieve the ID value of the User object.
	 * @return ID int value that represents the id of the User.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Method to manually set the ID value of the current User object.
	 * 
	 * @param iD
	 *            int value that represents the id to be set.
	 */
	public void setID(int iD) {
		ID = iD;
	}
	
	/**
	 * Method to retrieve the username of the User object.
	 * @return username String value that represents the username of the User.
	 */
	public String getUsername() {
		
		return username;
	}
	
	/**
	 * Method to manually set the username of the current User object.
	 * @param username String value that represents the username to be set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Method to retrieve the first name of the User object.
	 * @return firstName String value that represents the first name of the User.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Method to manually set the first name of the current User object.
	 * @param firstName String value that represents the first name to be set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Method to retrieve the last name of the User object.
	 * @return lastName String value that represents the first name of the User.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Method to manually set the last name of the current User object.
	 * @param lastName String value that represents the last name to be set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Method to retrieve the hashedPassword of the current User object.
	 * @return hashedPassword String value that represents the hashedPassword to be set.
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}
	
	/**
	 * Method to manually set the hashedPassword of the User object.
	 * @param hashedPassword String value that represents the hashedPassword to be set.
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	
	/**
	 * Method that sets the password of the current User to an empty password.
	 */
	public void blankPassword() {
		String empty = "";
		this.hashedPassword = empty;
	}
	
	/**
	 * Method that retrieves the BatchType object of the current User object.
	 * @return batchType BatchType object that represents the specific BatchType that the User 
	 * belongs to.
	 */
	public BatchType getBatchType() {
		return batchType;
	}
	
	/**
	 * Method that sets the BatchType object of the current User object.
	 * @param batchType BatchType object that represents the specific BatchType that the User 
	 * belongs to.
	 */
	public void setBatchType(BatchType batchType) {
		this.batchType = batchType;
	}
	
	/**
	 * Method that retrieves the list of AssociateAttendence objects that represents the User's attendance.
	 * @return attendance List containing all the AssociateAttendence objects that belongs to 
	 * the User object.
	 */
	public List<AssociateAttendance> getAttendance() {
		return attendance;
	}
	
	/**
	 * Method that sets the list of AssociateAttendence objects that represents the User's attendance.
	 * @param attendance List containing all the AssociateAttendence objects that belongs to 
	 * the User object.
	 */
	public void setAttendance(List<AssociateAttendance> attendance) {
		this.attendance = attendance;
	}
	
	/**
	 * Method that retrieves the list of AssociateTask objects that represents the User's tasks.
	 * @return tasks List containing all the AssociateTask objects that belongs to 
	 * the User object.
	 */
	public List<AssociateTask> getTasks() {
		return tasks;
	}
	
	/**
	 * Method that sets the list of AssociateTasks objects that represents the User's tasks.
	 * @param tasks List containing all the AssociateTask objects that belongs to 
	 * the User object.
	 */
	public void setTasks(List<AssociateTask> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Method that retrieves the list of JobEvent objects that represents the User's events.
	 * @return events List containing all the JobEvent objects that belongs to
	 * the User object.
	 */
	public List<JobEvent> getEvents() {
		return events;
	}

	/**
	 * Method that sets the list of JobEvent objects that represents the User's events.
	 * @param events List containing all the JobEvent objects that belongs to
	 * the User object.
	 */
	public void setEvents(List<JobEvent> events) {
		this.events = events;
	}

	/**
	 * Method that retrieves the role of the current user.
	 * @return userRole UserRole object that represents the current user's role.
	 */
	public UserRole getUserRole() {
		return userRole;
	}
	
	/**
	 * Method that manually sets the role of the User object.
	 * @param userRole UserRole object that represents the role that the user has.
	 */
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	/**
	 * Method that retrieves the batch graduation date of the user.
	 * @return graduationDate Timestamp of the graduation date for the user
	 */
	
	public Timestamp getGraduationDate() {
		return graduationDate;
	}

	/**
	 * Method that manually sets the graduation date of the User object.
	 * @param graduationDate Reflects the graduation date
	 */
	public void setGraduationDate(Timestamp graduationDate) {
		this.graduationDate = graduationDate;
	}

	/**
	 * Method that retrieves the list of skills of the user
	 */
	public Set<TechnicalSkills> getSkill() {
		return skill;
	}

	/**
	 * Method that manually sets the skills of the user object
	 */
	public void setSkill(Set<TechnicalSkills> skill) {
		this.skill = skill;
	}
	
	/**
	 * Method that retrieves the marketing status of the user
	 */
	public MarketingStatus getMarketingStatus() {
		return marketingStatus;
	}

	/**
	 * Method that manually sets the marketing status of the user object
	 */
	public void setMarketingStatus(MarketingStatus marketingStatus) {
		this.marketingStatus = marketingStatus;
	}

	/**
	 * Method that returns a string representation of the current User object.
	 */

	@Override
	public String toString() {
		return "User [ID=" + ID + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", hashedPassword=" + hashedPassword + ", batchType=" + batchType + ", attendance=" + attendance
				+ ", tasks=" + tasks + ", events=" + events + ", userRole=" + userRole + ", graduationDate="
				+ graduationDate + ", skill=" + skill + ", marketingStatus=" + marketingStatus + ", Projects=" + project +"]";
	}

	/**
	 * A password hashing algorithm used for testing.
	 * @param inputPassword
	 * @return The hashed password
	 */

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