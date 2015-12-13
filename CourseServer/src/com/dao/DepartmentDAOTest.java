package com.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tb.Tb_department;
import com.tb.Tb_teacher;

public class DepartmentDAOTest extends MyDAO {

	@Before
	public void setUp() throws Exception {
	}

//	@Test
//	public void testInsert() {
//		Tb_department tb_department = new Tb_department();
//		tb_department.setId("10086");
//		tb_department.setPassword("10086");
//		tb_department.setDepartment("10086");
//		tb_department.setPhone("10086");
//		DepartmentDAO.insert(tb_department);
//	}
	@Test
	public void testUpdate() {
		Tb_department tb_department = new Tb_department();
		tb_department.setId("10086");
		tb_department.setPassword("10086");
		tb_department.setDepartment("10086");
		tb_department.setPhone("sqhsb");
		DepartmentDAO.update(tb_department);
	}

}
