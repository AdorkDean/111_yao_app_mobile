package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 去支付页面接口
 *
 */
public class GotoPayService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderid = request.getParameter("orderid");// 订单id
		String sessionid = request.getParameter("sessionid");// 用户session id
		String statusCode = null;
		String errorMessage ="";//提示信息
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.hasText(sessionid)&&StringUtils.hasText(orderid)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			try {
				Long userId = tmembermanager.getMemberId(sessionid);
				Map<String,Object> mapv = new HashMap<String, Object>();
				if(0<userId){
					OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
					mapv.put("orderid", Long.parseLong(orderid));
					mapv.put("userid", userId);
					Map<String,Object> obj = (Map<String,Object>)om.selectForObjectByMap(mapv, "t_order.selectPayable");
					map.put("obj", obj);
					List<Map<String,Object>> list = (List<Map<String, Object>>)om.selectForListByMap(mapv, "t_order.selectAppPaymethod");
					map.put("list", list);
					statusCode="1";
					errorMessage="成功!";
				}else {
					statusCode="0";
					errorMessage="用户session失效!";
				}
			}catch (Exception e) {
				statusCode="0";
				errorMessage="网络异常!";
			}
		}else {
			statusCode="0";
			errorMessage="参数错误!";
		}
		map.put("statusCode", statusCode);
		map.put("errorMessage", errorMessage);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

}
