package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rc.openapi.service.ICartManager;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.util.ConstantUtil;

public class CartBatchDelURIService extends BaseURIService  {

	private OpenSqlManage opensqlmanage;
	private ICartManager cartmanager;
	private TMemberManager tmembermanager;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CartBatchDelURIService o = new CartBatchDelURIService();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
		o.opensqlmanage = (OpenSqlManage) context.getBean("opensqlmanage");
		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		tmembermanager=(TMemberManager)getSpringBean("tmembermanager");
		opensqlmanage=(OpenSqlManage)getSpringBean("opensqlmanage");
		
		
		String sessionId = request.getParameter("sessionid");
		String uuid = request.getParameter("uuid");
		if("".equals(sessionId)) sessionId = null;
		String product = request.getParameter("product"); // 1,2,3
		
		StringBuilder sb = new StringBuilder("");
		String[] arry = product.split(",");
		for(String e : arry){
			if(!"".equals(e)){
				sb.append(e+",");
			}
		}
		product = sb.substring(0,sb.length()-1);
		
		if(product==null || "".equals(product)){
			ConstantUtil.reJSON("{\"statusCode\":\"" + 0 + "\"}", request, response);
			return;
		}
		
		
		String statusCode = "0";
		try{
			
			long userId = -1;
			boolean islogin = true;
			if(sessionId==null){
				islogin = false;
			}else{
				userId = tmembermanager.getMemberId(sessionId);
			}
			
			Map map = new HashMap();
			if(islogin){
				map.put("userid", userId);
			}else{
				map.put("uuid",uuid);
			}
			map.put("product", product);
			
		 int n = opensqlmanage.updateByMap_drug(map, "mycart.batch-del-cart");
		 if(n>0) statusCode = "1";
		 
		}catch(Exception e){
			statusCode = "0";
			e.printStackTrace();
		}
		
		ConstantUtil.reJSON("{\"statusCode\":\"" + statusCode + "\"}", request, response);
		
	}
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
