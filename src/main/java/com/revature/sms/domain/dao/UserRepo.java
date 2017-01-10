package com.revature.sms.domain.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.BatchType;
import com.revature.sms.domain.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	User findByUsername(String username);
	
	List<User> findByBatchType(BatchType batchType);
	
	List<User> findByFirstName(String firstName);
	
	
	
	@Transactional
    Long deleteByUsername(String username);
}
