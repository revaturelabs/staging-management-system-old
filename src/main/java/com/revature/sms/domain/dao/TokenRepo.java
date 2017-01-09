package com.revature.sms.domain.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.Token;

@Repository
public interface TokenRepo extends JpaRepository<Token, Integer>{
	Token findByauthToken(String token);
}
