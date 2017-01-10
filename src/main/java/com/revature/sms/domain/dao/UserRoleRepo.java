package com.revature.sms.domain.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.UserRole;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
	UserRole findByName(String name);
	
	@Transactional
    Long deleteByName(String name);
}
