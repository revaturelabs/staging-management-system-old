package com.revature.sms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

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
	
	
	//This method will not retrieve leap days because I'm lazy
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
		
		String monthString;
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
