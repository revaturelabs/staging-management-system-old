package com.revature.sms.snas;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

	@Transactional
	public static void main(String[] args) {
		SpringApplication.run(SlackNotificationAppSystemApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(AssociateAttendanceRepo aar, UserRepo ur, UserRoleRepo urr) {
		return (args) -> {
			ZonedDateTime rightNow = ZonedDateTime.now();
			ZonedDateTime beginningOfDay = rightNow.minusHours(rightNow.getHour()).minusMinutes(rightNow.getMinute()).minusSeconds(rightNow.getSecond()).minusNanos(rightNow.getNano()); //get midnight of today
			ZonedDateTime endOfDay = beginningOfDay.plusDays(1).minusNanos(1); //get the last possible second of today
			
			List<User> allAssociates = ur.findByUserRole(urr.findByName("associate"));
			List<User> associatesToRemindAbout = new ArrayList<>();
			
			
			for (User associate : allAssociates) {
				Boolean attendanceObjectExists = false;
				for (AssociateAttendance aa : associate.getAttendance()) {
					ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.parse(aa.getDate().toString().replace(" ", "T")), ZoneId.of("UTC")); //get the timestamp of the attendance object in UTC time
					if ( ( zdt.isAfter(beginningOfDay) && zdt.isBefore(endOfDay) ) || zdt.isEqual(beginningOfDay) || zdt.isEqual(endOfDay)) {
						attendanceObjectExists = true;
						if (!aa.isVerified()) {
							associatesToRemindAbout.add(associate);
						}
						break;
					}
				}
				if (!attendanceObjectExists) {
					associatesToRemindAbout.add(associate);
				}
			}

			
			if (associatesToRemindAbout.size() > 0) { //if there are actually any associates to talk about
				String text = "Please verify attendance for the following users:\n";
				for (User associate : associatesToRemindAbout) {
					text += associate.getFirstName()+" "+associate.getLastName()+"\n";
				}
				text += "\nThanks!";
				text = text.replaceAll("'", "SINGLEQUOTE");  //identify all single quotes in a name
				SlackBot bot = new SlackBot();
				String message = "{'username': 'SMS Bot', 'icon_emoji': ':exclamation:','attachments': [{'fallback': 'Required plain-text summary of the attachment.','color': '#36a64f ','title': 'RevaturePro','title_link': 'http://revature.pro','text': '" + text + "'}]}";
				message= message.replaceAll("'", "\"");   //convert single quotes to double quotes to correctly escape single quotes in name
				message=message.replaceAll("SINGLEQUOTE", "\'");  //correctly escape single quotes in names
				bot.sendMessage(message);
				}

		};
	}
}
