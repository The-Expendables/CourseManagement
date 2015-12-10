package com.dao;

import java.util.ArrayList;
import java.util.List;

import com.tb.Tb_course;
import com.util.JdbcUtil;

public class CourseDAO extends MyDAO {
	public static int insertAll(List<Tb_course> tb_course_list,String table_name){
		init();
		int ret=0;
		try{
			sqlCommand="insert into "+table_name+
					" values(?,?,?,?,?,?,?,?,?,?,?,?)";
			conn=JdbcUtil.getConnection();
			pst=conn.prepareStatement(sqlCommand);
			for(Tb_course tb:tb_course_list){
				pst.setString(1, tb.getGrade());
				pst.setString(2, tb.getMajor());
				pst.setString(3, tb.getP_cnt());
				pst.setString(4, tb.getC_name());
				pst.setString(5, tb.getType());
				pst.setString(6, tb.getCredit());
				pst.setString(7, tb.getTimes());
				pst.setString(8, tb.getExp_times());
				pst.setString(9, tb.getPra_times());
				pst.setString(10, tb.getBe_weeks());
				pst.setString(11, tb.getT_name());
				pst.setString(12, tb.getRemark());
				ret=pst.executeUpdate();
			}
			
			pst.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return ret;
	}
	public static List<Tb_course> queryAll(String table_name){
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
}
