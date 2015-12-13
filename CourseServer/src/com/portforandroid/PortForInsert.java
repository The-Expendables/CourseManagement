package com.portforandroid;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DepartmentDAO;
import com.dao.TeacherDAO;
import com.dao.TeachingofficeDAO;
import com.google.gson.Gson;
import com.tb.Tb_department;
import com.tb.Tb_teacher;
import com.tb.Tb_teachingoffice;

/**
 * 可扩展！
 */
@WebServlet("/Insert_info")
public class PortForInsert extends HttpServlet {
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
        
        int su_teacher=0,su_teachingoffice=0,su_department=0;
        
        switch(table_name){
        case "教师信息表":
        	Tb_teacher tb_teacher=
        			gson.fromJson(info_json, Tb_teacher.class);
        	su_teacher=TeacherDAO.insert(tb_teacher);
        	if(su_teacher==0) 
        		su_teacher=TeacherDAO.update(tb_teacher);
        	break;
        case "系负责人信息表":
        	Tb_department tb_department=
        			gson.fromJson(info_json, Tb_department.class);
        	su_department=DepartmentDAO.insert(tb_department);
        	if(su_department==0) 
        		su_department=DepartmentDAO.update(tb_department);
        	break;
        case "教学办":
        	Tb_teachingoffice tb_teachingoffice=
        			gson.fromJson(info_json, Tb_teachingoffice.class);
        	su_teachingoffice=TeachingofficeDAO.insert(tb_teachingoffice);
        	if(su_teachingoffice==0)
        		su_teachingoffice=TeachingofficeDAO.update(tb_teachingoffice);
        	break;
        }
        
        String str="";
        if(su_teacher!=0){
        	str="教师";
        }else if(su_teachingoffice!=0){
        	str="教学办";
        }else if(su_department!=0){
        	str="系负责人";
        }
        String ret="";
        if(str.length()>0) ret=str+"信息插入成功";
        else ret="信息插入失败";
        
        response.getOutputStream().write(ret.getBytes("UTF-8"));
    	response.setContentType("text/json); charset=UTF-8");
    }

}
