package com.revature.sms.snas;

import java.sql.Time;
import java.sql.Timestamp;
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
			Timestamp ts = new Timestamp(now.getTime());
			List<User> allAssociates = ur.findByUserRole(urr.findByName("associate"));
			List<User> associatesToRemindAbout = new ArrayList<>();
			for (User associate : allAssociates) {
				Boolean attendanceObjectExists = false;
				for (AssociateAttendance aa : aar.findByDate(ts)) {
					if ((aa.getDate().getDate() == now.getDate()) && (aa.getDate().getMonth() == now.getMonth())) {
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
