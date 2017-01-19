package com.revature.sms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		public static Timestamp convertDate(String dateString) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date = null;
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				Logger.getRootLogger().debug("You got a ParseException", e);
			}
			
			long millis = date.getTime();
			Timestamp ts = new Timestamp(millis);
			return ts;
		}
		
		
		/*
		public static void enterDateListIntoListOfDateLists(ArrayList<ArrayList<String>> listOfDateLists, ArrayList<String> dateList) {
			boolean noEmptyDates = true;
			for (String date:dateList) {
				if (date.equals("")) {
					noEmptyDates = false;
				}
			}
			if (noEmptyDates) {
				listOfDateLists.add(dateList);
			}
		}
		*/

}
