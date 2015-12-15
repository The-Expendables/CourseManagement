package com.dao;

import java.util.List;

import com.tb.Tb_teacher_declare;
import com.util.JdbcUtil;

public class Teacher_declareDAO extends MyDAO {
	public static int insert(Tb_teacher_declare tb_teacher_declare) {
		init();
		int ret = 0;
		try {
			sqlCommand = "insert into 教师报课信息表" + " values(?,?,?,?,?,?,?)";
			conn = JdbcUtil.getConnection();
			pst = conn.prepareStatement(sqlCommand);
			
			pst.setString(1, tb_teacher_declare.getTable_name());
			pst.setString(2, tb_teacher_declare.getCourse_name());
			pst.setString(3, tb_teacher_declare.getGrade());
			pst.setString(4, tb_teacher_declare.getId());
			pst.setString(5, tb_teacher_declare.getT_name());
			pst.setString(6, tb_teacher_declare.getBe_weeks());
			pst.setString(7, tb_teacher_declare.getRemark());
			ret = pst.executeUpdate();

			pst.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return ret;
	}
	
	public static int delete(Tb_teacher_declare tb_teacher_declare) {
		init();
		int ret = 0;
		try {
			sqlCommand = "delete from 教师报课信息表 where 课程表名=? and 课程名称=? and 年级=? and 任课教师=?";
			conn = JdbcUtil.getConnection();
			pst = conn.prepareStatement(sqlCommand);
			
			pst.setString(1, tb_teacher_declare.getTable_name());
			pst.setString(2, tb_teacher_declare.getCourse_name());
			pst.setString(3, tb_teacher_declare.getGrade());
			pst.setString(4, tb_teacher_declare.getT_name());
			
			ret = pst.executeUpdate();

			pst.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return ret;
	}
	
	public static Boolean search(Tb_teacher_declare tb_teacher_declare) {
		init();
		String T_name=null;
		try {
			sqlCommand="select * from 教师报课信息表  where 课程表名=? and 课程名称=? and 年级=? and 工号=?;";
			conn = JdbcUtil.getConnection();
			pst = conn.prepareStatement(sqlCommand);
			
			pst.setString(1, tb_teacher_declare.getTable_name());
			pst.setString(2, tb_teacher_declare.getCourse_name());
			pst.setString(3, tb_teacher_declare.getGrade());
			pst.setString(4, tb_teacher_declare.getId());
			
			rs=pst.executeQuery();
			while(rs.next()){
				T_name=rs.getString("任课教师");
			}

			pst.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		if(T_name==null) return false;
		else return true;
	}
}
