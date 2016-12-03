package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.openapi.model.cart.CartParam;
import com.rc.openapi.service.ICartManager;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.util.ConstantUtil;

/**
 * 删除购物车项
 * 
 * @author user00
 * 
 */
public class CartDelItemURIService extends BaseURIService {

	private static final long serialVersionUID = 6185969883988053936L;
	private OpenSqlManage opensqlmanage;
	private ICartManager cartmanager;
	private TMemberManager tmembermanager;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		Map rs = new HashMap();
		opensqlmanage = (OpenSqlManage) getSpringBean("opensqlmanage");
		cartmanager = (ICartManager) getSpringBean("cartmanager");
		tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
		
	
		
		String productId = request.getParameter("productId");
		String sessionId = request.getParameter("sessionid");
		String uuid = request.getParameter("uuid");
		
		System.out.println("===============>"+productId);
		
		
		Map map = new HashMap();
		map.put("uuid", uuid);
		map.put("productId", productId);
		
		CartParam arg = new CartParam();
		arg.setCartkey(uuid);
		
		try {
				
			boolean islogin = true;  // 默认登录
			if("".equals(sessionId)) sessionId = null;
			if(sessionId!=null)
			{
				long userId = tmembermanager.getMemberId(sessionId);
				map.put("userid",userId);
				cartmanager.synCart(userId, uuid);
				arg.setUserId(userId);
				arg.setIslogin(true);
				opensqlmanage.updateByMap_drug(map,"mycart.delete-by-user");
			}
			
			opensqlmanage.updateByMap_drug(map,"mycart.delete-by-uuid");
		    rs = cartmanager.accounts(arg);
		    BigDecimal money = (BigDecimal)rs.get("money");
		    
			Map res = new HashMap();
			res.put("statusCode", "1");
			res.put("heji", CartGetListURIService.df.format(money));
			res.put("point",money.intValue());
			ConstantUtil.reJSON(JSONObject.fromObject(res).toString(), request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
			ConstantUtil.reJSON("{\"statusCode\":\"" + 0 + "\"}", request, response);
		}
		
	
		
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public OpenSqlManage getOpensqlmanage() {
		return opensqlmanage;
	}

	public void setOpensqlmanage(OpenSqlManage opensqlmanage) {
		this.opensqlmanage = opensqlmanage;
	}

}
