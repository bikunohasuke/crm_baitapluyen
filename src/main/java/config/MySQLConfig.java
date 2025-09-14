package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConfig {
	
	public static Connection getConnection() {
		Connection connection = null;
		
		try {
			String url = "jdbc:mysql://localhost:3307/crm_app";
			String username = "root";
			String password = "admin123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Connection error: " + e.getMessage());
		}
		
		return connection;
	}

}
