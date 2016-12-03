package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.service.impl.TOrderManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.NumberUtil;

/**
 * 购物车结算  优惠券列表
 * @author user3
 *
 */
public class OrderCouponCodeListService extends BaseURIService{
	private static final long serialVersionUID = 8649733127155267695L;
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
//		String paymentMethodId = request.getParameter("paymentMethodId");//支付方式
//		String receiverId = request.getParameter("receiverId");//收货人地址
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = "1";
		List<Map<String,String>> couponList = new ArrayList<Map<String,String>>();
		if (sessionId != null && !"".equals(sessionId)) {
			try {
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if (userId == -1l) {
					statusCode = "3";// 非法用户
				} else {
					TOrderManagerImpl tordermanager = (TOrderManagerImpl) getSpringBean("tordermanager");
					List<Map<String, Object>> couponnewList = tordermanager.getCouponByGoods(String.valueOf(userId));
					Map<String,String> couponMap = null;
					if(couponnewList!=null&&couponnewList.size()>0){
						for(Map<String, Object> coupon:couponnewList){
							couponMap=new HashMap<String,String>();
							couponMap.put("id", String.valueOf(coupon.get("id")));
							couponMap.put("minimum_price",NumberUtil.format2String(((BigDecimal)coupon.get("start_price")).setScale(2, RoundingMode.HALF_UP)));
							couponMap.put("name", String.valueOf(coupon.get("name")));
							couponMap.put("begin_Date", String.valueOf(coupon.get("begin_Date")));
							couponMap.put("end_date", String.valueOf(coupon.get("end_date")));
							couponList.add(couponMap);
						}
					}
				}
			} catch (Exception e) {
				statusCode = "0";// 失败
				e.printStackTrace();
			}
		} else {
			statusCode = "2";// sessionId 不能为空
		}
		map.put("statusCode", statusCode);
		map.put("couponList", couponList);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	
	
	
	
}
