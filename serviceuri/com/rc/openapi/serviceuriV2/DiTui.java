package com.rc.openapi.serviceuriV2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rc.openapi.service.ICartManager;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;

public class DiTui extends BaseURIService {
	
	private OpenSqlManageImpl opensqlmanage;
	private ICartManager cartmanager;

	
	public static void main(String[] args) {
		
		DiTui o = new DiTui();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
		o.opensqlmanage = (OpenSqlManageImpl) context.getBean("opensqlmanage");
		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		
		try{
			
			opensqlmanage = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
			cartmanager = (ICartManager) getSpringBean("cartmanager");
			
			String id = request.getParameter("id");
			String sessionid = request.getParameter("sessionid");
			
			
			/*long userid = dstmembermanager.getMemberId(sessionid);
			boolean isContain = CartUtil.isContain(Long.parseLong(id));
			if(isContain)
			{
				boolean b = cartmanager.ditui(0, userid); 	//如该用户已经参加过地推
				System.out.println("b-------------->"+b);
				if(b){
					request.getRequestDispatcher("/ditui_s/fail.html").forward(request, response); // 跳向失败页，说明用户已经参加过地推
					return;
				}
			}
			System.out.println("id============"+userid);
			*/
						
			
			Map map = new HashMap();
			map.put("id", id);
			Map rs = (HashMap)opensqlmanage.selectForObjectByMap(map, "mycart.get-ditui-goods");
			request.setAttribute("goods",rs);
			request.getRequestDispatcher("/ditui_s/index.jsp").forward(request, response);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
	}
	

	
}
