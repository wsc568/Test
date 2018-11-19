package com.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	private Connection con;
	private String user = "SCOTT";//数据库用户名，
	private String password = "tiger";//密码
	private String className = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:ORCL";// 我是用本地Tomcat，所以写localhost
	
	/** 创建数据库连接 */
	public Connection getCon() {
		try {
			Class.forName(className);
			con =  DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			con = null;
			e.printStackTrace();
		} 
		return con;
	}

	public void closed(Connection con)  {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				con=null;
			}
		}
	}
}
