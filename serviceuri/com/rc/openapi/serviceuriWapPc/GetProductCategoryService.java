package com.rc.openapi.serviceuriWapPc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.openapi.model.ProductCategoryModel;
import com.rc.openapi.service.TCategoryManager;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.MemCached;
import com.rc.openapi.vo.TCategory;
import com.rc.openapi.vo.TCategoryExample;

/**
 * 功能：获取商品分类、一级、二级、三级
 * 作者：LGP
 * 时间：2016年8月17日
 */
public class GetProductCategoryService extends BaseURIService 
{
	private static final long serialVersionUID = -6726191295157971181L;
	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html;charset=utf-8");
		String statusCode = "0";
		PrintWriter out = response.getWriter();
		String jsoncallback = request.getParameter("jsoncallback");
		String dataType = "wap";
		String _dt = request.getParameter("dataType");		
		if(_dt != null && !_dt.equals(""))
		{
			dataType = _dt;
		}
		String pid = request.getParameter("pid");
		List<TCategory> rootList = new ArrayList<TCategory>();
		String message = "";
		String resultStr = "";
		try
		{
			if(dataType.equals("wap"))
			{
				resultStr = (String) MemCached.getmcc().get("v_new_productcategory_wap");
			}
			else
			{
				resultStr = (String) MemCached.getmcc().get("v_new_productcategory_pc");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		if (resultStr == null || "".equals(resultStr)) 
		{
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			try 
			{
				TCategoryManager tCategoryManager = (TCategoryManager) getSpringBean("tcategorymanager");
				OpenSqlManageImpl openSqlManageImpl = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
				Map<String, Object> rootIdMap = new HashMap<String, Object>();
				rootIdMap.put("categoryLevel", 1);
				WapPcUtil.setParamInMap(rootIdMap, dataType);
				List<Long> rootIds = openSqlManageImpl.selectForListByMap(rootIdMap, "t_category_new.select_category_wap_pc");
				Long _pid = -1L;
				if(pid != null && !"".equals(pid)) 
				{
					try 
					{
						_pid = Long.valueOf(pid);
					} 
					catch (NumberFormatException e) 
					{
						e.printStackTrace();
						_pid = -1L;
					}
				}
				if(rootIds != null && rootIds.size() > 0) 
				{
					_pid = rootIds.get(0);
				}
				if(rootIds != null && rootIds.size() > 0) 
				{
					//查所有根目录的分类,以及给出分类的所有子分类
					TCategoryExample tCategoryExample = new TCategoryExample();
					tCategoryExample.createCriteria().andIdIn(rootIds);
					tCategoryExample.setOrderByClause(" weight desc ");
					rootList = tCategoryManager.selectByExampleNoCache(tCategoryExample);
				}
				Map<String, Object> rootMap = null;
				for(TCategory t : rootList) 
				{
					//根目录
					rootMap = new HashMap<String, Object>();
					rootMap.put("imgs", "");
					rootMap.put("name", t.getCategoryName());
					rootMap.put("pid", t.getId() + "");
					List<ProductCategoryModel> tmpList = new ArrayList<ProductCategoryModel>();
					if(t.getId().longValue() == (_pid).longValue()) 
					{
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("pid", t.getId());
						WapPcUtil.setParamInMap(param, dataType);
						//取所有该分类下的所有二级分类
						List<Map<String, Object>> secList = openSqlManageImpl.selectForListByMap(param, "t_category_new.select_secound_category_wap_pc");
						ProductCategoryModel p = null;
						for(Map<String, Object> map : secList) 
						{
							//二级带图片的分类;
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
								if (lastList != null && lastList.size() > 0) 
								{
									for(Map<String, Object> map2 : lastList) 
									{
										if(map2 != null) 
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
						//二级分类信息,带图片的信息
						rootMap.put("plist", tmpList);
					} 
					else 
					{
						rootMap.put("plist", tmpList);
					}
					resultList.add(rootMap);
				}
				statusCode = "1";
			} 
			catch(SQLException e) 
			{
				e.printStackTrace();
				message = "服务器忙,请稍后重试！";
				statusCode = "0";
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if("0".equals(statusCode)) 
			{
				message = "服务器忙,请稍后重试！";
				map.put("message", message);
			}
			map.put("statusCode", statusCode);
			map.put("list", resultList);
			JSONObject json4 = JSONObject.fromObject(map);
			resultStr = json4.toString();
			if("wap".equals(dataType))
			{
				MemCached.getmcc().add("v_new_productcategory_wap", resultStr, new Date(1000 * 60 * 5));
			}
			else
			{
				MemCached.getmcc().add("v_new_productcategory_pc", resultStr, new Date(1000 * 60 * 5));
			}
		}
		out.print(jsoncallback + "(" + resultStr + ");");
		out.flush();
		out.close();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	

}
