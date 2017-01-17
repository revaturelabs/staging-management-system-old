package com.revature.sms.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

//This class can be used by tests to access excel spreadsheets that hold test data to insert into a database
public class ExcelHelper {
    
	private final String file = "C:/Users/User/Desktop/SMS_Project/staging-management-system/src/test/resources/database_input.xlsx";
	private final String sheet = "Sheet1";
	private int columns;
	
	public ExcelHelper(int columns) {
		this.columns = columns;
	}
	
    public ArrayList<String> getValues(String key) {
    	
		try {
			Fillo fillo = new Fillo();
			Connection conn = fillo.getConnection(file);
			ArrayList<String> values = new ArrayList<String>();
			
			//Finds the row
			String query = "SELECT * FROM "+sheet+" WHERE "+"KeyColumn='"+key+"'";
			Recordset rs = conn.executeQuery(query);
			
			//Finds the column
			while (rs.next()) {
				int i = 1;
				while (i<=columns) {
					values.add(rs.getField(String.valueOf(i)));
					i++;
				}
			}
			
			return values;
        } catch (FilloException e) {
        	Logger.getRootLogger().debug("You got a FilloException", e);
        }
		return null;
    }
  
}