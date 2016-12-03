package com.rc.openapi.serviceuriWapPc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.model.ProductCategoryModel;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;

/**
 * 功能：获取商品分类、一级、二级、三级
 * 作者：LGP
 * 时间：2016年8月17日
 */
public class GetSubProductCategoryService extends BaseURIService 
{
	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String jsoncallback = request.getParameter("jsoncallback");
		String dataType = "wap";
		String _dt = request.getParameter("dataType");		
		if(_dt != null && !_dt.equals(""))
		{
			dataType = _dt;
		}
		String pid = request.getParameter("pid");
		String statusCode = "0";
		List<ProductCategoryModel> tmpList = new ArrayList<ProductCategoryModel>();
		String message = "";
		OpenSqlManageImpl openSqlManageImpl = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
		try 
		{
			if (pid != null && !"".equals(pid)) 
			{
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("pid", pid);
				WapPcUtil.setParamInMap(param, dataType);
				//取所有该分类下的所有二级分类;
				List<Map<String, Object>> secList = openSqlManageImpl.selectForListByMap(param, "t_category_new.select_secound_category_wap_pc");
				ProductCategoryModel p = null;
				for(Map<String, Object> map : secList) 
				{
					//二级带图片的分类
					if(map != null) 
					{
						p = new ProductCategoryModel();
						p.setName(map.get("name") == null ? "" : map.get("name").toString());
						p.setPid(map.get("id") == null ? "" : map.get("id").toString());
						p.setImgs(map.get("imgUrl") == null ? "" : map.get("imgUrl").toString());
						p.setType("0");
						p.setDescribe(map.get("describe") == null ? "" : map.get("describe").toString());
						Map<String, Object> param2 = new HashMap<String, Object>();
						param2.put("pid", Long.valueOf(map.get("id").toString()));
						WapPcUtil.setParamInMap(param2, dataType);
						List<Map<String, Object>> lastList = openSqlManageImpl.selectForListByMap(param2, "t_category_new.select_secound_category_wap_pc");
						List<ProductCategoryModel> l2 = new ArrayList<ProductCategoryModel>();
						if(lastList != null && lastList.size() > 0) 
						{
							for(Map<String, Object> map2 : lastList) 
							{
								if (map2 != null) 
								{
									ProductCategoryModel p2 = new ProductCategoryModel();
									p2.setPlist(null);
									p2.setPid(map2.get("id") == null ? "" : map2.get("id").toString());
									p2.setName(map2.get("name") == null ? "" : map2.get("name").toString());
									p2.setImgs("");
									p2.setType("0");
									p2.setDescribe(map2.get("describe") == null ? "" : map2.get("describe").toString());
									l2.add(p2);
								}
							}
						} 
						else 
						{
							ProductCategoryModel p2 = new ProductCategoryModel();
							p2.setPlist(null);
							p2.setPid(map.get("id") == null ? "" : map.get("id").toString());
							p2.setName(map.get("name") == null ? "" : map.get("name").toString());
							p2.setImgs("");
							p2.setType("0");
							l2.add(p2);
						}
						p.setPlist(l2);
						tmpList.add(p);
					}
				}
				statusCode = "1";
			} 
			else 
			{
				statusCode = "0";
				log.error("一级分类id为空");
				message = "服务器忙,请稍后重试";
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			statusCode = "0";
			message = "服务器忙,请稍后重试！";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if ("0".equals(statusCode)) 
		{
			map.put("message", message);
		}
		map.put("statusCode", statusCode);
		map.put("list", tmpList);
		JSONObject json4 = JSONObject.fromObject(map);
		String resultStr = json4.toString();
		out.print(jsoncallback + "(" + resultStr + ");");
		out.flush();
		out.close();
	}

}
