package com.dao;

import com.tb.Tb_course;
import com.tb.Tb_teacher;
import com.tb.Tb_teacher_declare;
import com.util.JdbcUtil;

public class TeacherDAO extends MyDAO {
	public static int insert(Tb_teacher tb_teacher) {
		init();
		int ret = 0;
		try {
			sqlCommand = "insert into 教师信息表" + " values(?,?,?,?,?,?,?,?)";
			conn = JdbcUtil.getConnection();
			pst = conn.prepareStatement(sqlCommand);
			
			pst.setString(1, tb_teacher.getId());
			pst.setString(2, tb_teacher.getPassword());
			pst.setString(3, tb_teacher.getName());
			pst.setString(4, tb_teacher.getDepartment());
			pst.setString(5, tb_teacher.getSex());
			pst.setString(6, tb_teacher.getBirth());
			pst.setString(7, tb_teacher.getEmail());
			pst.setString(8, tb_teacher.getPhone());
			
			ret = pst.executeUpdate();

			pst.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return ret;
	}
	
	public static int update(Tb_teacher tb_teacher) {
		init();
		int ret = 0;
		String table_name="教师信息表";
		try{
			sqlCommand = "update "+table_name+
					" set 密码=?,姓名=?,所属系=?,性别=?,出生年月=?,邮箱=?,手机号码=? where 工号=?;";
			conn = JdbcUtil.getConnection();
			pst = conn.prepareStatement(sqlCommand);
					
			pst.setString(1, tb_teacher.getPassword());
			pst.setString(2, tb_teacher.getName());
			pst.setString(3, tb_teacher.getDepartment());
			pst.setString(4, tb_teacher.getSex());
			pst.setString(5, tb_teacher.getBirth());
			pst.setString(6, tb_teacher.getEmail());
			pst.setString(7, tb_teacher.getPhone());
			
			pst.setString(8, tb_teacher.getId());
			ret = pst.executeUpdate();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return ret;
	}
}
