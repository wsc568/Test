package com.Servlet;

import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

public class test {
	public static void main(String[] args) {
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
		// 设置日期格式
		// System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

		/*
		 * java.sql.Date d = new java.sql.Date(0); java.util.Date date = new
		 * Date(); System.out.println(java.sql.Date()); //d.valueOf();
		 * System.out.println(d.toGMTString());
		 */

		//System.out.println(java.sql.Date.Timestamp(date.getTime()));
		
		 /* java.util.Date date=new java.util.Date(); java.sql.Date data1=new
		  java.sql.Date(date.getTime()); System.out.println(data1);
		 */

		
		/* Date time= new java.sql.Date(new java.util.Date().getTime());
		  System.out.println(time);*/
		 
		java.util.Date a = new java.util.Date();
		java.sql.Timestamp d = new java.sql.Timestamp(a.getTime());
		System.out.println(d.toString());
		System.out.println(d.toString().subSequence(0, 19));
		System.out.println(d.toString().length());

	}
}