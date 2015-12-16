package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthorizationCheckFilter 用户权限校验�?
 * id<0时表示页面需要登录才可以访问，如果用户尚未登录则将其引导至登陆页�? 待登陆成功后返回之前申请的页�?
 */
public class AuthorizationCheckFilter implements Filter {

    /**
     * Default constructor.
     */
    public AuthorizationCheckFilter() {
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        System.out.println("AuthorizationCheckFilter");
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        System.out.println("AuthorizationCheckFilterDoFilter");
        // 强制类型转换，否则无法获取http请求参数和session
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        //获取请求校验参数
        String stringId = request.getParameter("id");
        if (stringId == null) {
            stringId = "0";
        }
        Integer id = new Integer(stringId);

        //获取用户名，用户名不为空标明已经登陆
        String userName = new String("");
        HttpSession session = httpRequest.getSession();
        if (session.getAttribute("userName") != null) {
            userName = session.getAttribute("userName").toString();
        }
        
        //用户尚未登录，请求重定向
        if (id < 0 && userName.equals("")) {
            request.getRequestDispatcher("").forward(request, response);
            System.out.println("Checked Filed");
        }
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

}
