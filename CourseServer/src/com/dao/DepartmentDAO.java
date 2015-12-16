package com.dao;

import com.tb.Tb_department;
import com.tb.Tb_teacher;
import com.util.JdbcUtil;

public class DepartmentDAO extends MyDAO {
	public static int insert(Tb_department tb_department) {
		init();
		int ret = 0;
		try {
			sqlCommand = "insert into 系负责人信息表" + " values(?,?,?,?,?)";
			conn = JdbcUtil.getConnection();
			pst = conn.prepareStatement(sqlCommand);
			
			pst.setString(1, tb_department.getId());
			pst.setString(2, tb_department.getPassword());
			pst.setString(3, tb_department.getName());
			pst.setString(4, tb_department.getPhone());
			pst.setString(5, tb_department.getDepartment());
			
			ret = pst.executeUpdate();

			pst.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return ret;
	}
	public static int update(Tb_department tb_department) {
		init();
		int ret = 0;
		String table_name="系负责人信息表";
		try{
			sqlCommand = "update "+table_name+
					" set 密码=?,姓名=?,手机号码=?,所属系=? where 工号=?;";
			conn = JdbcUtil.getConnection();
			pst = conn.prepareStatement(sqlCommand);
					
			
			pst.setString(1, tb_department.getPassword());
			pst.setString(2, tb_department.getName());
			pst.setString(3, tb_department.getPhone());
			pst.setString(4, tb_department.getDepartment());
			
			pst.setString(5, tb_department.getId());
			ret = pst.executeUpdate();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return ret;
	}
}
