package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.CPaymentWayManager;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TCouponCardManager;
import com.rc.openapi.service.TCouponManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.CDeliveryWayManagerImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.service.impl.TOrderManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.NumberUtil;
import com.rc.openapi.vo.CDeliveryWay;
import com.rc.openapi.vo.CPaymentWay;
import com.rc.openapi.vo.TCoupon;
import com.rc.openapi.vo.TCouponCard;
import com.rc.openapi.vo.TOrder;

/**
 * 重新计算已存在订单的运费
 * @author user3
 *
 */
public class OrderFreightService extends BaseURIService{
	private static final long serialVersionUID = -8743549259664531236L;
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		String orderId =  request.getParameter("orderId");//订单id
		String paymentmethodId = request.getParameter("paymentmethodId");//支付方式id
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = "";
		BigDecimal freight = new BigDecimal("0");//运费
		BigDecimal amountPayable =  new BigDecimal("0"); //应付金额
		BigDecimal price = new BigDecimal("0"); //商品金额
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
						boolean feeflag = false;
						if(order.getCouponId()!=null){
							TCouponCardManager  tcouponcardmanager=(TCouponCardManager) getSpringBean("tcouponcardmanager");
							TCouponCard couponCard = tcouponcardmanager.selectByPrimaryKey(order.getCouponId());
							if(couponCard!=null&&couponCard.getTicketId()!=null){
								TCouponManager tcouponmanager=(TCouponManager) getSpringBean("tcouponmanager");
								TCoupon tcoupon =tcouponmanager.selectByPrimaryKey(couponCard.getTicketId());
								if(tcoupon!=null&&tcoupon.getScope()!=null&&tcoupon.getScope().intValue()==1){//免运费
									feeflag = true;
								}
							}
						}
						    BigDecimal weight = new BigDecimal("0");
							Map<String,Object> paramMap = new HashMap<String,Object>(); 
						    CPaymentWayManager cpaymentwaymanager = (CPaymentWayManager)getSpringBean("cpaymentwaymanager");
						    OpenSqlManage opensqlmanage =(OpenSqlManage)getSpringBean("opensqlmanage");
						    paramMap.put("orderId", Long.valueOf(orderId.trim()));
						    List orderPirceWeightlist =opensqlmanage.selectForListByMap(paramMap, "t_order.selectGoodsPriceWeightByorderID");
						    if(orderPirceWeightlist!=null&&orderPirceWeightlist.size()>0){
						    	Map<String,Object> resultMap = (Map<String, Object>) orderPirceWeightlist.get(0);
						    	if(resultMap.get("pricesum")!=null){
						    		price =(BigDecimal) resultMap.get("pricesum");
						    	}
						    	if(resultMap.get("weightsum")!=null){
						    		weight=(BigDecimal) resultMap.get("weightsum");
						    	}
						    }
						    
						    
						    
						    CPaymentWay paymentWay=	cpaymentwaymanager.selectByPrimaryKey(Long.valueOf(paymentmethodId.trim()));
						    String ysdjFlag ="0";// 0否   1 是
					       	 CDeliveryWay deliveryWay = new CDeliveryWay();
					       	 if(order.getDeliveryId()!=null){//配送方式id
					       		CDeliveryWayManagerImpl cdeliverywaymanager = (CDeliveryWayManagerImpl)getSpringBean("cdeliverywaymanager");
					       		 deliveryWay= cdeliverywaymanager.selectByPrimaryKey(Long.valueOf(order.getDeliveryId()));
					       		 if(deliveryWay.getDeliveryCode().equals("ysdj")){//药师到家
					       			 ysdjFlag ="1";
					       			freight=deliveryWay.calculateFreight(weight);
					       		 }
					       	 }
					       	 if(ysdjFlag.equals("0")){//不是 药师到家配送方式
					       		//计算全场免运费金额
					       		 if(!feeflag){
					       			TSysParameterManager tsysparametermanager =(TSysParameterManager)getSpringBean("tsysparametermanager");
							    	String free_shippingfee_price =tsysparametermanager.getKeys("free_shippingfee_price");
					 		    	if(StringUtils.hasText(free_shippingfee_price)){
					 		    		if(price.compareTo(new BigDecimal(free_shippingfee_price.trim()))>=0){
					 		    			feeflag=true;
					 		    		}
					 		    	}
					 		      }
					        	String deliveryCode="ptkd";//普通快递
					        	
					        	 TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
								 String ysd_ysdj_areaid =sys.getKeys("deliveryway_ysd_ysdj_areaid");//配送方式-药士达-药师到家-支持区域id
								 String	hdfk_area = sys.getKeys("paymethod_hdfk_area");
								 
								 boolean ysd_ysdj_areaid_flag = false;
							 		for(int i=0;i<ysd_ysdj_areaid.split(",").length;i++){
							 			if(ysd_ysdj_areaid.split(",")[i].equals(String.valueOf(order.getAreaId()))){
							 				ysd_ysdj_areaid_flag= true;
							 			}
							 		}
					    		 if(ysd_ysdj_areaid_flag){//判断望京   是望京
					    			 deliveryCode="ysd";//药士达
					    		 }else if(order.getAreaName().indexOf(hdfk_area)!=-1){//判断是否是北京   
					 				if(paymentWay.getPaymentCode().equals("hdfk")){//货到付款
					 					deliveryCode="hdfk";
					 				}
					    		 }
					   	        paramMap.clear();
								paramMap.put("deliveryCode", deliveryCode);
							    List<CDeliveryWay> deliveryList =opensqlmanage.selectForListByMap(paramMap, "c_delivery_way.selectDeliveryWayByMap");
							    if(deliveryList!=null&&deliveryList.size()>0){
							    	deliveryWay = deliveryList.get(0);
							    	freight=deliveryWay.calculateFreight(weight);
							    }
					        	if(feeflag){//是否免运费
					        		freight = new BigDecimal("0");
					        	 }
					       	 }
						    
						 amountPayable = order.getPayableAmount();//订单应付金额
						 amountPayable =amountPayable.subtract(order.getShippingFee()).add(freight);
					     statusCode = "1";// 
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
		map.put("amountPayable",NumberUtil.format2String(amountPayable));
		map.put("price", NumberUtil.format2String(price));
		map.put("freight", NumberUtil.format2String(freight));
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

}
