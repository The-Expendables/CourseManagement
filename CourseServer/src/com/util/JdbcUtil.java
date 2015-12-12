package com.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;




public class JdbcUtil {
	public static Connection getConnection() throws SQLException,IOException{
		Properties props=new Properties();
		String fileName="DBConfig.properties";
		InputStream in =Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		if(in==null) System.out.println("in is null!");
		props.load(in);
		in.close();
		
//		System.out.println(JdbcUtil.class.getResource("/").toString());
//		System.out.println(Thread.currentThread().getContextClassLoader().toString());
//		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").toString());
		
		String drivers=props.getProperty("jdbc.drivers");
		if(drivers==null) System.out.println("is null");
		if(drivers!=null)
			System.setProperty("jdbc.drivers", drivers);
		String url=props.getProperty("jdbc.url");
		String username=props.getProperty("jdbc.username");
		String password=props.getProperty("jdbc.password");
		System.out.println("DB.properties works!");
		return DriverManager.getConnection(url,username,password);
	}
}
