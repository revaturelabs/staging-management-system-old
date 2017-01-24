package com.revature.sms.database;

public class DBInitializationRunner {
	public static void main(String[] args) {
		DBInitializationController dbic = new DBInitializationController();
		dbic.initializeUsers();
		dbic.initializeUserObjects();
	}
	
}
