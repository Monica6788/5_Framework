package com.kh.spring.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	// 오라클 DB 접속정보
	// 접속 URL = jdbc:oracle:thin:@호스트:포트넘버:SID형식
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "C##JDBC";
	private static final String PASSWORD = "JDBC";
	
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			// 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// Connection 객체 생성
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
