package com.revature.sms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.User;

public class Utils {

	//Allows passwords to be hashed for testing purposes
	public static String hashPassword(String inputPassword) {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA");
			md.update(inputPassword.getBytes());
			byte byteData[] = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			Logger.getRootLogger().debug("Your attempt to hash a password failed", e);
			return null;
		}
		
	}
	
	//Because Timestamps are a pain to initialize
	public static Timestamp convertDateStringToTimestamp(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date;
		long millis;
		Timestamp ts;
		try {
			date = sdf.parse(dateString);
			millis = date.getTime();
			ts = new Timestamp(millis);
			return ts;
		} catch (ParseException e) {
			Logger.getRootLogger().debug("You got a ParseException", e);
		}
		return null;
	}
	
	
	public static String convertTimestampToSimpleDate(Timestamp ts) {
		String datetime = ts.toString();
		String[] splitDatetime = datetime.split(" ");
		String date = splitDatetime[0];
		String[] splitDate = date.split("-");
		int month = Integer.parseInt(splitDate[1]);
		int day = Integer.parseInt(splitDate[2]);
		String simpleDate = month+"/"+day;
		return simpleDate;
	}
	
	
	//Takes a Timestamp (from SQL), makes it a string, isolates the  components of this string, and uses the
	//components to create a new LocalDate object (from the Java 8 time package).
	public static LocalDate convertTimestampToLocalDate(Timestamp ts) {
		String datetime = ts.toString();
		String[] splitDatetime = datetime.split(" ");
		String date = splitDatetime[0];
		String[] splitDate = date.split("-");
		int year = Integer.parseInt(splitDate[0]);
		int month = Integer.parseInt(splitDate[1]);
		int day = Integer.parseInt(splitDate[2]);
		LocalDate dateObject = LocalDate.of(year, month, day);
		return dateObject;
	}
	
	
	//This method does not retrieve leap days because I'm lazy
	public static String getDateFromText(String text) {
		String pattern = "\\d{1,2}[-/]\\d{1,2}";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(text);
		String dateString = null;
		if (m.find()) {
			dateString = m.group();
		}
		
		String[] split;
		if (dateString.contains("-")) {
			split = dateString.split("-");
		} else {
			split = dateString.split("/");
		}
		
		int month = Integer.parseInt(split[0]);
		int day = Integer.parseInt(split[1]);
		boolean properDate = true;
		
		if (month<0||day<0) {
			properDate = false;
		}
		
        switch (month) {
            case 1:  
            	if (day>31) {
            		properDate = false;
            	}
                break;
            case 2:  
            	if (day>28) {
            		properDate = false;
            	}
                break;
            case 3:  
            	if (day>31) {
            		properDate = false;
            	}
                break;
            case 4:  
            	if (day>30) {
            		properDate = false;
            	}
                break;
            case 5:  
            	if (day>31) {
            		properDate = false;
            	}
                break;
            case 6:  
            	if (day>30) {
            		properDate = false;
            	}
                break;
            case 7:  
            	if (day>31) {
            		properDate = false;
            	}
                break;
            case 8:  
            	if (day>31) {
            		properDate = false;
            	}
                break;
            case 9:  
            	if (day>30) {
            		properDate = false;
            	}
                break;
            case 10: 
            	if (day>31) {
            		properDate = false;
            	}
                break;
            case 11: 
            	if (day>30) {
            		properDate = false;
            	}
                break;
            case 12: 
            	if (day>31) {
            		properDate = false;
            	}
                break;
            default: 
            	properDate = false;
                break;
        }
		
		if (!properDate) {
			dateString = null;
		}
		
		return dateString;
	}
	
	
	public static HashMap<MonthDay, String> getExpectedAttendanceStatuses(User user) {
		List<AssociateAttendance> attendanceList = user.getAttendance();
		HashMap<MonthDay, String> expectedStatuses = new HashMap<MonthDay, String>();  //Database data
		
		//Goes through the User's list of attendance objects and gathers information from them in a way that allows
		//it to be compared to the website.
		for (AssociateAttendance a : attendanceList) {
			Timestamp ts = a.getDate();
			String fullTime = ts.toString();
			String monthDay = fullTime.substring(5, 10);
			String formattedMonthDay = "--" + monthDay;
			MonthDay md = MonthDay.parse(formattedMonthDay);

			String status;
			boolean ci = a.isCheckedIn();
			boolean v = a.isVerified();
			//Depending on whether an associate is checked in and verified, a certain icon should be displayed, 
			//and each icon is associated with a different string in the html.
			if (v) { 
				status = "done_all";
			} else if (ci && !v) {
				status = "done";
			} else if (!ci && !v) {
				status = "close";
			} else {
				status = "???";
			}
			expectedStatuses.put(md, status);
		}
		return expectedStatuses;
	}
	
	
	
	//Allows a thread to sleep for the given number of milliseconds.
	public static void attemptWait(int m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			Logger.getRootLogger().debug(e);
			Thread.currentThread().interrupt();
		}
	}
	

}
