package com.revature.sms.database;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.revature.sms.database")
public class DBInitializationRunner {
	
	public static void main(String[] args) {
		//ApplicationContext context = new AnnotationConfigApplicationContext(DBInitializationRunner.class);
		//DBInitializationRunner runner = context.getBean(DBInitializationRunner.class);
		//runner.start();
	}
	
	
	@Autowired
	private static DBInitializationController controller;
	
	@SuppressWarnings("unused")
	private void start() {
		controller.initializeUsers();
		controller.initializeUserObjects();
	}
	
}
