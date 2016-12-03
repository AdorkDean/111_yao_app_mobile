package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.payplugin.yktpay.PayWapYktPlugin;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TOrderManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.vo.TOrder;
/**
 * 订单采用医卡通支付跳转到医卡通支付页面
 * @author user3
 *
 */
public class YktPayUrlService  extends BaseURIService{
	private static final long serialVersionUID = 6442441838204452857L;
    
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		String orderId =  request.getParameter("orderId");//订单id
		if(StringUtils.hasText(orderId)){
			TOrderManagerImpl tordermanager = (TOrderManagerImpl) getSpringBean("tordermanager");
			try {
				TOrder order = tordermanager.selectByPrimaryKey(Long.valueOf(orderId.trim()));
				OpenSqlManageImpl opensqlmanage = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
				Map<String, Object> parMap = new HashMap<String, Object>();
				parMap.put("orderId", order.getId());
				List<Map<String,Object>> orderItemList =opensqlmanage.selectForListByMap(parMap, "t_order_item.selectOrderItemGoodsByOrderid");
				request.setAttribute("items", orderItemList);
				BigDecimal shouldAmount = new BigDecimal(0);
				for (Map<String,Object> oi : orderItemList) {
					shouldAmount = shouldAmount.add(((BigDecimal)oi.get("price")).multiply(new BigDecimal((Integer)oi.get("quantity"))));
				}
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				// 减免金额
				parameterMap.put("costAdjust",order.getPayableAmount().subtract(shouldAmount));
				PayWapYktPlugin payWapYkt = new PayWapYktPlugin();
				parameterMap.putAll(payWapYkt.getParameterMap(order,order.getOrderSn(), request));
				request.setAttribute("parameterMap", parameterMap);
				request.setAttribute("requestUrl", payWapYkt.getRequestUrl());
				request.setAttribute("requestMethod", payWapYkt.getRequestMethod());
				request.setAttribute("requestCharset", payWapYkt.getRequestCharset());
				request.getRequestDispatcher("/page/pay_submit.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
      
	}
	
	
	
	
}
