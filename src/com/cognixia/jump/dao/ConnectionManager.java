package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private static Connection connection = null;
	
	private static final String URL = "jdbc:mySQL://localhost:3306/bank";
	
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";
	
	private ConnectionManager() {
		
	}
	
	private static void makeConnection() {
		
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Connection made to " + URL + " as user: "+ USERNAME);
			
		} catch(SQLException e) {
			System.out.println("Connection failed. Check stack trace\n" + e.getStackTrace());
		}
	}
	
	
	
	public static Connection getConnection() {
		if(connection == null) {
			makeConnection();
		}
		return connection;
	}
}