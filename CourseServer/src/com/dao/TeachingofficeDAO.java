package com.dao;

import com.tb.Tb_teacher;
import com.tb.Tb_teachingoffice;
import com.util.JdbcUtil;

public class TeachingofficeDAO extends MyDAO{
	public static int insert(Tb_teachingoffice tb_teachingoffice) {
		init();
		int ret = 0;
		try {
			sqlCommand = "insert into 教学办" + " values(?,?,?,?)";
			conn = JdbcUtil.getConnection();
			pst = conn.prepareStatement(sqlCommand);
			
			pst.setString(1, tb_teachingoffice.getId());
			pst.setString(2, tb_teachingoffice.getPassword());
			pst.setString(3, tb_teachingoffice.getName());
			pst.setString(4, tb_teachingoffice.getPhone());
			
			ret = pst.executeUpdate();

			pst.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return ret;
	}
	public static int update(Tb_teachingoffice tb_teachingoffice) {
		init();
		int ret = 0;
		String table_name="教学办";
		try{
			sqlCommand = "update "+table_name+
					" set 密码=?,姓名=?,手机号码=? where 工号=?;";
			conn = JdbcUtil.getConnection();
			pst = conn.prepareStatement(sqlCommand);
			
			pst.setString(1, tb_teachingoffice.getPassword());
			pst.setString(2, tb_teachingoffice.getName());
			pst.setString(3, tb_teachingoffice.getPhone());
			
			pst.setString(4, tb_teachingoffice.getId());
			
			ret = pst.executeUpdate();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return ret;
	}
}
