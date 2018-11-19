package com.Util;

import java.sql.Connection;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DbUtil jdbc = new DbUtil();
		Connection con = jdbc.getCon();
		jdbc.closed(con);
	}
}
