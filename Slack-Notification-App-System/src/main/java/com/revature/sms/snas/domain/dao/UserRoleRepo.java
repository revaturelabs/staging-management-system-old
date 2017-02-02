package com.revature.sms.snas.domain.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.snas.domain.UserRole;
/**
 * DAO Repo for UserRole
 *
 */
@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
	/**
	 * Method to find a UserRole by the supplied String containing the user role's name.
	 * @param name String that contains value of the UserRole name to find
	 * @return UserRole object matching the supplied string.
	 */
	UserRole findByName(String name);
	
	/**
	 * Method to delete a UserRole based on the supplied name.
	 * @param name String containing name of UserRole to be deleted.
	 * @return int id of UserRole that was deleted. Returns 0 if the userRole doesn't exist.
	 */
	@Transactional
    Long deleteByName(String name);
}
