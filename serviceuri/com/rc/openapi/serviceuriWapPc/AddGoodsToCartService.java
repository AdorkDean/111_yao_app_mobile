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

import com.rc.openapi.model.cart.CartParam;
import com.rc.openapi.service.ICartManager;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.serviceuri.BaseURIService;


/**
 * 功能：单品或套餐商品加入购物车
 * 作者：LGP
 * 时间：2016年9月5日
 */
public class AddGoodsToCartService extends BaseURIService 
{
	private static final long serialVersionUID = 1L;
	private ICartManager cartmanager;
	private OpenSqlManage opensqlmanage;
	public static String cartKey = "111yao";
	private TMemberManager tmembermanager;
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("text/html;charset=utf-8");
		opensqlmanage = (OpenSqlManage) getSpringBean("opensqlmanage");
		tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
		cartmanager = (ICartManager) getSpringBean("cartmanager");
		PrintWriter out = resp.getWriter();
		String jsoncallback = req.getParameter("jsoncallback");
		String flag = req.getParameter("flag");
		String statusCode = "0";
		List<String> failGoodsList = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		short failfalg = 0;
		int n = 0;
		try
		{
			String sessionId = req.getParameter("sessionid");
			String uuid = req.getParameter("uuid");
			String productId = req.getParameter("productid");
			int count = Integer.parseInt(req.getParameter("count")==null?"1":req.getParameter("count"));
			CartParam arg = new CartParam();
			if("".equals(sessionId)) 
			{
				sessionId = null;
			}
			long userId = -1;
			if(sessionId != null)
			{
			   userId = tmembermanager.getMemberId(sessionId);
			}
			if(userId != -1)
			{
				arg.setUserId(userId);
				arg.setIslogin(true);
			}
			else
			{
				arg.setIslogin(false);
				arg.setCartkey(uuid);
			}
			long id = 1;
			//正常商品
			if("1".equals(flag))
			{	
				arg.setGoodsId(Long.parseLong(productId));
				id = cartmanager.addCart(arg,count);
				String s = "{\"statusCode\":\""+id+"\",\"failfalg\":\""+failfalg+"\",\"name\":\""+sb+"\"}";
				out.print(jsoncallback+"("+s+");");
				out.flush();
				out.close();
				return;
			}
			//套餐商品
			else
			{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("taoid", productId);
				List<Map<String, Object>> list = opensqlmanage.selectForListByMap(map, "mycart.taocan_goods_n_e_w");
				n = list.size();
				for(Map<String, Object> m : list)
				{
					arg.setGoodsId((Long)m.get("goods_id"));
					id = cartmanager.addCart(arg, count);
					//加入购物车失败的商品
					if(id<0)
					{	
						failGoodsList.add((String)m.get("goods_name"));
					}
				}
			}
			if(failGoodsList.size() > 0)
			{
				int x = 1;
				for(String name : failGoodsList)
				{
					sb.append(x+"."+name+";\\n");
					x++;
				}
				failfalg = 1;
				//部分商品添加成功 
				if(n>failGoodsList.size())
				{ 
					statusCode = "2";
				}
				//所有商品都没有库存或下架
				else if(n==failGoodsList.size())
				{	
					statusCode = "3";
				}
			}
			else
			{
				statusCode = "1";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			statusCode = "0";
			
		}
		String s = "{\"statusCode\":\""+statusCode+"\",\"failfalg\":\""+failfalg+"\",\"name\":\""+sb+"\"}";
		out.print(jsoncallback+"("+s+");");
		out.flush();
		out.close();
		
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		doPost(req,resp);
	}

}
