package com.rc.openapi.serviceuriWapPc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 功能：获取各平台价格
 * 作者：LGP
 * 时间：2016年9月1日
 */
public class GetPriceByFlatformService extends BaseURIService
{
	private static final long serialVersionUID = 8312396122974369342L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String jsoncallback = request.getParameter("jsoncallback");
		String params = request.getParameter("params");
		String dataType = request.getParameter("dataType");
		if(null !=params && !("").equals(params))
		{
			JSONArray jsonarray = JSONArray.fromObject(params);
			List<Map<String,Object>> paramsarray =  (List<Map<String, Object>>) JSONArray.toCollection(jsonarray, Map.class);
			List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
			if(paramsarray != null)
			{
				OpenSqlManage opensqlmanage = (OpenSqlManage) getSpringBean("opensqlmanage");
				for(Map<String, Object> m : paramsarray)
				{
					//商品ID
					String goodsId = String.valueOf(m.get("goodsId"));
					//平台
					String platform = String.valueOf(m.get("platform")).trim();
					//HTML元素ID
					String htmlObjId = String.valueOf(m.get("htmlObjId"));
					
					Map<String, Object> nowDatas = null;
					Map<String, Object> map1 = new HashMap<String, Object>();
					try 
					{
						Map<String, Object> p = new HashMap<String, Object>();
						p.put("id", goodsId);
						nowDatas = (Map<String, Object>) opensqlmanage.selectForListByMap(p, "t_goods.selectGoodsPriceById_all_platform").get(0);
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					if(nowDatas != null)
					{
						String price = null;
						if(dataType != null)
						{
							if(dataType.equals("pc"))
							{
								price = String.valueOf(nowDatas.get("pc_price"));
							}
							if(dataType.equals("wap"))
							{
								price = String.valueOf(nowDatas.get("wap_price"));
							}
						}
						else
						{
							price = String.valueOf(nowDatas.get("app_price"));
						}
						map1.put("goodsId", goodsId);
						map1.put("nowPrice", price);
						map1.put("platform", platform);
						map1.put("htmlObjId", htmlObjId);
						result.add(map1);
					}
					else
					{
						result.add(m);
					}
				}
				JSON json = JSONSerializer.toJSON(result);
				ConstantUtil.reJSON(jsoncallback + "(" + json.toString() + ");", request, response);
			}
			else
			{
				ConstantUtil.reJSON(jsoncallback + "({'msg':'the params you send is null.'});", request, response);
			}
		}
		else
		{
			ConstantUtil.reJSON(jsoncallback + "({'msg':'the params you send is null.'});", request, response);
		}
	}
	
}
