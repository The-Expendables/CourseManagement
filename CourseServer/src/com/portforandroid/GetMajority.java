package com.portforandroid;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.GeneralDAO;
import com.google.gson.Gson;
import com.tb.Tb_course;
import com.tb.Tb_course_mes;
import com.tb.Tb_department;
import com.tb.Tb_teacher;
import com.tb.Tb_teacher_declare;

@WebServlet("/sendList")
public class GetMajority extends HttpServlet {
	private static final long serialVersionUID = 369840050351775312L;
	private Gson gson=new Gson();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8"); //避免中文乱码 POST方式提交  
    	
    	String table_name = request.getParameter("table_name");
    	String type = request.getParameter("type");
    	
    	String str = null;
    	if(type == "1")
    	{
    		List<Tb_course> tb_course_list = GeneralDAO.queryCourse(table_name);
    		str = gson.toJson(tb_course_list);
    	}
    	else if(type == "2")
    	{
    		List<Tb_teacher_declare> tb_teacher_declare = GeneralDAO.queryTeacher_declare(table_name);
    		str = gson.toJson(tb_teacher_declare);
    	}
    	else if(type == "3")
    	{
    		List<Tb_teacher> tb_teacher = GeneralDAO.queryTeacher(table_name);
    		str = gson.toJson(tb_teacher);
    	}
    	else if(type == "4")
    	{
    		List<Tb_department> tb_department = GeneralDAO.queryDepartment(table_name);
    		str = gson.toJson(tb_department);
    	}
    	else if(type == "5")
    	{
    		List<Tb_course_mes> tb_course_mes = GeneralDAO.queryCourse_mes(table_name);
    		str = gson.toJson(tb_course_mes);
    	}
    	response.getOutputStream().write(str.getBytes("UTF-8"));
    	response.setContentType("text/json); charset=UTF-8");
    }
}
