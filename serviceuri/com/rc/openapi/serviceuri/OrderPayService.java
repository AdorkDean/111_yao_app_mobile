package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.service.impl.TOrderManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TOrder;

/**
 * 未支付订单 支付接口
 * @author user3
 *
 */
public class OrderPayService extends BaseURIService{
	private static final long serialVersionUID = -3966597799007619338L;
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		String orderId =  request.getParameter("orderId");//订单id
		String paymentPluginId = request.getParameter("paymentPluginId");//支付插件id
		if("4".equals(paymentPluginId)){
			paymentPluginId=null;
		}
		String paymentmethodId = request.getParameter("paymentmethodId");//支付方式id
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = "";
		String payType ="";
		Object obj = null;
        if(StringUtils.hasText(sessionId)&&StringUtils.hasText(orderId)&&StringUtils.hasText(paymentmethodId)){
        	try{
        		TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if (userId == -1l) {
					statusCode = "3";// 非法用户
				} else {
					TOrderManagerImpl tordermanager = (TOrderManagerImpl) getSpringBean("tordermanager");
					TOrder order = tordermanager.selectByPrimaryKey(Long.valueOf(orderId));
					if(order!=null&&order.getMemberId()!=null){
						if (order == null) {
							statusCode = "0";
						}else if (order.getOrderStatus()!=0) {//待支付
							statusCode = "0";
						}else if (order.getPayableAmount().compareTo(new BigDecimal(0)) <= 0) {
							statusCode = "0";
						}else if(userId.longValue()!=order.getMemberId().longValue()){
							statusCode = "0";
						}else{
							obj =tordermanager.updateOrderPayment(order, userId, Long.valueOf(paymentmethodId), paymentPluginId, request);
							if("wzfMobilePlugin".equals(paymentPluginId)){//微信
								payType="2";//
							}else if("alipayMobilePlugin".equals(paymentPluginId)){//支付
								payType="1";//
							}
							//FIXME WWF(秀粉支付完成之后，给领袖发送提示短信)
							
							
//							MessageSender topicMessageSender = (MessageSender) getSpringBean("topicMessageSender");
//							TMemberLeaderManager tMemberLeaderManager = (TMemberLeaderManager) getSpringBean("tmemberleadermanager");
//							//t_member_leader.selectLeaderPhoneByXiufenId
//							Map<String, Object> phoneMap = tMemberLeaderManager.selectLeaderPhoneByXiufenId(order.getMemberId());
//							
//							String phone = null;
//							if (phoneMap != null && phoneMap.get("m_phone") != null
//									&& !"".equals(phoneMap.get("m_phone").toString())) {
//								phone = phoneMap.get("m_phone").toString();
//							} else if (phoneMap != null && phoneMap.get("l_phone") != null
//									&& !"".equals(phoneMap.get("l_phone").toString())) {
//								phone = phoneMap.get("l_phone").toString();
//							}
//							if (phone != null && !"".equals(phone)) {
//								Map<String, String> messageMap = new HashMap<String, String>();
//								messageMap.put("mobiles", phone);
//								messageMap.put("version", "v6");
//								messageMap.put("smsContent", "领秀" + phoneMap.get("nick_name")
//										+ "您好，您有秀粉产生了订单返佣，请到待入账金额中查看！");
//								topicMessageSender.sendMapMessage(messageMap);
//							}
							
							statusCode = "1";// 
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
        map.put("payType", payType);
		map.put("statusCode", statusCode);
		if(StringUtils.hasText(paymentPluginId)&&"1".equals(statusCode)){
			map.put("requestVO", obj);
		}
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	
	
	
	
}
