package com.revature.sms.testlibs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.sms.domain.Token;
import com.revature.sms.domain.User;
import com.revature.sms.domain.dao.TokenRepo;

/**
 * 
 * @author Sage
 *
 * 
 * class TokenDataManager is used to create new authorization tokens and delete them after
 * the tests are executed. It's essential that all setup methods record any changes made to
 * the database so that cleanup methods can be called to undo those changes.  
 * 
 */

public class TokenDataManager {

	List<Token> createdTokens = new ArrayList<>();
	
	@Autowired
	TokenRepo tr;
	
	
	/**
	 * createTestToken is a setup method that is called to create an authorization token for testing.
	 * The cleanup method RemoveAllTestTokens must be called after an instance of this class is done being used.
	 * 
	 * @return The token object that is created in the database
	 */
	
	public Token createTestToken(User user){
		Token newToken = new Token(user);
		tr.save(newToken);
		createdTokens.add(newToken);
		return newToken;
	}
	
	
	
	
	
	
	/**
	 * A cleanup method that must be called when an instance of the class is done being used.
	 */
	
	public void removeAllTestTokens(){
		for(Token i:createdTokens){
			tr.delete(i);
		}
		createdTokens.clear();
	}
	
}
