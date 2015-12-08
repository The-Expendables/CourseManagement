package com.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class CharacterEncoding 编码转换过滤�?,可解决中文乱码问�?
 */
public class EncodingFilter implements Filter {
	// 要使该过滤器生效�?在web.xml里面加入如下代码
	// <filter-name>EncodingFilter</filter-name>
	// <filter-class>filter.EncodingFilter</filter-class>
	// <init-param>
	// <param-name>Encoding</param-name>
	// <param-value>UTF-8</param-value>
	// </init-param>
	// </filter>
	// <filter-mapping>
	// <filter-name>EncodingFilter</filter-name>
	// <url-pattern>/*</url-pattern>
	// </filter-mapping>
	//
	private String Encoding = null;

	/**
	 * Default constructor.
	 */
	public EncodingFilter() {

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		Encoding = fConfig.getInitParameter("Encoding");
		System.out.println(Encoding);
		if (Encoding == null) {
			throw new ServletException("web.xml中的Encodingfliter中的编码未设�?");
		}
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	    System.out.println("EncodingFilter");
		request.setCharacterEncoding(Encoding);
		response.setCharacterEncoding(Encoding);
		chain.doFilter(request, response);
		request.setCharacterEncoding(Encoding);
		response.setCharacterEncoding(Encoding);
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

}
