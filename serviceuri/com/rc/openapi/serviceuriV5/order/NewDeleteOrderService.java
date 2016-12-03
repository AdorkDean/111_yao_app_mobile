package com.rc.openapi.serviceuriV5.order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.serviceApi.OrderServiceDubboApi;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 删除订单
 * @author yangjieliang	
 * @createTime 2016-8-30
 */
public class NewDeleteOrderService extends BaseURIService {

	private static final long serialVersionUID = 646102189335073532L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map temMap=new HashMap();
		OrderServiceDubboApi orderservicedubboapi = (OrderServiceDubboApi) getSpringBean("orderservicedubboapi");
		TMemberManager tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
		String sessionid = request.getParameter("sessionId");
		String orderid = request.getParameter("orderid");
		Long memberId=null;
		//各种判断为空
		if(sessionid==null||"".equals(sessionid)){
			temMap.put("statusCode", "0");
			temMap.put("message", "登录信息有误");
			ConstantUtil.reJSON(JSONObject.fromObject(temMap).toString(), request, response);
			return;
		}else{
			try {
				 memberId = tmembermanager.getMemberId(sessionid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(memberId==null||memberId<=0L){
				temMap.put("statusCode", "0");
				temMap.put("message", "登录信息有误");
				ConstantUtil.reJSON(JSONObject.fromObject(temMap).toString(), request, response);
				return;
			}
		}
		if(orderid==null||"".equals(orderid)){
			temMap.put("statusCode", "0");
			temMap.put("message", "订单信息有误");
			ConstantUtil.reJSON(JSONObject.fromObject(temMap).toString(), request, response);
			return;
		}
		try {
			  boolean flag = orderservicedubboapi.deleteOrder(new Long(orderid));
			 if(flag){
				 temMap.put("statusCode","1");
				 temMap.put("message","删除成功");
			 }else{
				 temMap.put("statusCode", "0");
				 temMap.put("message","操作失败");
			 }
			
		} catch (Exception e) {
			temMap.put("statusCode", "0");
			temMap.put("message", e.getMessage().toString());
			ConstantUtil.reJSON(JSONObject.fromObject(temMap).toString(), request, response);
			e.printStackTrace();
		}
		ConstantUtil.reJSON(JSONObject.fromObject(temMap).toString(), request, response);
	}
	
}
