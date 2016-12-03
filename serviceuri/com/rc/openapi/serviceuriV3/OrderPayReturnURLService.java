package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.impl.TOrderManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.vo.TOrder;
import com.rc.openapi.vo.TOrderExample;
/**
 * 支付结果 同步回调
 * @author user3
 *
 */
public class OrderPayReturnURLService extends BaseURIService{
	private static final long serialVersionUID = -4009753741413218665L;
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ordersn ="";
		String orderId ="";
		String paySuccessFlag="0";  //是否支付成功   0 支付处理中   1 支付成功
		if(StringUtils.hasText(request.getParameter("serialNo"))){//医卡通
			ordersn =request.getParameter("serialNo");
		}
		TOrderManagerImpl tordermanager = (TOrderManagerImpl) getSpringBean("tordermanager");
		TOrderExample orderExample = new TOrderExample(); 
		orderExample.createCriteria().andOrderSnEqualTo(ordersn);
		try {
			List<TOrder> orderList =tordermanager.selectByExample(orderExample);
			if(orderList!=null&&orderList.size()>0){
				orderId = String.valueOf(orderList.get(0).getId());
				if(orderList.get(0).getOrderStatus().intValue()==1){//支付成功
					paySuccessFlag="1";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("orderId", orderId);
		request.setAttribute("paySuccessFlag",paySuccessFlag);
		request.getRequestDispatcher("/page/pay_return.jsp").forward(request, response);
	}
	
}
