package com.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.tb.Tb_teacher;
import com.tb.Tb_teacher_declare;

public class GeneralDAOTest {

	@Test
	public void testQueryTeacher() {
		String table_name = "教师报课信息表";
		List<Tb_teacher_declare> tb_teacher = GeneralDAO.queryTeacher_declare(table_name);
		for (int i = 0; i < tb_teacher.size(); i++) {
		    System.out.println(tb_teacher.get(i));
		}
	}

}
