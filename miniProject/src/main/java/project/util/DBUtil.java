package project.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "C##PROJECT";
	private static final String PASSWORD = "PROJECT";
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
