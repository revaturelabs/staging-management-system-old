package com.revature.sms.snas;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SlackBot {

	private final static String WEBHOOK_URL = "https://hooks.slack.com/services/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";
	private final static String USER_AGENT = "Mozilla/5.0";

	public void sendMessage(String message) {
		try {
			URL obj = new URL(WEBHOOK_URL);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(message);
			wr.flush();
			wr.close();

			//int responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			/*String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}*/
			in.close();
		} catch (Exception ex) {

		}
	}
}
