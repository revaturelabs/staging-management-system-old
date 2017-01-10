package com.revature.sms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

	// Defunct hashPassword script. While used to create initial hashed passwords here, actual hashing will take place on the client-side javascript
		public static String hashPassword(String inputPassword) {
			try {
				MessageDigest md;
				md = MessageDigest.getInstance("SHA");
				md.update(inputPassword.getBytes());
				byte byteData[] = md.digest();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < byteData.length; i++) {
					sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
				}
				return sb.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return null;
			}
		}

}
