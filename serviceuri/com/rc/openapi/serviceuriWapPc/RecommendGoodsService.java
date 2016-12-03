package com.rc.openapi.serviceuriWapPc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 商品详情推荐商品[猜你在找]
 * @author LGP
 * @date 2016-8-8
 */
@SuppressWarnings({"unused","unchecked"})
public class RecommendGoodsService extends BaseURIService 
{
	private static final long serialVersionUID = 1L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String jsoncallback = request.getParameter("jsoncallback");
		//价格类型
		String price_type = "app";
		//平台类型
		String platform_type = request.getParameter("platform_type");
		//用户ID
		String memberId = request.getParameter("memberId");
		if(memberId == null)
		{
			memberId = "0";
		}
		if(platform_type != null && platform_type.equals("1"))
		{
			price_type = "pc";
		}
		if(platform_type != null && platform_type.equals("2"))
		{
			price_type = "wap";
		}
		if(platform_type == null)
		{
			platform_type = "3";
		}
		Map<String, Object> result = new HashMap<String, Object>();
		String statusCode = "0";
		OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("price_type", price_type);
		m.put("platform_type", platform_type);
		m.put("memberId", memberId);
		List<Map<String, Object>> rlist = om.selectForListByMap(m,"t_goods.select_recommend_product_new");
		result.put("statusCode", statusCode);
		result.put("rlist", rlist);
		JSONObject json = JSONObject.fromObject(result);
		ConstantUtil.reJSON(jsoncallback + "(" + json.toString() + ");", request, response);
	}
}
