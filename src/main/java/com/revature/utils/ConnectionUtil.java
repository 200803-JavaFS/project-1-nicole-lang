package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
public static Connection getConnection() throws SQLException {
		
		//For compatibility with other technologies/frameworks will need to register our Driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:postgresql://javafs200803.czwrnxk0mgv6.us-east-2.rds.amazonaws.com:5432/delta_ers";
		String username = "postgres";
		String password = "password"; 
		
		return DriverManager.getConnection(url, username, password);
	}

}
