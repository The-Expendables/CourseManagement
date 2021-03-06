package com.portforandroid;

/**
 * 用户登录接口
 */

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UsersDAO;

@WebServlet("/login")
public class Login extends HttpServlet {

    private static final long serialVersionUID = 369840050351775312L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	 //登陆成功标志
        String LOGIN_FLAG="";
        //获得客户端提交用户名密码
        int type=Integer.parseInt(request.getParameter("type"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //调用UserDAO中isLogin方法判断数据中用户名密码是否正确
//        int type=1;
//        String username="21001";
//        String password="21001";
        String flag=UsersDAO.login(type, username, password);
//        if(!flag) System.out.println("---------flag null----------");
//        boolean flag=true;
        try {
        	String ret;
            if (flag.equals("false")) {
            	//登录失败  
                LOGIN_FLAG="failure";
                ret="false";
                System.out.println(LOGIN_FLAG);
            }else{
            	LOGIN_FLAG="success";
            	System.out.println(LOGIN_FLAG);
//            	Tb_teacher th4 = new Tb_teacher("23459","23459","计算机专业","钱八","男","1985年11月","925471746@qq.com","18865247894");
            	ret=flag;
            }
            response.getOutputStream().write(ret.getBytes("UTF-8"));
        	response.setContentType("text/json); charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   		

}