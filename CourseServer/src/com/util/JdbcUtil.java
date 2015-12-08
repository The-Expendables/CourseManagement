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
	/*
	public static void main(String args[]){
		try{
			String sqlCommand;
			ResultSet rs;
			try{
				Connection con=JdbcUtil.getConnection();
//				System.out.println(con.getCatalog());
				try(Statement sta=(Statement)con.createStatement()){
					//修改：
					sqlCommand="update users set password='00000' where username='happy';";
					sta.executeUpdate(sqlCommand);
					
					//添加：
					sqlCommand="insert into users(username,password)"
							+"values('happy','123456')";
					sta.execute(sqlCommand);
					
					//删除
					sqlCommand="delete from users where id=1;";
					sta.executeUpdate(sqlCommand);
					//查询
					sqlCommand="select * from users";
					rs=sta.executeQuery(sqlCommand);
					rs.next();
					for(int i=0;i<rs.getRow();i++){
						System.out.println(rs.getString("username")+":"+rs.getString("password"));
						rs.next();
					}
					
				}catch(Exception e){
					System.out.println(e.toString());
				}
				con.close();
			}catch(Exception e){
				System.out.println(e.toString());
				
			}
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	*/
	public static Connection getConnection() throws SQLException,IOException{
		Properties props=new Properties();
		String fileName="DB.properties";
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
