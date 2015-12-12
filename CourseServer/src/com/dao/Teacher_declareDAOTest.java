package com.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tb.Tb_teacher_declare;

public class Teacher_declareDAOTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsert() {
		Tb_teacher_declare tb_teacher_declare=
				new Tb_teacher_declare("sqhsb","sqhsb","sqhsb","sqhsb","sqhsb","sqhsb","sqhsb");
		Teacher_declareDAO.insert(tb_teacher_declare);
	}

}
