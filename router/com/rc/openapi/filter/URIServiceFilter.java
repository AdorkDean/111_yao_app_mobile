package com.rc.openapi.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rc.openapi.model.Root;
import com.rc.openapi.util.ConfigureUitl;


/**
 * 
 * 接口有统一的入口,http://localhost:8080/111_mobile_zdf/sltRouter?
 * 如果通过统一入口进来的请求,是不会进入这个过滤器的
 * 第一个过滤器
 * 判断是否是debug模式，如果是直接调用servlet的URI，否，则调用统一入口
 * @author yinbin
 *
 */
public class URIServiceFilter implements Filter {
	protected final Logger log = Logger.getLogger(getClass());
	private boolean isDebug = false;

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		if (isDebug) {
			chain.doFilter(req, res);
		} else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out
					.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			out.println("<HTML>");
			out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
			out.println("  <BODY>");
			out.println("  request is denied!!!");
			out.println("  </BODY>");
			out.println("</HTML>");
			out.flush();
			out.close();
		}
	}
	
	/* 初始化参数,加载配置文件
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		String isStrDebug = filterConfig.getInitParameter("isDebug");
		String temp = isStrDebug;
		if ("true".equals(temp) || "on".equals(temp)) {
			isDebug = true;
		} else if ("false".equals(temp) || "off".equals(temp)) {
			isDebug = false;
		} else {
			isDebug = false;
		}
		//判断全局是否加载配置文件
		Root root=(Root)filterConfig.getServletContext().getAttribute(ConfigureUitl.PARNTERROOT);
		if(root==null){
			new ConfigureUitl().loadConfigureRoot(filterConfig.getServletContext());
		}
		
	}

	
	
	public void destroy() {
	}
}