package com.revature.sms.dca;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.revature.sms.dca.domain.AssociateAttendance;
import com.revature.sms.dca.domain.Token;
import com.revature.sms.dca.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.dca.domain.dao.TokenRepo;

@SpringBootApplication
public class DataCleanupAppApplication {

	//Using a command line runner. Idea here: https://spring.io/guides/gs/accessing-data-jpa/ 
	
	public static void main(String[] args) {
		SpringApplication.run(DataCleanupAppApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(AssociateAttendanceRepo aar, TokenRepo tr) {
		return (args) -> {
			List<Integer> idsToDelete = new ArrayList<Integer>();
			
			List<AssociateAttendance> allAttendance = aar.findAll();
			
			for (AssociateAttendance a : allAttendance) {
				LocalDateTime dateTime = LocalDateTime.parse(a.getDate().toString().replace(" ", "T")); //Convert the SQL Timestamp to LocalDateTime for easier manipulation
				boolean isAfterExpiration = dateTime.isBefore(LocalDateTime.now().minusMonths(1)); //this is our condition for if an object is to be deleted
				if(isAfterExpiration){
					idsToDelete.add(a.getID());
				}
			}
			for (Integer i : idsToDelete) {
				aar.delete(i);
			}
			
			List<String> tokensToDelete = new ArrayList<String>();
			
			List<Token> allTokens = tr.findAll();
			
			for(Token t : allTokens){
				LocalDateTime dateTime = LocalDateTime.parse(t.getMostRecentAccess().toString().replace(" ", "T")); //Convert the SQL Timestamp to LocalDateTime for easier manipulation
				boolean isAfterExpiration = dateTime.isBefore(LocalDateTime.now().minusMonths(1)); //this is our condition for if an object is to be deleted
				if(isAfterExpiration){
					tokensToDelete.add(t.getAuthToken());
				}
			}
			for (String s : tokensToDelete) {
				tr.delete(tr.findByAuthToken(s));
			}
			
		};
		
	}

	
}
