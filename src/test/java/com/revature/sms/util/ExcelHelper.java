package com.revature.sms.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

//This class can be used by tests to access excel spreadsheets that hold test data to insert into a database
public class ExcelHelper {
    
	private final String file = "src/test/resources/ExcelSheets/database_input.xlsx";
	private String sheet;
	private Fillo fillo;
	private Connection conn;
	
	public ExcelHelper(String sheet) throws FilloException {
		try {
			fillo = new Fillo();
			conn = fillo.getConnection(file);
			String query = "SELECT * FROM "+sheet;
			conn.executeQuery(query);
		} finally {
			conn.close();
		}
		this.sheet = sheet;
	}
	
    public ArrayList<String> getValues(String key) {
    	
		try {
			fillo = new Fillo();
			conn = fillo.getConnection(file);
			ArrayList<String> values = new ArrayList<String>();
			
			//Finds the row
			String query = "SELECT * FROM "+sheet+" WHERE "+"KeyColumn='"+key+"'";
			Recordset rs = conn.executeQuery(query);
			
			//Looks through columns in the row in order until an cell with 'STOP' is found. Row 1 in the excel
			//sheet must include column numbers for this to work.
			while (rs.next()) {
				boolean flag = true;
				int i  = 1;
				while (flag) {
					String value = rs.getField(String.valueOf(i));
					if (!"STOP".equals(value)) {
						values.add(value);
						i++;
					} else {
						flag = false;
					}
				}
			}			
			return values;
        } catch (FilloException e) {
        	System.out.println("You got a FilloException");
        	System.out.println(e.getMessage());
        	System.out.println("");
        	Logger.getRootLogger().debug("You got a FilloException", e);
        } finally {
        	conn.close();
        }
		return null;
    }
  
}