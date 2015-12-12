package com.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.tb.Tb_teacher;

public class GeneralDAOTest {

	@Test
	public void testQueryTeacher() {
		String table_name = "教师信息表";
		List<Tb_teacher> tb_teacher = GeneralDAO.queryTeacher(table_name);
		for (int i = 0; i < tb_teacher.size(); i++) {
		    System.out.println(tb_teacher.get(i));
		}
	}

}
