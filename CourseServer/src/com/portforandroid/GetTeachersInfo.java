package com.portforandroid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.CourseDAO;
import com.dao.TeacherDAO;
import com.dao.Teacher_declareDAO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tb.Tb_course;
import com.tb.Tb_teacher;
import com.tb.Tb_teacher_declare;

/**
 * Servlet implementation class GetTeachersInfo
 */
@WebServlet("/GetTeachersInfo")
public class GetTeachersInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Gson gson=new Gson();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8"); //避免中文乱码 POST方式提交  
    	
    	String table_name= request.getParameter("table_name");
        String teachers_json = request.getParameter("info_json");
        
        List<Tb_teacher> teachers=new ArrayList<Tb_teacher>();
        teachers=gson.fromJson(teachers_json ,new TypeToken<List<Tb_teacher>>(){}.getType());

        //插入教师信息表
        int su=TeacherDAO.insertAll(teachers);//su为是否成功的意思
        
        String ret="";
        if(su==0){
        	ret="导入失败";
        }else{
        	ret="导入成功";
        }
        response.getOutputStream().write(ret.getBytes("UTF-8"));
    	response.setContentType("text/json); charset=UTF-8");
    }   
}
