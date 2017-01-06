package com.revature.sms.domain.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	User findByEmail(String email);
	
	List<User> findByBatchType(String batchType);
	
	@Transactional
    Long deleteByEmail(String email);
}
