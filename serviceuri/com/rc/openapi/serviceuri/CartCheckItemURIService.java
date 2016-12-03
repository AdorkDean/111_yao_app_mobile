package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * 更新购物车项
 * 
 * @author user00
 * 
 */
public class CartCheckItemURIService extends BaseURIService {

	private static final long serialVersionUID = 2885718438390896671L;

	private OpenSqlManage opensqlmanage;
	private ICartManager cartmanager;
	private TMemberManager tmembermanager;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		opensqlmanage = (OpenSqlManage) getSpringBean("opensqlmanage");
		cartmanager = (ICartManager) getSpringBean("cartmanager");
		tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
		String sessionId = req.getParameter("sessionid");
		String uuid = req.getParameter("uuid");
		String productId = req.getParameter("productId");
		String flag = req.getParameter("flag");
		String statusCode = "1";
		if("".equals(sessionId)) sessionId = null;
		
		try {
			
			CartParam arg = new CartParam();
			arg.setCartkey(uuid);
			Map map = new HashMap();
			map.put("flag", flag);
			map.put("uuid",uuid);
			
			Long userId = 0L;
			if(sessionId!=null)
			{
				userId = tmembermanager.getMemberId(sessionId);
				arg.setUserId(userId);
				arg.setIslogin(true);
				map.put("userid", userId);
			}
			
			List<Long> proLongs = new ArrayList<Long>();
			String[] str = productId.trim().split(",");
			for (String string : str) {
				if(string != null && !"".equals(string.trim())){
					proLongs.add(Long.valueOf(string));
				}
			}
			
			int n =0;
			for(long id : proLongs ){
				map.put("pid", id);
				n = opensqlmanage.updateByMap_drug(map, "mycart.check-cart-item");
			}
		    
			Map result = cartmanager.accounts(arg);
			BigDecimal heji= (BigDecimal)result.get("money");
			result.put("statusCode",statusCode);
			result.put("heji", CartGetListURIService.df.format(heji));
			JSONObject json = JSONObject.fromObject(result);
			ConstantUtil.reJSON(json.toString(), req, resp);
			    
		}catch(Exception e){
			statusCode = "0";
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	public OpenSqlManage getOpensqlmanage() {
		return opensqlmanage;
	}

	public void setOpensqlmanage(OpenSqlManage opensqlmanage) {
		this.opensqlmanage = opensqlmanage;
	}
	
	public static void main(String[] args) {
		String id=",264,222,";
		System.out.println(id.subSequence(0, id.length()-1));
	}

}
