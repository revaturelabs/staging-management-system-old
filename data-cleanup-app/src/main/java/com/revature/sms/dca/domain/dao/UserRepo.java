package com.revature.sms.dca.domain.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.dca.domain.BatchType;
import com.revature.sms.dca.domain.User;
/**
 * DAO repo for User objects.
 *
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	/**
	 * Method to retrieve User object by supplied username.
	 * @param username String value that contains the username of the user.
	 * @return User object matching supplied username.
	 */
	User findByUsername(String username);

/**
 * Method to retrieve list of Users matching a specific BatchType.
 * @param batchType String value of the BatchType used to find users.
 * @return object containing users of a specific batchType.
 */
	List<User> findByBatchType(BatchType batchType);
/**
 * Method to retrieve list of users by firstName.
 * @param firstName String representing the first name of users in the list.
 * @return object containing all users with the supplied first name.
 */

	List<User> findByFirstName(String firstName);
	
	
	/**
	 * Method to delete a user with a specific username.
	 * @param username String value containing the username of the user to be deleted.
	 * @return Long value of the id of the user that was deleted. Returns 0 if the user
	 * with the username doesn't exist in the database.
	 */
	@Transactional
    Long deleteByUsername(String username);
}
