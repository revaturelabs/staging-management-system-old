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
	public static Timestamp convertDateToTimestamp(String dateString) {
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
	
	
	@SuppressWarnings("rawtypes")
	public static void printMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
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
