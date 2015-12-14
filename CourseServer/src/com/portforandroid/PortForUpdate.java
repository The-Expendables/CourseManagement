package com.portforandroid;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.CourseDAO;
import com.dao.DepartmentDAO;
import com.dao.TeacherDAO;
import com.dao.TeachingofficeDAO;
import com.google.gson.Gson;
import com.tb.Tb_course;
import com.tb.Tb_department;
import com.tb.Tb_teacher;
import com.tb.Tb_teachingoffice;

/**
 * Servlet implementation class PortForUpdate
 */
@WebServlet("/PortForUpdate")
public class PortForUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Gson gson = new Gson();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8"); //避免中文乱码 POST方式提交  
    	
        String table_name= request.getParameter("table_name");
        String info_json = request.getParameter("info_json");
        
        int su_teacher=0,su_teachingoffice=0,su_department=0,su_course=0;
        
        switch(table_name){
        case "教师信息表":
        	Tb_teacher tb_teacher=
        			gson.fromJson(info_json, Tb_teacher.class);
        	su_teacher=TeacherDAO.update(tb_teacher);
        	break;
        case "系负责人信息表":
        	Tb_department tb_department=
        			gson.fromJson(info_json, Tb_department.class);
        	su_department=DepartmentDAO.update(tb_department);
        	break;
        case "教学办":
        	Tb_teachingoffice tb_teachingoffice=
        			gson.fromJson(info_json, Tb_teachingoffice.class);
        	su_teachingoffice=TeachingofficeDAO.update(tb_teachingoffice);
        	break;
        case "计算机科学与技术专业开课表":
        	Tb_course tb_course1=
					gson.fromJson(info_json, Tb_course.class);
        	su_course=CourseDAO.update(tb_course1,"计算机科学与技术专业开课表");
        	break;
        case "计算机科学与技术（实验班）专业开课表":
        	Tb_course tb_course2=
					gson.fromJson(info_json, Tb_course.class);
        	su_course=CourseDAO.update(tb_course2,"计算机科学与技术（实验班）专业开课表");
        	break;
        case "计算机科学与技术（卓越班）专业开课表":
        	Tb_course tb_course3=
					gson.fromJson(info_json, Tb_course.class);
        	su_course=CourseDAO.update(tb_course3,"计算机科学与技术（卓越班）专业开课表");
        	break;
        case "软件工程专业开课表":
        	Tb_course tb_course4=
					gson.fromJson(info_json, Tb_course.class);
        	su_course=CourseDAO.update(tb_course4,"软件工程专业开课表");
        	break;
        case "网络工程专业开课表":
        	Tb_course tb_course5=
					gson.fromJson(info_json, Tb_course.class);
        	su_course=CourseDAO.update(tb_course5,"网络工程专业开课表");
        	break;
        case "信息安全专业开课表":
        	Tb_course tb_course6=
					gson.fromJson(info_json, Tb_course.class);
        	su_course=CourseDAO.update(tb_course6,"信息安全专业开课表");
        	break;
        case "数学类专业开课表":
        	Tb_course tb_course7=
					gson.fromJson(info_json, Tb_course.class);
        	su_course=CourseDAO.update(tb_course7,"数学类专业开课表");
        	break;
        case "数学类（实验班）专业开课表":
        	Tb_course tb_course8=
					gson.fromJson(info_json, Tb_course.class);
        	su_course=CourseDAO.update(tb_course8,"数学类（实验班）专业开课表");
        	break;
        }
        
        String str="";
        if(su_teacher!=0){
        	str="教师";
        }else if(su_teachingoffice!=0){
        	str="教学办";
        }else if(su_department!=0){
        	str="系负责人";
        }else if(su_course!=0){
        	str="课程";
        }
        String ret="";
        if(str.length()>0) ret=str+"信息更新成功";
        else ret="信息更新失败";
        
        response.getOutputStream().write(ret.getBytes("UTF-8"));
    	response.setContentType("text/json); charset=UTF-8");
    }
}
