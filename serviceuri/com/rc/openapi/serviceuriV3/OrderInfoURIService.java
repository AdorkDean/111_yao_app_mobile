package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rc.openapi.model.OrderPaymethodVO;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TOrderManager;
import com.rc.openapi.serviceuri.BaseURIService;

public class OrderInfoURIService extends BaseURIService {
	private static final long serialVersionUID = -6755371914959876989L;
	private Logger log = Logger.getLogger(getClass());
	private OpenSqlManage opensqlmanage;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String orderId = req.getParameter("orderid");
		String sessionId = req.getParameter("sessionId");
		List<Map> orderItemList= null;
		try {
			opensqlmanage = (OpenSqlManage) getSpringBean("opensqlmanage");
			TOrderManager tordermanager = (TOrderManager)getSpringBean("tordermanager");
			
			Map m = new HashMap();
			m.put("orderid", new Long(orderId));
			Map  map = (Map) opensqlmanage.selectForObjectByMap(m,
					"t_order.get-order-info");
			req.setAttribute("order", map);
			orderItemList = opensqlmanage.selectForListByMap(m,
					"t_order.get-order-info-item");
			if(String.valueOf(map.get("order_status")).equals("0")){
				List<OrderPaymethodVO> list = tordermanager.getOrderPaymethodVOByOrderId(new Long(orderId));
				List<OrderPaymethodVO> paymethodList = new ArrayList<OrderPaymethodVO>();
				if(list!=null&&list.size()>0){
					for(OrderPaymethodVO paymethod:list){
						if(!paymethod.getPaypluginId().equals("4")){
							paymethodList.add(paymethod);
						}
					}
				}
			    req.setAttribute("list", paymethodList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("itemList", orderItemList);
		req.setAttribute("sessionId", sessionId);
		req.getRequestDispatcher("/page/order_details_V3.jsp").forward(req, resp);
	}


}
