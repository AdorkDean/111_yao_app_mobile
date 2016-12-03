package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.util.StringUtils;

import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.service.impl.TOrderFlowManagerImpl;
import com.rc.openapi.service.impl.TOrderManagerImpl;
import com.rc.openapi.util.ConnectionUtil;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
import com.rc.openapi.vo.TOrder;
import com.rc.openapi.vo.TOrderFlow;
import com.rc.openapi.vo.TOrderFlowExample;

/**
 * 微信支付查询订单
 * @author user3
 *
 */
public class OrderQueryResultService extends BaseURIService{

	private static final long serialVersionUID = -4813231151711776388L;
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
        		TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if (userId == -1l) {
					statusCode = "3";// 非法用户
				} else {
					TOrderManagerImpl dstordermanager = (TOrderManagerImpl) getSpringBean("tordermanager");
					TOrder order = dstordermanager.selectByPrimaryKey(Long.valueOf(orderId));
					if(order!=null&&order.getMemberId()!=null){
						if(order.getMemberId()!=userId){
							TOrderFlowManagerImpl torderflowmanager = (TOrderFlowManagerImpl) getSpringBean("torderflowmanager");
							TOrderFlowExample torderflowExample = new TOrderFlowExample();
							torderflowExample.createCriteria().andOrderIdEqualTo(order.getId());
							List<TOrderFlow> paymentList =torderflowmanager.selectByExample(torderflowExample);
							if(paymentList!=null&&paymentList.size()>0){
								if("wzfMobilePlugin".equals(paymentList.get(0).getPaymentPlugin())){
									String out_Order_sn = order.getOrderSn();
									String source="app111yao";
									String client_type = request.getParameter("client_type"); //客户端类型
								    String client_version = request.getParameter("client_version"); //客户端版本
								    if((client_type.equals("iOS")&&client_version.equals("v2.1.2"))||(client_type.equals("Android")&&client_version.equals("2.1.2"))){
								    	 source="newapp111yao";
								    }
									String postResult =ConnectionUtil.getContentFromUrl(InfoUtil.getInstance().getInfo("config", 
											"payment.wxQueryOrderService.url"), "outTradeNo="+out_Order_sn+"&source="+source);
									if(postResult.startsWith("true")){
										statusCode="1";//成功
									}else{
										statusCode="0";//失败
									}
								}else{
									statusCode="7";//该订单没有微信支付方式
								}
							}else{
								statusCode="6";//该订单无支付方式
							}
						}else{
							statusCode="5";//该订单不属于当前用户，非法请求
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
