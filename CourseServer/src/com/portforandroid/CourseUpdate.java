package com.portforandroid;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.CourseDAO;
import com.dao.Teacher_declareDAO;
import com.google.gson.Gson;
import com.tb.Tb_course;
import com.tb.Tb_teacher_declare;

@WebServlet("/CourseUpdate")
public class CourseUpdate extends HttpServlet {
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
        String delete_json = request.getParameter("delete_json");
       
		int su_course=0,su_teacher_declare=0;
        switch(table_name)
        {
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
        Tb_teacher_declare tb_teacher_declare=
        		gson.fromJson(delete_json, Tb_teacher_declare.class);
        su_teacher_declare=Teacher_declareDAO.delete(tb_teacher_declare);
        String str1="",str2="";
        if(su_course!=0){
        	str1="课程";
        }
        if(su_teacher_declare!=0){
        	str2="教师报课";
        }
        System.out.print(su_course);
        System.out.print(su_teacher_declare);
        String ret="";
        if(str1.length()>0) ret=str1+"信息更新成功";
        else ret="信息更新失败";
        
        String ret1="";
        if(str2.length()>0) ret1=str2+"信息更新成功";
        else ret1="信息更新失败";
        
        response.getOutputStream().write(ret.getBytes("UTF-8"));
        response.getOutputStream().write(ret1.getBytes("UTF-8"));
    	response.setContentType("text/json); charset=UTF-8");
	}
}
