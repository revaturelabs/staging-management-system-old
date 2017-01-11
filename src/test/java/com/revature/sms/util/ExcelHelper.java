package com.revature.sms.util;

//Manage these connections at C:\Windows\SysWOW64\odbcad32.exe
//import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

import java.lang.reflect.Method;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelHelper {
	static Fillo fillo;
	static Connection conn; 
    static Recordset rs;
  
    public static String getValue(String file, String sheet, String key, String valueColumn, String keyColumn) {
		String value = "";
		try {
			fillo = new Fillo();
			conn = fillo.getConnection(file);
			String query = "SELECT "+valueColumn+" FROM "+sheet+" WHERE "+keyColumn+"='"+key+"'";
			rs = conn.executeQuery(query);
			while (rs.next()) {
				value = rs.getField(valueColumn);
			}
        } catch (Exception e) {
          e.printStackTrace();
      }
		return value;
  
  
  }
  
}