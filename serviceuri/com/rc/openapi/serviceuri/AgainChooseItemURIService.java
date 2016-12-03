package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.openapi.service.ICartManager;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.util.ConstantUtil;

/**
 * 重新购买操作 
 */
public class AgainChooseItemURIService extends BaseURIService {

	private static final long serialVersionUID = -4714905664361620800L;
	private OpenSqlManage opensqlmanage;
	private ICartManager cartmanager;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String statusCode = "0";
		try {
			String orderId = request.getParameter("orderId");
			cartmanager = (ICartManager) getSpringBean("cartmanager");
			cartmanager.againBuy(Long.parseLong(orderId));
			statusCode = "1";
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = "0";
		}
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("statusCode", statusCode);
		m.put("notenough", "");
		JSONObject json = JSONObject.fromObject(m);
		ConstantUtil.reJSON(json.toString(), request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public OpenSqlManage getOpensqlmanage() {
		return opensqlmanage;
	}

	public void setOpensqlmanage(OpenSqlManage opensqlmanage) {
		this.opensqlmanage = opensqlmanage;
	}

}
