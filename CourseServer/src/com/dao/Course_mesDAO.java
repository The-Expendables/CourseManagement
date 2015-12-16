package com.dao;

import com.tb.Tb_course_mes;
import com.tb.Tb_teacher;
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
	
	public static int update(Tb_course_mes tb_course_mes) {
		init();
		int ret = 0;
		String table_name="发布表";
		try{
			sqlCommand = "update "+table_name+
					" set 教师报课截止时间=?,系负责人审核截止时间=? where 课程表名=? and 学期=?;";
			conn = JdbcUtil.getConnection();
			pst = conn.prepareStatement(sqlCommand);
					
			pst.setString(1, tb_course_mes.getTeacher_time());
			pst.setString(2, tb_course_mes.getDepartment_time());
			pst.setString(3, tb_course_mes.getTable_name());
			pst.setString(4, tb_course_mes.getTerm());
			
			ret = pst.executeUpdate();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return ret;
	}
}
