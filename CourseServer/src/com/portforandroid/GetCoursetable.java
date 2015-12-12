package com.portforandroid;

/**
 * 教学办开课接口
 */

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.CourseDAO;
import com.dao.Course_mesDAO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tb.Tb_course;
import com.tb.Tb_course_mes;
import com.util.GV;

@WebServlet("/sendCoursetable")
public class  GetCoursetable extends HttpServlet {

    private static final long serialVersionUID = 369840050351775312L;
    private Gson gson=new Gson();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8"); //避免中文乱码 POST方式提交  
    	
        String table_json = request.getParameter("table_json");
        String course_mes_json = request.getParameter("course_mes_json");
//        System.out.println(table_json);
        
        List<Tb_course> tb_course_list;
        tb_course_list=gson.fromJson(table_json, new TypeToken<List<Tb_course>>(){}.getType());
        
//        for(Tb_course tb:tb_course_list){
//        	System.out.println(tb.getC_name());
//        }
        
        Tb_course_mes tb_course_mes=new Tb_course_mes();
        tb_course_mes=gson.fromJson(course_mes_json, Tb_course_mes.class);
        
        GV gv=new GV();
        String table_name=gv.real_table_name.get(tb_course_mes.getTable_name());
//        System.out.println("表名："+table_name);
        
        int su=CourseDAO.insertAll(tb_course_list,table_name);
        int su1=Course_mesDAO.insert(tb_course_mes);
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
