package com.revature.sms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class Utils {

	// Defunct hashPassword script. While used to create initial hashed passwords here, actual hashing will take place on the client-side javascript
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

}
