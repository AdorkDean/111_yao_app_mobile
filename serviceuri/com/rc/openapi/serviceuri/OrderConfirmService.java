package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.TOrderManager;
import com.rc.openapi.service.impl.TLeaderStayMoneyManagerImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.service.impl.TSysParameterManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TOrder;

/**
 * 确认 订单 支付接口
 * @author user3
 *
 */
public class OrderConfirmService extends BaseURIService{
	private static final long serialVersionUID = -3966597193007619338L;
    
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		String orderId =  request.getParameter("orderId");//订单id
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = "";
        if(StringUtils.hasText(sessionId)&&StringUtils.hasText(orderId)){
        	try{
        		TMemberManagerImpl tmembermanager = (TMemberManagerImpl)getSpringBean("tmembermanager");
        		TLeaderStayMoneyManagerImpl tleaderstaymoneymanager = (TLeaderStayMoneyManagerImpl)getSpringBean("tleaderstaymoneymanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if (userId == -1l) {
					statusCode = "3";// 非法用户
				} else {
					TOrderManager tordermanager = (TOrderManager)getSpringBean("tordermanager");
//					OpenSqlManage opensqlmanage = (OpenSqlManage) getSpringBean("opensqlmanage");
					TSysParameterManagerImpl tsysparametermanager = (TSysParameterManagerImpl) getSpringBean("tsysparametermanager");
					TOrder order = tordermanager.selectByPrimaryKey(Long.valueOf(orderId));
					if(order!=null && order.getMemberId()!=null ){
						if(order.getMemberId().equals(userId) && order.getOrderStatus()==2){
							String publicServiceUrl = tsysparametermanager.getKeys("public_service_url");
							tordermanager.finishOrder(order,publicServiceUrl);
							statusCode = "1";
						}else{
							statusCode = "3";//非法用户
						}
					}else{
						statusCode = "4";// 订单不存在
					}
				}
        	}catch(Exception e){
        		statusCode = "0";// 失败
        		e.printStackTrace();
        	}
        }else{
        	statusCode="2";//参数
        }
		map.put("statusCode", statusCode);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
}
