package com.rc.openapi.serviceuriWapPc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 功能：查询某类别下的商品
 * 作者：LGP
 * 时间：2016年8月18日
 */
@SuppressWarnings({"unchecked", "rawtypes", "unused"})
public class GetCategoryProductService extends BaseURIService 
{

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

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
		Map map = new HashMap();
		String cId = request.getParameter("cId");
		//1 全部  2 折扣 3价格
		String ptype = request.getParameter("ptype");
		
		if(null != cId && !("").equals(cId) && null != ptype && !("").equals(ptype))
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
			Map map1 = new HashMap();
			map1.put("cId", cId);
			map1.put("cIdS", "%,"+cId+",%");
			if(ptype.equals("1"))
			{
				map1.put("order", " order by gp.is_top desc");	
			}
			else if(ptype.equals("2"))
			{
				map1.put("order", " order by gp.price / d.price asc");
			}
			else if(ptype.equals("3"))
			{
				map1.put("order", " order by gp.price asc");
			}
			else 
			{
				map1.put("order", " order by gp.price desc");
			}
			if(dataType.equals("wap"))
			{
				map1.put("price_type", "wap");
			}
			if(dataType.equals("pc"))
			{
				map1.put("price_type", "pc");
			}
			try 
			{
				OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
				PageWraper pw = new PageWraper();
				pw = om.selectForPageByMap(map1, "t_goods.selectCount","t_goods.select_product_category", new Integer(pageNo), new Integer(pageSize));
				map.put("statusCode", "1");
				map.put("cId", cId);
				map.put("ptype", ptype);
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
