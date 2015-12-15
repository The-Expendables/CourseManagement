package com.portforandroid;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.Teacher_declareDAO;
import com.google.gson.Gson;
import com.tb.Tb_teacher_declare;

@WebServlet("/Limit")
public class TeacherDeclareLimit extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Gson gson = new Gson();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8"); //避免中文乱码 POST方式提交  
    	
        String info_json = request.getParameter("limit_json");
        Tb_teacher_declare tb_teacher_declare=
    			gson.fromJson(info_json, Tb_teacher_declare.class);
        boolean flag=Teacher_declareDAO.search(tb_teacher_declare);
        System.out.print(flag);
        try {
        	String ret;
            if (flag) {
            	ret="false";
            }else{
                ret="true";
            }
            System.out.println(ret);
            response.getOutputStream().write(ret.getBytes("UTF-8"));
        	response.setContentType("text/json); charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
