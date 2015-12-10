package com.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tb.Tb_course;

public class CourseDAOTest {

	@Before
	public void setUp() throws Exception {
	}

//	@Test
//	public void testInsertAll() {
//		Tb_course tb_course=
//			new Tb_course("2012","计算机科学与技术","88","毕业设计(论文)","毕业设计","15","","","","","","","");
//		
//		
//		Tb_course tb_course1=
//				new Tb_course("2013","计算机科学与","88","毕业计(论文)","毕业","1","","","","","","","");
//		List<Tb_course> tb_course_list=new ArrayList<Tb_course>();
//		tb_course_list.add(tb_course);
//		tb_course_list.add(tb_course1);
//		CourseDAO.insertAll(tb_course_list, "计算机科学与技术专业开课表");
//	}
	
	@Test
	public void testqueryAll(){
		List<Tb_course> tb_courses=CourseDAO.queryAll("计算机科学与技术专业开课表");
		for(Tb_course tb:tb_courses){
			System.out.println(tb.getC_name());
		}
	}
}
