package com.cx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtils {


public static Connection dbOpen() throws SQLException {
		
		String url="jdbc:mysql://127.0.0.1:3306/banksvs?serverTimezone=GMT";
		String username ="root";
		String passWord ="123";
		Connection conn= null;
		
			
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					System.out.println("连接成功");
					conn= DriverManager.getConnection(url,username,passWord);
					return conn;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				     }
				return null;
			
                }	    
	
public static  void dbClose(Connection conn, Statement stmt, ResultSet rs) {
	if(conn!=null) {
		try { 
			conn.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	if(stmt !=null) {
		try {
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	if(rs!=null) {
		try {
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
  }
}
