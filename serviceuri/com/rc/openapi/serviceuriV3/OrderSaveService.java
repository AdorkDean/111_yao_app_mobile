package com.rc.openapi.serviceuriV3;

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

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.model.OrderGoodCart;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.service.impl.TMemberReceiverLatLonManagerImpl;
import com.rc.openapi.service.impl.TMemberReceiverManagerImpl;
import com.rc.openapi.service.impl.TOrderManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TMemberReceiverLatLon;
import com.rc.openapi.vo.TOrder;
/**
 * 提交订单 
 * @author user3
 *
 */
public class OrderSaveService extends BaseURIService{
	private static final long serialVersionUID = 3044564771410165733L;
    
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		String couponCodeId = request.getParameter("couponCodeId");//使用优惠券id
		String receiverId = request.getParameter("receiverId");//收货地址
		String source = request.getParameter("source");//订单来源   app 2 安卓     ios 5 苹果
		String isInvoice = request.getParameter("isInvoice");//是否开具发票  0 不开   1 开
		String invoiceTitle = request.getParameter("invoiceTitle");//发票抬头
		String deliveryId = request.getParameter("deliveryId");//配送方式id
		String paymentmethodId = request.getParameter("paymentmethodId");//支付方式
		String invoiceType = request.getParameter("invoiceType");//发票类型
		String remark = request.getParameter("remark");//备注
		String receiverNewFlag =  request.getParameter("receiverNewFlag");//使用新的收货地址标记   Flag  1 表示是 
		String platform = "3";//平台参数
		Map<String, Object> resultmap = new HashMap<String, Object>();
		String statusCode = "";
		boolean checkFlag = true;
		String errorMessage ="";
		String payFlag ="1";//跳转支付标记   1  跳转支付  2 不跳转支付
		String orderId="";
		if (sessionId != null && !"".equals(sessionId)) {
			try {
				TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long memberId = tmembermanager.getMemberId(sessionId);
				if (memberId == -1l) {
					statusCode = "3";// 非法用户
					errorMessage="无效用户";
				} else {
					if(!StringUtils.hasText(paymentmethodId)&&checkFlag){
						statusCode="4";
						log.info("==V2创建订单：paymentmethodIds 支付方式不能为空");
						errorMessage="请选择支付方式";
						checkFlag=false;
					}
					if(!StringUtils.hasText(receiverId)&&checkFlag){
						statusCode="4";
						log.info("==V2创建订单：receiverIds 收货地址不能为空");
						errorMessage="请选择收货地址";
						checkFlag=false;
					}
					if(!StringUtils.hasText(deliveryId)&&checkFlag){
						statusCode="4";
						log.info("==V2创建订单：deliveryId 配送方式不能为空");
						errorMessage="请选择配送方式";
						checkFlag=false;
					}
					if(!StringUtils.hasText(source)&&checkFlag){
						statusCode="4";
						log.info("==V2创建订单：source 订单来源 不能为空");
						errorMessage="订单来源 不能为空";
						checkFlag=false;
					}
					if("1".equals(isInvoice)&&checkFlag){//开发票
						if(!StringUtils.hasText(invoiceTitle)){
							statusCode="4";
							log.info("==V2创建订单：invoiceTitle 发票抬头不能为空");
							errorMessage="发票抬头不能为空";
							checkFlag=false;
						}
						invoiceType="1";//明细
					}else{
						invoiceTitle =null;
					}
					if(checkFlag){
						OpenSqlManageImpl opensqlmanage = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("memberId", memberId);
						paramMap.put("ifPremiums", 0);
						List<OrderGoodCart> goodsList = opensqlmanage.selectForListByMap(paramMap,"t_goods.selectGoodsByCartMemberid");
						if(goodsList==null||(goodsList!=null&&goodsList.size()==0)){
							checkFlag = false;
							statusCode ="0";
							errorMessage="购物车商品为空,请重新选择所购买商品!";
						}
						if(checkFlag){
							//判断商品是否下架
							if(null != goodsList && goodsList.size()>0){
								for(OrderGoodCart orderGoodCart:goodsList){
									if(orderGoodCart.getAppStatus().intValue()==2){//判断商品是否下架
										errorMessage=errorMessage+orderGoodCart.getGoodsName()+";";
										statusCode="0";
										log.info("======>手机端提交订单"+orderGoodCart.getGoodsName()+"商品已经下架，不能结算！");
										checkFlag = false;
									}
								}
								if(!checkFlag){
									errorMessage="商品:"+errorMessage+"已经下架,请返回购物车修改！";
								}
							}else{
								checkFlag = false;
								errorMessage="购物车中无商品！";
							}	
						}
						if(checkFlag){
							//判断商品库存是否充足
							for(OrderGoodCart orderGoodCart:goodsList){
								if(orderGoodCart.getStquan()<0){//判断库存是否充足
									statusCode="0";
									errorMessage=errorMessage+orderGoodCart.getGoodsName()+";";
									log.info("======>手机端提交订单"+orderGoodCart.getGoodsName()+"库存不足！");
									checkFlag = false;
								}
							}
							if(!checkFlag){
								errorMessage="商品:"+errorMessage+"库存不足,下次记得再眼疾手快一点哦！！";
							}
						}
						if(checkFlag){
							//判断商品是否处方药
							for(OrderGoodCart orderGoodCart:goodsList){
								if(orderGoodCart.getType().intValue()==3){//非购买处方药
									errorMessage=errorMessage+orderGoodCart.getGoodsName()+";";
									statusCode="0";
									log.info("======>手机端提交订单"+orderGoodCart.getGoodsName()+"为不可加入购物车的处方药！");
									checkFlag = false;
								}
							}
							if(!checkFlag){
								errorMessage="商品:"+errorMessage+"为处方药不可购买,请返回购物车修改！";
							}
						}
						if(checkFlag){
							String wjarea ="0";//是否属于望京  0 不属于  1 属于
							boolean ysd_ysdj_areaid_flag = false;
							if(!"1".equals(receiverNewFlag)){
								TMemberReceiverManagerImpl tmemberreceivermanager = (TMemberReceiverManagerImpl) getSpringBean("tmemberreceivermanager");
								String ysd_ysdj_areaid =sys.getKeys("deliveryway_ysd_ysdj_areaid");//配送方式-药士达-药师到家-支持区域id
						 		for(int i=0;i<ysd_ysdj_areaid.split(",").length;i++){
						 			if(ysd_ysdj_areaid.split(",")[i].equals(tmemberreceivermanager.selectByPrimaryKey(Long.valueOf(receiverId.trim())).getArea())){
						 				ysd_ysdj_areaid_flag= true;
						 			}
						 		}
							}else{
								TMemberReceiverLatLonManagerImpl tmemberreceiverlatlonmanager = (TMemberReceiverLatLonManagerImpl) getSpringBean("tmemberreceiverlatlonmanager");
								TMemberReceiverLatLon receiverLatLon =tmemberreceiverlatlonmanager.selectByPrimaryKey(Long.valueOf(receiverId.trim()));
								 if(receiverLatLon!=null&&StringUtils.hasText(receiverLatLon.getStoreId())){
			            			 ysd_ysdj_areaid_flag= true;
			            		 }
							}
							if(ysd_ysdj_areaid_flag){//判断是否属于实体店配送范围
								wjarea="1";
							}
							for (OrderGoodCart orderGoodCart:goodsList) {
								if(orderGoodCart.getType().intValue()==2&&("0".equals(wjarea)||!"4".equals(paymentmethodId))){//非购买处方药
									errorMessage=errorMessage+orderGoodCart.getGoodsName()+";";
									statusCode="0";
									log.info("======>手机端提交订单"+orderGoodCart.getGoodsName()+"为处方药，并且收货地址不是望京货不是货到付款！");
									checkFlag = false;
								}
							}
							if(!checkFlag){
								errorMessage="请修改后重新下单！您购买的"+errorMessage+"是处方药，目前处方药仅支持北京望京地区以货到付款的方式购买。";
							}
						}
						if(checkFlag){
							
							String homePageConfigId = sys.getKeys("public_service_url");
							Map<String,String> publicMap = new HashMap<String,String>();
							publicMap.put("paymentmethodId", paymentmethodId);
							publicMap.put("receiverId", receiverId);
							publicMap.put("couponCodeId", couponCodeId);
							publicMap.put("deliveryId", deliveryId);
							publicMap.put("platform", platform);
							publicMap.put("memberId", String.valueOf(memberId));
							publicMap.put("isInvoice",isInvoice);
							publicMap.put("invoiceType", invoiceType);
							publicMap.put("invoiceTitle", invoiceTitle);
							publicMap.put("remark", remark);
							publicMap.put("source", source);
							publicMap.put("receiverNewFlag", receiverNewFlag);
							String resultJsonstr =ClientSubmitPublic.getPublicService(publicMap, homePageConfigId+"ordersave");
							Map<String,Object> resultMap =(Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(resultJsonstr),HashMap.class);
							if(resultMap.get("flag").equals("1")){
								orderId = String.valueOf(resultMap.get("orderId"));
								if(!"0".equals(orderId)){
									TOrderManagerImpl tordermanager = (TOrderManagerImpl) getSpringBean("tordermanager");
									TOrder order =tordermanager.selectByPrimaryKey(Long.valueOf(orderId.trim()));
									if(order!=null){
										statusCode = "1";// 成功
										if(order.getPayableAmount().compareTo(new BigDecimal(0)) <= 0){
											payFlag="2";
										}
										if(order.getOrderStatus().intValue()!=0){
											payFlag="2";
										}
									}else{
										orderId="";
										statusCode = "0";// 失败
										errorMessage="提交订单失败";
									}
								}else{
									orderId="";
									statusCode = "0";// 失败
									errorMessage="提交订单失败";
								}
							}else{
								statusCode = "0";// 失败
								errorMessage="提交订单失败";
							}
						}
					}
				}
			} catch (Exception e) {
				statusCode = "0";// 失败
				errorMessage="提交订单失败";
				e.printStackTrace();
			}
		} else {
			statusCode = "2";// sessionId 不能为空
			errorMessage="无效用户";
		}
		resultmap.put("statusCode", statusCode);
		resultmap.put("errorMessage", errorMessage);
		resultmap.put("payFlag", payFlag);
		resultmap.put("orderId", orderId);
		JSONObject json4 = JSONObject.fromObject(resultmap);
		ConstantUtil.reJSON(json4.toString(), request, response);
	
	}
	
}
