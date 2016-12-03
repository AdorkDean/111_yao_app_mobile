package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.model.OrderAddVO;
import com.rc.openapi.model.OrderGoodCart;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.service.impl.TOrderManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.NumberUtil;

/**
 * 创建订单
 * @author user3
 *
 */
public class CreateOrderService extends BaseURIService{
	private static final long serialVersionUID = -2863385764710134443L;
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		String paymentmethodIds = request.getParameter("paymentMethodId");//支付方式
		String couponCodeIds = request.getParameter("couponCodeId");//使用优惠券id
		String receiverIds = request.getParameter("receiverId");//收货地址
		String paymentPluginId = request.getParameter("paymentPluginId");//支付插件id
		String source = request.getParameter("source");//订单来源   app 2 安卓     ios 5 苹果
		String deliveryId = request.getParameter("deliveryId");//配送方式id
		if("4".equals(paymentPluginId)||"4".equals(paymentmethodIds)){
			paymentPluginId=null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = "";
		String payType ="";
		String errorMessage ="";
		Object obj = null;
		if (sessionId != null && !"".equals(sessionId)) {
			try {
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long memberId = tmembermanager.getMemberId(sessionId);
				if (memberId == -1l) {
					statusCode = "3";// 非法用户
				} else {
					Long paymentmethodId = null;
					if(StringUtils.hasText(paymentmethodIds)){
						paymentmethodId=Long.valueOf(paymentmethodIds);
					}else{
						statusCode="4";
						log.info("==创建订单：paymentmethodIds 支付方式不能为空");
					}
					Long receiverId = null;
					if(StringUtils.hasText(receiverIds)){
						receiverId=Long.valueOf(receiverIds);
					}else{
						statusCode="4";
						log.info("==创建订单：receiverIds 收货地址不能为空");
					}
					if(!StringUtils.hasText(source)){
						statusCode="4";
						log.info("==创建订单：source 订单来源 不能为空");
					}
					
					if("wzfMobilePlugin".equals(paymentPluginId)){//微信
						payType="2";//
					}else if("alipayMobilePlugin".equals(paymentPluginId)){//支付
						payType="1";//
					}
					if("".equals(statusCode)){
						OpenSqlManageImpl opensqlmanage = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("memberId", memberId);
						paramMap.put("ifPremiums", 0);
						List<OrderGoodCart> goodsList = opensqlmanage.selectForListByMap(paramMap,"t_goods.selectGoodsByCartMemberid");
						if(goodsList==null||(goodsList!=null&&goodsList.size()==0)){
							statusCode ="0";
							errorMessage="购物车商品为空,请重新选择所购买商品!";
						}
						//判断商品库存是否充足
						for(OrderGoodCart orderGoodCart:goodsList){
							if(orderGoodCart.getStquan()<0){//判断库存是否充足
								statusCode="5";//库存不足
								errorMessage=errorMessage+orderGoodCart.getGoodsName()+";";
								log.info("======>手机端提交订单"+orderGoodCart.getGoodsName()+"库存不足！");
							}
						}
						//判断商品是否处方药
						for(OrderGoodCart orderGoodCart:goodsList){
							if(orderGoodCart.getType().intValue()==3){//非购买处方药
								errorMessage=errorMessage+orderGoodCart.getGoodsName()+";";
								statusCode="0";
								log.info("======>手机端提交订单"+orderGoodCart.getGoodsName()+"为不可加入购物车的处方药！");
							}
						}
						//判断商品是否下架
						for(OrderGoodCart orderGoodCart:goodsList){
							if(orderGoodCart.getAppStatus().intValue()==2){//判断商品是否下架
								errorMessage=errorMessage+orderGoodCart.getGoodsName()+";";
								statusCode="0";
								log.info("======>手机端提交订单"+orderGoodCart.getGoodsName()+"商品已经下架，不能结算！");
							}
						}
						TOrderManagerImpl tordermanager = (TOrderManagerImpl) getSpringBean("tordermanager");
						if("".equals(statusCode)){
							OrderAddVO orderVO =tordermanager.getOrderPromotionDiscount(goodsList,memberId, NumberUtil.string2Long(paymentmethodIds),NumberUtil.string2Long(receiverIds),NumberUtil.string2Long(couponCodeIds),deliveryId);
							String wjarea ="0";//是否属于望京  0 不属于  1 属于
							 TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
							 String ysd_ysdj_areaid =sys.getKeys("deliveryway_ysd_ysdj_areaid");//配送方式-药士达-药师到家-支持区域id
							
							 boolean ysd_ysdj_areaid_flag = false;
						 		for(int i=0;i<ysd_ysdj_areaid.split(",").length;i++){
						 			if(ysd_ysdj_areaid.split(",")[i].equals(orderVO.getReceiver().getArea())){
						 				ysd_ysdj_areaid_flag= true;
						 			}
						 		}
							 
							 if(ysd_ysdj_areaid_flag){//判断是否属于望京
								wjarea="1";
							}
							for (OrderGoodCart orderGoodCart:goodsList) {
								if(orderGoodCart.getType().intValue()==2&&("0".equals(wjarea)||!"4".equals(paymentmethodIds))){//非购买处方药
									errorMessage=errorMessage+orderGoodCart.getGoodsName()+";";
									statusCode="0";
									log.info("======>手机端提交订单"+orderGoodCart.getGoodsName()+"为处方药，目前处方药仅支持北京部分地区以货到付款的方式购买！");
								}
							}
							if(statusCode.equals("")){
								long start = System.currentTimeMillis();
								obj =tordermanager.createOrder(orderVO, memberId, paymentmethodId, receiverId, 
										NumberUtil.string2Long(couponCodeIds), null, null, paymentPluginId,source,false,request);
								statusCode="1";
							    long end =System.currentTimeMillis();
							    log.info("创建订单耗时==================》"+String.valueOf(end-start)+"毫秒");
								
							}	
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
		map.put("payType", payType);
		map.put("statusCode", statusCode);
		map.put("errorMessage", errorMessage);
		if(StringUtils.hasText(paymentPluginId)&&"1".equals(statusCode)){
			map.put("requestVO", obj);
		}else if("5".equals(statusCode)){
			log.info("商品:"+errorMessage.substring(0, errorMessage.length()-1)+"库存不足,下次记得再眼疾手快一点哦！！");
			map.put("errorMessage", "商品:"+errorMessage.substring(0, errorMessage.length()-1)+"库存不足,下次记得再眼疾手快一点哦！！");
		}
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
}
