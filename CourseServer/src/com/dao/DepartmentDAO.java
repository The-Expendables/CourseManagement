package com.dao;

import com.tb.Tb_department;
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
}
