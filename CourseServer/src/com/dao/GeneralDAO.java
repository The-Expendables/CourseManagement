package com.dao;

import java.util.ArrayList;
import java.util.List;

import com.tb.Tb_course;
import com.tb.Tb_course_mes;
import com.tb.Tb_department;
import com.tb.Tb_teacher;
import com.tb.Tb_teacher_declare;
import com.util.JdbcUtil;

public class GeneralDAO extends MyDAO {
	public static List<Tb_course> queryCourse(String table_name){
		init();
		List<Tb_course> tb_courses=new ArrayList<Tb_course>();
		try{
			
			sqlCommand="select * from "+table_name;
			conn=JdbcUtil.getConnection();
			pst=conn.prepareStatement(sqlCommand);
//			pst.setString(1, tb_name);
			rs=pst.executeQuery();
			while(rs.next()){
				Tb_course tb=new Tb_course();
				
				
				tb.setGrade(rs.getString("年级"));
				tb.setMajor(rs.getString("专业"));
				tb.setP_cnt(rs.getString("专业人数"));
				tb.setC_name(rs.getString("课程名称"));
				tb.setType(rs.getString("选修类型"));
				tb.setCredit(rs.getString("学分"));
				tb.setTimes(rs.getString("学时"));
				tb.setBe_weeks(rs.getString("起讫周序"));
				tb.setT_name(rs.getString("任课教师"));
				tb.setExp_times(rs.getString("实验学时"));
				tb.setPra_times(rs.getString("上机学时"));
				tb.setRemark(rs.getString("备注"));
				
				tb_courses.add(tb);
			}
			
			pst.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return tb_courses;
	}
	
	public static List<Tb_teacher_declare> queryTeacher_declare(String table_name){
		init();
		List<Tb_teacher_declare> tb_teacher_declare=new ArrayList<Tb_teacher_declare>();
		try{
			
			sqlCommand="select * from "+table_name;
			conn=JdbcUtil.getConnection();
			pst=conn.prepareStatement(sqlCommand);
//			pst.setString(1, tb_name);
			rs=pst.executeQuery();
			while(rs.next()){
				Tb_teacher_declare tb=new Tb_teacher_declare();
				
				tb.setTable_name(rs.getString("课程表名"));
				tb.setCourse_name(rs.getString("课程名称"));
				tb.setGrade(rs.getString("年级"));
				tb.setId(rs.getString("工号"));
				tb.setT_name(rs.getString("任课教师"));
				tb.setBe_weeks(rs.getString("起讫周序"));
				tb.setRemark(rs.getString("备注"));
				
				tb_teacher_declare.add(tb);
			}
			
			pst.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return tb_teacher_declare;
	}
	
	public static List<Tb_course_mes> queryCourse_mes(String table_name){
		init();
		List<Tb_course_mes> tb_courses_mes=new ArrayList<Tb_course_mes>();
		try{
			
			sqlCommand="select * from "+table_name;
			conn=JdbcUtil.getConnection();
			pst=conn.prepareStatement(sqlCommand);
//			pst.setString(1, tb_name);
			rs=pst.executeQuery();
			while(rs.next()){
				Tb_course_mes tb=new Tb_course_mes();
				
				tb.setTable_name(rs.getString("课程表名"));
				tb.setTerm(rs.getString("学期"));
				tb.setTeacher_time(rs.getString("教师报课截止时间"));
				tb.setDepartment_time(rs.getString("系负责人审核截止时间"));
				
				tb_courses_mes.add(tb);
			}
			
			pst.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return tb_courses_mes;
	}
	
	public static List<Tb_teacher> queryTeacher(String table_name){
		init();
		List<Tb_teacher> tb_teacher=new ArrayList<Tb_teacher>();
		try{
			
			sqlCommand="select * from "+table_name;
			conn=JdbcUtil.getConnection();
			pst=conn.prepareStatement(sqlCommand);
//			pst.setString(1, tb_name);
			rs=pst.executeQuery();
			while(rs.next()){
				Tb_teacher tb=new Tb_teacher();
				
				tb.setId(rs.getString("工号"));
				tb.setPassword(rs.getString("密码"));
				tb.setDepartment(rs.getString("所属系"));
				tb.setName(rs.getString("姓名"));
				tb.setSex(rs.getString("性别"));
				tb.setBirth(rs.getString("出生年月"));
				tb.setEmail(rs.getString("邮箱"));
				tb.setPhone(rs.getString("手机号码"));
				
				tb_teacher.add(tb);
			}
			
			pst.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return tb_teacher;
	}
	
	public static List<Tb_department> queryDepartment(String table_name){
		init();
		List<Tb_department> tb_department=new ArrayList<Tb_department>();
		try{
			
			sqlCommand="select * from "+table_name;
			conn=JdbcUtil.getConnection();
			pst=conn.prepareStatement(sqlCommand);
//			pst.setString(1, tb_name);
			rs=pst.executeQuery();
			while(rs.next()){
				Tb_department tb=new Tb_department();
				
				tb.setId(rs.getString("工号"));
				tb.setPassword(rs.getString("密码"));
				tb.setDepartment(rs.getString("所属系"));
				tb.setPhone(rs.getString("手机号码"));
				tb.setName(rs.getString("姓名"));
				
				tb_department.add(tb);
			}
			
			pst.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return tb_department;
	}
}
