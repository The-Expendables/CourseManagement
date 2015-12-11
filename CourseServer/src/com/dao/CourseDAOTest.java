package com.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tb.Tb_course;
import com.tb.Tb_teacher_declare;

public class CourseDAOTest {

	@Before
	public void setUp() throws Exception {
	}

//	@Test
//	public void testInsertAll() {
//		Tb_course tb_course=
//			new Tb_course("2012","计算机科学与技术","88","毕业设计(论文)","毕业设计","15","","","","","","","");
//		Tb_course tb_course1=
//				new Tb_course("2013","计算机科学与","88","毕业计(论文)","毕业","1","","","","","","","");
//		List<Tb_course> tb_course_list=new ArrayList<Tb_course>();
//		tb_course_list.add(tb_course);
//		tb_course_list.add(tb_course1);
//		CourseDAO.insertAll(tb_course_list, "计算机科学与技术专业开课表");
//	}
//	
//	@Test
//	public void testqueryAll(){
//		List<Tb_course> tb_courses=CourseDAO.queryAll("计算机科学与技术专业开课表");
//		for(Tb_course tb:tb_courses){
//			System.out.println(tb.getC_name());
//		}
//	}
	
	@Test
	public void testupdate(){
		Tb_teacher_declare tb_teacher_declare=new Tb_teacher_declare
				("计算机科学与技术专业开课表","毕业设计(论文)","2012","1-3","110","苏钦辉sp","我是傻逼");
        String table_name=tb_teacher_declare.getTable_name();
        
        //插入教师报课信息表
//        Teacher_declareDAO.insert(tb_teacher_declare);
        
        //插入开课表=================================================================
        Tb_course tb_course=CourseDAO.query(tb_teacher_declare.getGrade(), 
        		tb_teacher_declare.getCourse_name(), table_name);
//        if(tb_course==null) System.out.println("tb_course is null");
//        else System.out.println("tb_course is not null");
        //与查询得到的数据进行合并
        tb_course.setThree(tb_teacher_declare.getBe_weeks(),
        		tb_teacher_declare.getT_name(), tb_teacher_declare.getRemark());
        
        System.out.println(tb_course.getBe_weeks()+
        		":"+tb_course.getT_name()+":"+tb_course.getRemark());
        int su=CourseDAO.update(tb_course, table_name);
        if(su!=0) System.out.println("update success!");
        //====================
	}
}
