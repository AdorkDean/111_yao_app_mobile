package com.rc.openapi.serviceuriV5.order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.serviceApi.OrderServiceDubboApi;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 个人中心订单列表接口
 * @author yangjieliang	
 * @createTime 2016-8-25
 */
public class PersonalCenterOrderListService extends BaseURIService {

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
		Long memberId=null;
		String orderType=request.getParameter("orderType");
		String pageSize = request.getParameter("pageSize");
		String page = request.getParameter("page");
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
		
		if(orderType==null||"".equals(orderType)){
			orderType="-1";
		}
		if(pageSize==null||"".equals(pageSize)){
			temMap.put("statusCode", "0");
			temMap.put("message", "分页信息有误");
			ConstantUtil.reJSON(JSONObject.fromObject(temMap).toString(), request, response);
			return;
		}
		if(page==null||"".equals(page)){
			temMap.put("statusCode", "0");
			temMap.put("message", "分页信息页码有误");
			ConstantUtil.reJSON(JSONObject.fromObject(temMap).toString(), request, response);
			return;
		}
		try {
			PageWraper pw = orderservicedubboapi.getOrderListByPage(memberId,null,null, Integer.parseInt(orderType),null, Integer.parseInt(pageSize), Integer.parseInt(page));
			temMap.put("statusCode", "1");
			temMap.put("message", "返回成功");
			temMap.put("result", pw);
			
		} catch (Exception e) {
			temMap.put("statusCode", "0");
			temMap.put("message", "网络异常");
			ConstantUtil.reJSON(JSONObject.fromObject(temMap).toString(), request, response);
			e.printStackTrace();
		}
		ConstantUtil.reJSON(JSONObject.fromObject(temMap).toString(), request, response);
	}
	
	public class ResultData{
		private String statusCode;
		private String message;
		private PageWraper resultList;
		
		public PageWraper getResultList() {
			return resultList;
		}
		public void setResultList(PageWraper resultList) {
			this.resultList = resultList;
		}
		public String getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
	
}
