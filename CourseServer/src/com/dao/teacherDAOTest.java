package com.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tb.Tb_teacher;

public class teacherDAOTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsert() {
		Tb_teacher tb_teacher = new Tb_teacher();
		tb_teacher.setId("10086");
		tb_teacher.setPassword("10086");
		tb_teacher.setDepartment("10086");
		tb_teacher.setPhone("10086");
		TeacherDAO.insert(tb_teacher);
	}

}
