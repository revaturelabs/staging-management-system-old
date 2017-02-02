package com.revature.sms.snas;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import com.revature.sms.snas.domain.AssociateAttendance;
import com.revature.sms.snas.domain.User;
import com.revature.sms.snas.domain.dao.AssociateAttendanceRepo;
import com.revature.sms.snas.domain.dao.UserRepo;
import com.revature.sms.snas.domain.dao.UserRoleRepo;

@Transactional
@SpringBootApplication
public class SlackNotificationAppSystemApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SlackNotificationAppSystemApplication.class, args);
	}
	
	
	@Bean
	public CommandLineRunner demo(AssociateAttendanceRepo aar, UserRepo ur, UserRoleRepo urr) {
		return (args) -> { 
			Date now = new Date();
			List<User> allAssociates = ur.findByUserRole(urr.findByName("associate"));
			List<User> associatesToRemindAbout = new ArrayList<>();
			for (User associate : allAssociates) {
				associate.getAttendance().size();
				Boolean attendanceObjectExists = false;
				for(AssociateAttendance aa : associate.getAttendance()){
					Date thisDate = new Date(aa.getDate().getTime());
					if((thisDate.getDate() == now.getDate()) && (thisDate.getMonth() == now.getMonth())){
						attendanceObjectExists = true;
						if(!aa.isVerified()){
							associatesToRemindAbout.add(associate);
						}
						break;
					}
				}
			}
			
			//temporary code to test the text
			associatesToRemindAbout.add(ur.findByUsername("java"));
			
			if(associatesToRemindAbout.size()>0){
				String text = "Please verify attendance for the following users:\n";
				for (User associate : associatesToRemindAbout) {
					text += associate.getFirstName()+" "+associate.getLastName()+"\n";
				}
				text += "\nThanks! \n Love, \n SlackBot";
				SlackBot bot = new SlackBot();
				String message = "{'text':'"+text+"', 'icon_emoji':':ghost:', 'username':'ghost_bot'}";
				bot.sendMessage(message);
			}
			

		};
	}
}
