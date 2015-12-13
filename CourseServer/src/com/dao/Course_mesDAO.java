package com.dao;

import com.tb.Tb_course_mes;
import com.util.GV;
import com.util.JdbcUtil;

public class Course_mesDAO extends MyDAO {
	public static int insert(Tb_course_mes tb_course_mes){
		init();
		int ret=0;
		//把excel表名换成数据库表头
		GV gv=new GV();
		tb_course_mes.setTable_name(gv.real_table_name.get(tb_course_mes.getTable_name()));
		try{
			sqlCommand="insert into 发布表"+
					" values(?,?,?,?)";
			conn=JdbcUtil.getConnection();
			pst=conn.prepareStatement(sqlCommand);
			pst.setString(1, tb_course_mes.getTable_name());
			pst.setString(2, tb_course_mes.getTerm());
			pst.setString(3, tb_course_mes.getTeacher_time());
			pst.setString(4, tb_course_mes.getDepartment_time());
			ret=pst.executeUpdate();
			
			pst.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return ret;
	}
}
