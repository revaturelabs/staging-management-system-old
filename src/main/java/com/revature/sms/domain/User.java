package com.revature.sms.domain;

import java.util.List;

import javax.persistence.*;

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
	@Column(name = "HASHED_PASSWORD")
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
	 * UserRole object that keeps track of the user's specific role.
	 */
	@ManyToOne
	@JoinColumn(name = "user_role")
	private UserRole userRole;
/**
 * Null args constructor. Doesn't initialize any of the User instance variables.
 */
	public User() {
		super();
	}

	/**
	 * Constructor for User object. This constructor is specifically designed to
	 * be used for creating a User who is an associate. Initializes all instance variables except for ID, as that is
	 * automatically generated on creation. 
	 * @param username - String that represents the username of the User object.
	 * @param firstName - String that represents the first name of the User object.
	 * @param lastName - String that represents the last name of the User object.
	 * @param hashedPassword - String that represents the hashedPassword of the User object.
	 * @param batchType - BatchType object that represents the specific BatchType that the User belongs to.
	 * @param attendance - List containing AssociateAttendence objects that keeps track of the user's attendance.
	 * @param tasks - List containing AssociateTask objects that keeps track of the user's tasks.
	 * @param userRole - UserRole object that keeps track of the user's specific role.
	 */
	public User(String username, String firstName, String lastName, String hashedPassword, BatchType batchType,
			List<AssociateAttendance> attendance, List<AssociateTask> tasks, UserRole userRole) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hashedPassword = hashedPassword;
		this.batchType = batchType;
		this.attendance = attendance;
		this.tasks = tasks;
		this.userRole = userRole;
	}

	// constructor for non-associate
	/**
	 * Constructor for User object. This constructor is meant to be used to create
	 * users who aren't associates. Initializes the username, firsName, lastName, hashedPassword,
	 * and userRole variables of the user object based on the supplied values.
	 * @param username - String that represents the username of the User object.
	 * @param firstName - String that represents the first name of the User object.
	 * @param lastName - String that represents the last name of the User object.
	 * @param hashedPassword - String that represents the hashedPassword of the User object.
	 * @param userRole - UserRole object that keeps track of the user's specific role.
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
 * @return ID - int value that represents the id of the User.
 */
	public int getID() {
		return ID;
	}
/**
 * Method to manually set the ID value of the current User object.
 * @param iD - int value that represents the id to be set.
 */
	public void setID(int iD) {
		ID = iD;
	}
/**
 * Method to retrieve the username of the User object.
 * @return username - String value that represents the username of the User.
 */
	public String getUsername() {
		
		return username;
	}
/**
 * Method to manually set the username of the current User object.
 * @param username - String value that represents the username to be set.
 */
	public void setUsername(String username) {
		this.username = username;
	}
/**
 * Method to retrieve the first name of the User object.
 * @return firstName - String value that represents the first name of the User.
 */
	public String getFirstName() {
		return firstName;
	}
/**
 * Method to manually set the first name of the current User object.
 * @param firstName - String value that represents the first name to be set.
 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Method to retrieve the last name of the User object.
	 * @return lastName - String value that represents the first name of the User.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Method to manually set the last name of the current User object.
	 * @param lastName - String value that represents the last name to be set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
/**
 * Method to retrieve the hashedPassword of the current User object.
 * @return hashedPassword - String value that represents the hashedPassword to be set.
 */
	public String getHashedPassword() {
		return hashedPassword;
	}
/**
 * Method to manually set the hashedPassword of the User object.
 * @param hashedPassword - String value that represents the hashedPassword to be set.
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
 * @return batchType - BatchType object that represents the specific BatchType that the User 
 * belongs to.
 */
	public BatchType getBatchType() {
		return batchType;
	}
/**
 * Method that sets the BatchType object of the current User object.
 * @param batchType - BatchType object that represents the specific BatchType that the User 
 * belongs to.
 */
	public void setBatchType(BatchType batchType) {
		this.batchType = batchType;
	}
/**
 * Method that retrieves the list of AssociateAttendence objects that represents the User's attendance.
 * @return attendance - List containing all the AssociateAttendence objects that belongs to 
 * the User object.
 */
	public List<AssociateAttendance> getAttendance() {
		return attendance;
	}
/**
 * Method that sets the list of AssociateAttendence objects that represents the User's attendance.
 * @param attendance - List containing all the AssociateAttendence objects that belongs to 
 * the User object.
 */
	public void setAttendance(List<AssociateAttendance> attendance) {
		this.attendance = attendance;
	}
	/**
	 * Method that retrieves the list of AssociateTask objects that represents the User's tasks.
	 * @return tasks - List containing all the AssociateTask objects that belongs to 
	 * the User object.
	 */
	public List<AssociateTask> getTasks() {
		return tasks;
	}
	/**
	 * Method that sets the list of AssociateTasks objects that represents the User's tasks.
	 * @param tasks - List containing all the AssociateTask objects that belongs to 
	 * the User object.
	 */
	public void setTasks(List<AssociateTask> tasks) {
		this.tasks = tasks;
	}
/**
 * Method that retrieves the role of the current user.
 * @return userRole - UserRole object that represents the current user's role.
 */
	public UserRole getUserRole() {
		return userRole;
	}
/**
 * Method that manually sets the role of the User object.
 * @param userRole - UserRole object that represents the role that the user has.
 */
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
/**
 * Method that returns a string representation of the current User object.
 */
	@Override
	public String toString() {
		return "User [ID=" + ID + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", hashedPassword=" + hashedPassword + ", batchType=" + batchType + ", attendance=" + attendance
				+ ", tasks=" + tasks + ", userRole=" + userRole + "]";
	}

}
