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

public class CartGetSumURIService extends BaseURIService {

	private ICartManager cartmanager;
	private static java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00"); 
	private OpenSqlManage opensqlmanage;
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
		
		BigDecimal b = new BigDecimal(0);
		System.out.println(df.format(b));
		
	}
	
	
	@Override
	public  void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map back = new HashMap();
		JSONObject json = null;
		try{
			tmembermanager=(TMemberManager)getSpringBean("tmembermanager");
			cartmanager=(ICartManager)getSpringBean("cartmanager");
			opensqlmanage=(OpenSqlManage)getSpringBean("opensqlmanage");
			
			String sessionId = req.getParameter("sessionid");
			String uuid = req.getParameter("uuid");
			Map map = new HashMap();
			map.put("key", uuid);
			
			CartParam arg = new CartParam();
			arg.setCartkey(uuid);
			if("".equals(sessionId)) sessionId = null;
			if(sessionId!=null)
			{
				long userId = tmembermanager.getMemberId(sessionId);
				map.put("userid",userId);
			// 	cartmanager.synCart(userId, uuid);
			}
			
//			Integer cnt = (Integer)opensqlmanage.selectForObjectByMap(map, "mycart.get-cart-sum");
			
			Integer cnt = 0;
			List<java.lang.Integer> list = opensqlmanage.selectForListByMap(map, "mycart.get-cart-sum");
			if(list.size()>0){
				cnt = list.get(0);
			}
			
			/*List<HashMap> list = opensqlmanage.selectForListByMap(map, "mycart.get-cart-item");
			int cnt = 0;
			for(HashMap m : list){
				cnt = cnt + ((Integer)m.get("quantity"));
			}*/
			
			back.put("statusCode", 1+"");
			back.put("sum", cnt+"");
			json = JSONObject.fromObject(back);
		}catch(Exception e){
			back.put("statusCode", "0");
			e.printStackTrace();
		}
		ConstantUtil.reJSON(json.toString(), req, resp);
		
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		doPost(req, resp);
		
	}
	

}
