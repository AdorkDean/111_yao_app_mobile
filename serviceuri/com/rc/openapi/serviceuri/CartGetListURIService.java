package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.model.cart.CartParam;
import com.rc.openapi.service.ICartManager;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.util.ConstantUtil;

public class CartGetListURIService extends BaseURIService {

	public static java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00"); 
	private OpenSqlManage opensqlmanage;
	private ICartManager cartmanager;
	private TMemberManager tmembermanager;
	private Logger log = Logger.getLogger(getClass());
	
	
	public static void main(String[] args) throws SQLException {
//		GetCartURIService o = new GetCartURIService();
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
//		o.opensqlmanage = (OpenSqlManage) context.getBean("opensqlmanage");
//		o.dstproductmanager = (DstProductManager) context.getBean("dstproductmanager");
//		o.dstcartitemmanager = (DstCartItemManager) context.getBean("dstcartitemmanager");
//		o.dstmembermanager = (DstMemberManager) context.getBean("dstmembermanager");
//		o.dstcartmanager = (DstCartManager) context.getBean("dstcartmanager");
//		o.dstordermanager = (DstOrderManager) context.getBean("dstordermanager");
		
		BigDecimal b = new BigDecimal(1500);
		System.out.println(df.format(b));
		
	}
	
	@Override
	public synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		Map rs = new HashMap();
		
		try{
			
			tmembermanager=(TMemberManager)getSpringBean("tmembermanager");
			cartmanager=(ICartManager)getSpringBean("cartmanager");
			opensqlmanage=(OpenSqlManage)getSpringBean("opensqlmanage");
			String sessionId = req.getParameter("sessionid");
			String uuid = req.getParameter("uuid");
			
			Map map = new HashMap();
			map.put("uuid", uuid);
			CartParam arg = new CartParam();
			arg.setCartkey(uuid);
			if("".equals(sessionId)) sessionId = null;
			long userId = -1;
			
			if(sessionId!=null){
				userId = tmembermanager.getMemberId(sessionId);
			}
			
			if(userId!=-1)
			{
				
				map.put("userid",userId);
				arg.setUserId(userId);
				arg.setIslogin(true);
				
				
//			    cartmanager.synCart(userId, uuid);
			    cartmanager.combineCart(userId, uuid);
			}
			
			
			// 取列表 
			List<Map>  list = opensqlmanage.selectForListByMap(map, "mycart.get-cart-item");
			
			StringBuilder sb = new StringBuilder("");
			for(Map item : list){
				sb.append(item.get("goods_name")+",");
			}
			
			String stockLess = "";
			
			if(sb.length()>0){
			   stockLess  = sb.toString().substring(0,sb.toString().length()-1)+",";
			}
		
			// 取核算
			rs = cartmanager.accounts(arg);
			BigDecimal money = (BigDecimal)rs.get("money");
			rs.put("cart", list);
			rs.put("heji", df.format(money));
			rs.put("point", money.intValue());
			rs.put("notenough", stockLess);
			rs.put("statusCode", "1");
		}catch(Exception e){
			rs.put("statusCode", "0");
			e.printStackTrace();
		}
		JSONObject json = JSONObject.fromObject(rs);
		ConstantUtil.reJSON(json.toString(), req, resp);
		
	}
	
	
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
