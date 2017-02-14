package com.revature.sms.snas.domain.dao;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.sms.snas.domain.Token;
/**
 * DAO repo for Token objects
 *
 */
@Repository
public interface TokenRepo extends JpaRepository<Token, Integer>{
	/**
	 * Method that retrieves Token by supplied String for authToken.
	 * @param token String containing token value.
	 * @return Token object matching supplied String.
	 */
	Token findByAuthToken(String token);
	
}
