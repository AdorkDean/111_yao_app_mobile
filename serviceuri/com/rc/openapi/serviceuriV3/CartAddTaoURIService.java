package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import com.rc.openapi.service.TCartItemManager;
import com.rc.openapi.service.TMemberCollectManager;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.service.TMemberSessionManager;
import com.rc.openapi.serviceuri.BaseURIService;


public class CartAddTaoURIService extends BaseURIService {
	
	private ICartManager cartmanager;
	private OpenSqlManage opensqlmanage;
	private TCartItemManager tcartitemmanager;
	private TMemberCollectManager tmembercollectmanager;
	public static String cartKey = "111yao";
	private TMemberSessionManager tmembersessionmanager;
	private TMemberManager tmembermanager;


	
	public static void main(String[] args) throws SQLException {
		
//		AddCartURIService o = new AddCartURIService();
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
//		o.opensqlmanage = (OpenSqlManage) context.getBean("opensqlmanage");
//		o.dstproductmanager = (DstProductManager) context.getBean("dstproductmanager");
//		o.dstcartitemmanager = (DstCartItemManager) context.getBean("dstcartitemmanager");
//		o.dstmembermanager = (DstMemberManager) context.getBean("dstmembermanager");
//		o.dstcartmanager = (DstCartManager) context.getBean("dstcartmanager");
//		o.dstproductmanager = (DstProductManager) context.getBean("dstproductmanager");
//		o.dstcartmanager = (DstCartManager) context.getBean("dstcartmanager");
		
		String s = "232";
		String[] ary = s.split(",");
		for(String ss : ary){
			System.out.println(ss);
		}
	
	}
	

	
	
	@Override
	public synchronized void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=utf-8");
		
		opensqlmanage=(OpenSqlManage)getSpringBean("opensqlmanage");
		tmembermanager=(TMemberManager)getSpringBean("tmembermanager");
		cartmanager=(ICartManager)getSpringBean("cartmanager");
		
		PrintWriter out = resp.getWriter();
		String jsoncallback = req.getParameter("jsoncallback");
		String flag = req.getParameter("flag");
		String from = req.getParameter("from"); // app OR wap
		
		System.out.println("from---------->"+from);
		
		String statusCode = "0";
		List<String> failGoodsList = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		short failfalg = 0;
		int n = 0;
		try{
			
			String sessionId = req.getParameter("sessionid");
			String uuid = req.getParameter("uuid");
			System.out.println("uuid------------->"+uuid);
			System.out.println("sessionid------------->"+sessionId);
			String productId = req.getParameter("productid");
			int count = Integer.parseInt(req.getParameter("count")==null?"1":req.getParameter("count"));
			CartParam arg = new CartParam();
			if("".equals(sessionId)) sessionId = null;
			long userId = -1;
			if(sessionId!=null){
			   userId = tmembermanager.getMemberId(sessionId);
			}
			if(userId!=-1)
			{
				arg.setUserId(userId);
				arg.setIslogin(true);
			}else{
				arg.setIslogin(false);
				arg.setCartkey(uuid);
			}
			long id = 1;
			if("1".equals(flag)){	// 正常商品
				arg.setGoodsId(Long.parseLong(productId));
				id = cartmanager.addCart(arg,count);
				String s = "{\"statusCode\":\""+id+"\",\"failfalg\":\""+failfalg+"\",\"name\":\""+sb+"\"}";
				System.out.print("s----->"+s);
				out.print(jsoncallback+"("+s+");");
				out.close();
				return;
			}else{
				
				Map map = new HashMap();
				map.put("taoid", productId);
				List<Map> list = opensqlmanage.selectForListByMap(map, "mycart.taocan_goods");
				n = list.size();
				long goodsId = 0;
				for(Map m : list){
					arg.setGoodsId((Long)m.get("goods_id"));
					id = cartmanager.addCart(arg,count);
					if(id<0){	// 加入购物车失败的商品
						failGoodsList.add((String)m.get("goods_name"));
					}
				}
			}


			if(failGoodsList.size()>0)
			{
				int x = 1;
				for(String name : failGoodsList){
					sb.append(x+"."+name+";\\n");
					x++;
				}
				failfalg = 1;
				if(n>failGoodsList.size()){ // 部分商品添加成功 
					statusCode = "2";
				}else if(n==failGoodsList.size()){	// 所有商品都没有库存或下架
					statusCode = "3";
				}
				
			}else{
				statusCode = "1";
			}
			
			System.out.println("add cart fail goods--->"+sb);
			
			
		}catch(Exception e){
			e.printStackTrace();
			statusCode = "0";
			
		}
		String s = "{\"statusCode\":\""+statusCode+"\",\"failfalg\":\""+failfalg+"\",\"name\":\""+sb+"\"}";
		System.out.print("s----->"+s);
//		String s = "{\"statusCode\":\""+statusCode+"\"}";
		out.print(jsoncallback+"("+s+");");
		out.close();
		
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Aduto-generated method stub
		doPost(req,resp);
	}



}
