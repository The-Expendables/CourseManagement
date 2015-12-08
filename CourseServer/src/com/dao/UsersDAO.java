package com.dao;

import com.util.JdbcUtil;

public class UsersDAO extends MyDAO{	
//	private static JdbcUtil jdbcutil=new JdbcUtil();
	public static boolean login(int type,String username,String password){
		init();
		String pwd=null;
		try{
			conn=JdbcUtil.getConnection();
			sqlCommand="select * from 教师   where 用户名=?;";
			pst=conn.prepareStatement(sqlCommand);
			pst.setString(1, username);
			rs=pst.executeQuery();
			while(rs.next()){
				pwd=rs.getString("用户密码");
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
		if(pwd==null||!pwd.equals(password)) return false;
		else return true;
	}
}
