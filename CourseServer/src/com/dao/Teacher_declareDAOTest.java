package com.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tb.Tb_course;
import com.tb.Tb_teacher_declare;

public class Teacher_declareDAOTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDelete() {
		Tb_teacher_declare tb_teacher_declare1=
				new Tb_teacher_declare("计算机科学与技术专业开课表","毕业实习","2012","1","1","草泥马","1");
//        Tb_teacher_declare tb_teacher_declare=
//        		new Tb_teacher_declare("计算机科学与技术专业开课表","毕业实习","2012","","","草泥马","");
    	int ret1=0,ret2=0;
		ret1=Teacher_declareDAO.insert(tb_teacher_declare1);
//    	ret2=Teacher_declareDAO.delete(tb_teacher_declare);
    	System.out.print(ret1);
		System.out.print(ret2);
	}

}
