package com.example.demo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	private Connection connection;
	
	public ConnectDB() {
		// TODO Auto-generated constructor stub
		String url = "jdbc:mysql://localhost:3306/mqtt";
		String user = "root";
		String pass = "10012002";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pass);
			System.out.println("connect sucessfull");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
//	public static void main(String[] args) {
//		ConnectDB conn = new ConnectDB();
//	}
}
