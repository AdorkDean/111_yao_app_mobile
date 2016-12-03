package com.rc.openapi.router;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rc.openapi.util.ConfigureUitl;

public class ServiceRouter extends HttpServlet {
	private static final long serialVersionUID = -8300296594974369342L;
	private WebApplicationContext wac = null;

	/**
	 * Constructor of the object.
	 */
	public ServiceRouter() {
		super();
	}

	/**
	 * 根据请求的method 获取真实uri
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodStr = getRequestAPIMethod(request,response);	
		if (methodStr != null && methodStr.trim().length() > 0) {
			String serviceURI = ConfigureUitl.getMethodURI(request.getSession().getServletContext(), methodStr);
			if (serviceURI != null && serviceURI.trim().length() > 0) {
				getServletContext().getRequestDispatcher(serviceURI.trim()).forward(request, response);
			}
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}	
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
	}

	protected String getRequestAPIVersion(HttpServletRequest request, HttpServletResponse response) {
		return request.getParameter("v");
	}

	protected String getRequestAPIAppKey(HttpServletRequest request, HttpServletResponse response) {
		return request.getParameter("appKey");
	}

	protected String getRequestAPITimeStamp(HttpServletRequest request, HttpServletResponse response) {
		return request.getParameter("timestamp");
	}

	protected String getRequestAPIFormat(HttpServletRequest request, HttpServletResponse response) {
		return request.getParameter("format");
	}

	protected String getRequestAPISig(HttpServletRequest request, HttpServletResponse response) {
		return request.getParameter("sig");
	}

	protected String getRequestAPIMethod(HttpServletRequest request, HttpServletResponse response) {
		return request.getParameter("method");
	}



	protected Object getSpringBean(String beanName) {
		if (beanName != null && beanName.trim().length() > 0) {
			return wac.getBean(beanName);
		} else {
			return null;
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		super.init();
		wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	}

}