package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.model.OrderAddVO;
import com.rc.openapi.model.OrderCartVO;
import com.rc.openapi.model.OrderGoodCart;
import com.rc.openapi.model.OrderPaymethodVO;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.service.impl.TOrderManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.NumberUtil;

/**
 * 购物车提交 跳转到订单添加页面
 * @author user3
 *
 */
public class AddOrderService extends BaseURIService {
	private static final long serialVersionUID = 6112619709438176038L;
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		String paymentMethodId = request.getParameter("paymentMethodId");//支付方式
		String receiverId = request.getParameter("receiverId");//收货人地址
		String couponCodeId = request.getParameter("couponCodeId");//优惠券id
		String deliveryId = request.getParameter("deliveryId");//配送方式id
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = "";
		OrderCartVO orderCart = null;
		String errorMessage ="";
		if (sessionId != null && !"".equals(sessionId)) {
			try {
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if (userId == -1l) {
					statusCode = "3";// 非法用户
				} else {
					OpenSqlManageImpl opensqlmanage = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("memberId", userId);
					paramMap.put("ifPremiums", 0);
					List<OrderGoodCart> goodsList = opensqlmanage.selectForListByMap(paramMap,"t_goods.selectGoodsByCartMemberid");
					//判断商品库存是否充足
					for(OrderGoodCart orderGoodCart:goodsList){
						if(orderGoodCart.getStquan()<0){//判断库存是否充足
							statusCode="5";//库存不足
							errorMessage=errorMessage+orderGoodCart.getGoodsName()+";";
						}
					}
					if(statusCode==""){
						TOrderManagerImpl tordermanager = (TOrderManagerImpl) getSpringBean("tordermanager");
						OrderAddVO orderVO =tordermanager.getOrderPromotionDiscount(goodsList,userId, NumberUtil.string2Long(paymentMethodId), 
								NumberUtil.string2Long(receiverId),NumberUtil.string2Long(couponCodeId),deliveryId);
						orderCart = new OrderCartVO(orderVO);
						if(orderCart.getPaymethodList()!=null&&orderCart.getPaymethodList().size()>0){
							List<OrderPaymethodVO> paymethodList = new ArrayList<OrderPaymethodVO>();
							for(OrderPaymethodVO paymethod:orderCart.getPaymethodList()){
								if(!paymethod.getPaypluginId().equals("payWapYktPlugin")){
									paymethodList.add(paymethod);
								}
							}
							orderCart.setPaymethodList(paymethodList);
						}
						statusCode = "1";// 成功
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
		map.put("errorMessage", errorMessage);
		if("1".equals(statusCode)){
			//药师到家：执业药师上门，用药指导、健康咨询、身体诊查一网打尽！体验价仅需9.9元！
			orderCart.setDeliveryInstro("药师到家：执业药师上门，用药指导、健康咨询、身体诊查一网打尽！体验价仅需9.9元！");
			map.put("orderCart", orderCart);
		}else if("5".equals(statusCode)){
			map.put("errorMessage", "商品:"+errorMessage.substring(0, errorMessage.length()-1)+"库存不足,下次记得再眼疾手快一点哦！！");
		}
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	
	
	
	
	
	
	
	
}
