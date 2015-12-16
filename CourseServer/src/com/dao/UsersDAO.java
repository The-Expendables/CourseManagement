package com.dao;

import java.awt.List;

import com.tb.Tb_teacher;
import com.util.JdbcUtil;

public class UsersDAO extends MyDAO{	
	public static String login(int type,String username,String password){
		init();
		String pwd=null;
		String name=null;
		String fail="false";
		try{
			
			switch(type){
			case 1:
				sqlCommand="select * from 教师信息表  where 工号=?;";
				break;
			case 2:
				sqlCommand="select * from 系负责人信息表  where 工号=?;";
				break;
			case 3:
				sqlCommand="select * from 教学办   where 工号=?;";
				break;
			}
			conn=JdbcUtil.getConnection();
			pst=conn.prepareStatement(sqlCommand);
//			pst.setString(1, tb_name);
			pst.setString(1, username);
			rs=pst.executeQuery();
			while(rs.next()){
				pwd=rs.getString("密码");
				name=rs.getString("姓名");
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
		if(pwd==null||!pwd.equals(password)) return fail;
		else return name;
	}
}
