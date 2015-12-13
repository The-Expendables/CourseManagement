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

/**
 * 接收教师报课信息
 * 这里特别注意表名的问题！！！
 */



@WebServlet("/Send_teacher_declare")
public class Get_teacher_declare extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private Gson gson=new Gson();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8"); //避免中文乱码 POST方式提交  
    	
        String teacher_declare_json = request.getParameter("tb_teacher_declare_json");
//        System.out.println(table_json);
        
        Tb_teacher_declare tb_teacher_declare=new Tb_teacher_declare();
        tb_teacher_declare=gson.fromJson(teacher_declare_json,Tb_teacher_declare.class);
        //这里的表名根据实际情况看看是否进行软编码
        String table_name=tb_teacher_declare.getTable_name();
        //插入教师报课信息表
        int su=Teacher_declareDAO.insert(tb_teacher_declare);//su为是否成功的意思
        
        //插入开课表
        Tb_course tb_course=CourseDAO.query(tb_teacher_declare.getGrade(), 
        		tb_teacher_declare.getCourse_name(), table_name);
        tb_course.setThree(tb_teacher_declare.getBe_weeks(),
        		tb_teacher_declare.getT_name(), tb_teacher_declare.getRemark());
        int su1=CourseDAO.update(tb_course, table_name);
        
        String ret="";
        if(su==0||su1==0){
        	ret="false";
        }else{
        	ret="true";
        }
        response.getOutputStream().write(ret.getBytes("UTF-8"));
    	response.setContentType("text/json); charset=UTF-8");
    }   

}
