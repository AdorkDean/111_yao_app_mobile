package com.rc.openapi.serviceuriV4.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.model.UnifiedOrderResDataAPPAPI;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.service.impl.TOrderFlowManagerImpl;
import com.rc.openapi.service.impl.TOrderManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConnectionUtil;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
import com.rc.openapi.util.NetworkUtil;
import com.rc.openapi.util.NumberUtil;
import com.rc.openapi.util.RandomStringGenerator;
import com.rc.openapi.util.Signature;
import com.rc.openapi.vo.TOrder;
import com.rc.openapi.vo.TOrderFlow;
import com.rc.openapi.vo.TOrderFlowExample;
/**
 * 订单支付
 * @author user3
 *
 */
public class OrderPayService extends BaseURIService{
	private static final long serialVersionUID = 4590915627920439798L;
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
		Map<String, Object> resultmap = new HashMap<String, Object>();
		String statusCode = "";
		String payType ="";
		String errorMessage ="";
		String htmlPayFlag="0";//是否跳转 html5支付  0 否  1 是
        if(StringUtils.hasText(sessionId)&&StringUtils.hasText(orderId)&&StringUtils.hasText(paymentPluginId)){
        	try{
        		TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if (userId == -1l) {
					statusCode = "0";// 非法用户
					errorMessage="无效用户";
				} else {
					TOrderManagerImpl tordermanager = (TOrderManagerImpl) getSpringBean("tordermanager");
					TOrder order = tordermanager.selectByPrimaryKey(Long.valueOf(orderId));
					if(order!=null&&order.getMemberId()!=null){
						if (order == null) {
							statusCode = "0";
							errorMessage="订单存在";
						}else if (order.getOrderStatus()!=0) {//待支付
							statusCode = "0";
							errorMessage="订单无法支付";
						}else if (order.getPayableAmount().compareTo(new BigDecimal(0)) <= 0) {
							statusCode = "0";
							errorMessage="订单金额小于等于0,无法支付";
						}else if(userId.longValue()!=order.getMemberId().longValue()){
							statusCode = "0";
							errorMessage="订单无法完成支付";
						}else{
							if("wzfMobilePlugin".equals(paymentPluginId)){//微信
								payType="2";//
							}else if("alipayMobilePlugin".equals(paymentPluginId)){//支付
								payType="1";//
							}else if("payWapYktPlugin".equals(paymentPluginId)){
								payType="3";//
								htmlPayFlag="1";//跳转html支付
							}
							TOrderFlowManagerImpl torderflowmanager = (TOrderFlowManagerImpl) getSpringBean("torderflowmanager");
							TOrderFlowExample flowExample = new TOrderFlowExample();
							flowExample.createCriteria().andMemberIdEqualTo(order.getMemberId()).andOrderIdEqualTo(order.getId());
							List<TOrderFlow> orderFlowList = torderflowmanager.selectByExample(flowExample);
							
							TOrderFlow orderFlow= null;
							String paymentPluginName="";
							if("wzfMobilePlugin".equals(paymentPluginId)){//微信
								paymentPluginName="APP微信支付";
							}else if("alipayMobilePlugin".equals(paymentPluginId)){//支付宝	
								paymentPluginName="APP支付宝支付";
							}else if("payWapYktPlugin".equals(paymentPluginId)){//APP医卡通支付
								paymentPluginName="APP医卡通支付";
							}
							if(orderFlowList!=null&&orderFlowList.size()>0){
								orderFlow = orderFlowList.get(0);
								orderFlow.setPaymentPlugin(paymentPluginId);
								orderFlow.setPayMethod(paymentPluginName);
								orderFlow.setHavePay(order.getPayableAmount());
								orderFlow.setOrderId(order.getId());
								torderflowmanager.updateByPrimaryKeySelective(orderFlow);
							}else{
								orderFlow =new TOrderFlow();
								orderFlow.setMemberId(order.getMemberId());
								orderFlow.setPaymentStatus(0);//支付状态 0 未支付 1已支付
								orderFlow.setPaymentPlugin(paymentPluginId);
								orderFlow.setPayMethod(paymentPluginName);
								orderFlow.setHavePay(order.getPayableAmount());
								orderFlow.setCreateTime(new Date());
								orderFlow.setOrderId(order.getId());
								torderflowmanager.insertSelective(orderFlow);
							}
							OpenSqlManageImpl opensqlmanage = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
							Map<String,Object> paramMap = new HashMap<String,Object>(); 
							paramMap.put("orderId", order.getId());
							 List<Map<String,Object>> orderGoodNameList =opensqlmanage.selectForListByMap(paramMap, "t_order.selectGoodsNameByorderID");	
							 String goodTitle="";
							 if (orderGoodNameList != null && orderGoodNameList.size() > 0) {
									for (Map<String,Object> goodNameMap : orderGoodNameList) {
										goodTitle = goodTitle+(String)goodNameMap.get("goods_name")+",";//商品标题
									}
						    }
							if("wzfMobilePlugin".equals(paymentPluginId)){//微信
								Map<String,String> wzfMap = new HashMap<String,String>();
								String body =goodTitle;
								if(body.length() > 10){
									body = body.substring(0,10);
								}
							    String total_fee = order.getPayableAmount().setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(0).toString();
							    //微信分配的公众号ID（开通公众号之后可以获取到）
							    wzfMap.put("appid",InfoUtil.getInstance().getInfo("config", "wxpay.config.appID"));    //公众账号ID  (必填)
							    wzfMap.put("mch_id",InfoUtil.getInstance().getInfo("config", "wxpay.config.mchID"));    //商户号 (必填)
							    wzfMap.put("device_info","");    //设备号.  终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
							    wzfMap.put("nonce_str",RandomStringGenerator.getRandomStringByLength(32));    //随机字符串 (必填)
							    wzfMap.put("body",body);    //商品描述  商品或支付单简要描述  (必填)
							    wzfMap.put("detail",goodTitle);    //商品详情 商品名称明细列表
							    wzfMap.put("out_trade_no",order.getOrderSn());    //商户订单号  商户系统内部的订单号 (必填)
							    wzfMap.put("fee_type","CNY");    //货币类型   CNY
							    wzfMap.put("total_fee",total_fee);    //总金额  (必填)
//								wzfMap.put("total_fee","1");//上线时 要修改
							    wzfMap.put("spbill_create_ip",NetworkUtil.getIpAddress(request));    //终端IP  APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。  (必填)
							    wzfMap.put("time_start","");    //交易起始时间  订单生成时间，格式为yyyyMMddHHmms
							    wzfMap.put("time_expire","");    //交易结束时间   订单失效时间，格式为yyyyMMddHHmmss
							    wzfMap.put("goods_tag","");    //商品标记   商品标记，代金券或立减优惠功能的参数，说明详见 WXG
							    wzfMap.put("notify_url",InfoUtil.getInstance().getInfo("config", "wzf.wxPay_notify_url") );    //通知地址  接收微信支付异步通知回调地址   (必填)
							    wzfMap.put("trade_type","APP");    //交易类型  取值如下：JSAPI，NATIVE，APP，WAP  (必填)
							    wzfMap.put("product_id",String.valueOf(order.getId()) );    //商品ID  rade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
							    wzfMap.put("openid","");    //用户标识  trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。下单前需要调用【网页授权获取用户信息】接口获取到用户的Openid。
							    wzfMap.put("source", "sylsapp111yao");
								String sign = Signature.getWXSign(wzfMap, InfoUtil.getInstance().getInfo("config", "wxPaymentPk"));//Signature.getSign(wzfMap);
								wzfMap.put("sign", sign); // 签名 (必填)
								wzfMap.put("body", URLEncoder.encode(body,"UTF-8")); // 签名 (必填)
								wzfMap.put("detail", URLEncoder.encode(goodTitle,"UTF-8")); // 签名 (必填)
								wzfMap.put("notify_url", URLEncoder.encode(InfoUtil.getInstance().getInfo("config", "wzf.wxPay_notify_url"),"UTF-8")); // 签名 (必填)
								StringBuffer postDataS = new StringBuffer("");
								for(Map.Entry<String,String> entry:wzfMap.entrySet()){
						            if(entry.getValue()!=""){
						            	postDataS.append(entry.getKey() + "=" + entry.getValue() + "&");
						            }
						        }
								String asurl =InfoUtil.getInstance().getInfo("config", "payment.goUnifiedOrderService.url");
								String postResult =ConnectionUtil.getContentFromUrl(asurl, postDataS.toString());
								if(!postResult.startsWith("fail")){
									UnifiedOrderResDataAPPAPI dataAPPAPI = (UnifiedOrderResDataAPPAPI) JSONObject.toBean(JSONObject.fromObject(postResult),
													UnifiedOrderResDataAPPAPI.class);
									resultmap.put("appid", dataAPPAPI.getAppid());//公众账号ID
									resultmap.put("partnerid", dataAPPAPI.getPartnerid());//商户号
									resultmap.put("prepayid", dataAPPAPI.getPrepayid());//预支付交易会话ID
									resultmap.put("packages", dataAPPAPI.getPackage_str());//扩展字段  暂填写固定值Sign=WXPay
									resultmap.put("noncestr", dataAPPAPI.getNoncestr());//随机字符串
									resultmap.put("timestamp", dataAPPAPI.getTimestamp());//时间戳
									resultmap.put("sign", dataAPPAPI.getSign());//签名
									statusCode = "1";//
								}else{
									statusCode = "0";//
									errorMessage="订单支付失败";
									throw new Exception("=====>提交订单到微信失败");
								}
							}else if("alipayMobilePlugin".equals(paymentPluginId)){//支付
								resultmap.put("outTradeNo", order.getOrderSn());//订单号
								resultmap.put("totalFee", NumberUtil.format2String(order.getPayableAmount()));//订单支付金额
								resultmap.put("subject",goodTitle.substring(0, goodTitle.length()-1).length()>120?goodTitle.substring(0, 120):goodTitle.substring(0, goodTitle.length()-1));
								resultmap.put("body", goodTitle.substring(0, goodTitle.length()-1).length()>500?goodTitle.substring(0, 500):goodTitle.substring(0, goodTitle.length()-1));
								resultmap.put("notifyUrl", InfoUtil.getInstance().getInfo("config", "alipay.notify_url"));
						    	resultmap.put("paymentType", "1");//
						    	resultmap.put("itBPay", "30m");
								statusCode = "1";//
							}else if("payWapYktPlugin".equals(paymentPluginId)){
								statusCode="1";//跳转html支付
							}
						}
					}else{
						statusCode = "0";// 订单不存在
						errorMessage="订单不存在";
					}
				}
        	}catch(Exception e){
        		statusCode = "0";// 失败
        		e.printStackTrace();
        	}
        }else{
        	statusCode="0";//参数
        	errorMessage="订单支付参数不完整";
        }
        resultmap.put("payType", payType);
        resultmap.put("statusCode", statusCode);
        resultmap.put("orderId", orderId);
        resultmap.put("errorMessage",errorMessage);
        resultmap.put("htmlPayFlag", htmlPayFlag);
		JSONObject json4 = JSONObject.fromObject(resultmap);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	
	
}
