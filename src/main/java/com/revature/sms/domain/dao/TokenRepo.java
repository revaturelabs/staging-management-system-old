package com.revature.sms.domain.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.Token;
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
	Token findByauthToken(String token);
}
