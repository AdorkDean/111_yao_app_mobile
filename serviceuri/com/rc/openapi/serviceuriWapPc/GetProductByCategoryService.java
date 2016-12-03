package com.rc.openapi.serviceuriWapPc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 功能：根据分类ID查询商品
 * 作者：LGP
 * 时间：2016年8月26日
 */
public class GetProductByCategoryService extends BaseURIService 
{
	private static final long serialVersionUID = 1110296124974369342L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String jsoncallback = request.getParameter("jsoncallback");
		String dataType = "wap";
		String _dt = request.getParameter("dataType");		
		if(_dt != null && !_dt.equals(""))
		{
			dataType = _dt;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String cId = request.getParameter("cId");
		
		if(null != cId && !("").equals(cId))
		{
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			if(null == pageNo || ("").equals(pageNo) || "" == pageNo)
			{
				pageNo = "1";
			}
			if(null == pageSize || ("").equals(pageSize) || "" == pageSize)
			{
				pageSize = "10";
			}		
			try 
			{
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("price_type", dataType);
				m.put("categoryid", cId);
				OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
				PageWraper pw = new PageWraper();
				pw = om.selectForPageByMap(m, "t_goods.select_product_count_by_category_wap_pc","t_goods.select_product_by_category_wap_pc", new Integer(pageNo), new Integer(pageSize));
				map.put("statusCode", "1");
				map.put("cId", cId);
				map.put("dataType", dataType);
				map.put("list", pw.getResult());
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				map.put("statusCode", "0");
			}
		}
		else
		{
			map.put("statusCode", "2");
		}
		JSONObject json = JSONObject.fromObject(map);
		ConstantUtil.reJSON(jsoncallback + "(" + json.toString() + ");", request, response);
	}
	
}
