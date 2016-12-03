package com.rc.openapi.serviceuriV5.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.util.StringUtils;

import com.rc.openapi.dubbo.model.TOrderGoodModel;
import com.rc.openapi.dubbo.vo.TOrder;
import com.rc.openapi.serviceApi.OrderServiceDubboApi;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 订单详情
 * OrderDetailService<BR>
 * 创建人:Marlon <BR>
 * 时间：2016-8-25-上午10:42:59 <BR>
 * @version 1.0.0
 */
public class OrderDetailService extends BaseURIService {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");//sessionId
		String orderId = request.getParameter("orderId");//订单id
		Map<String, Object> resultMap = new HashMap<String, Object>();//将所有结果集存放到改map中用于统一返回json字符串
		String statusCode ="";
		String message="服务器繁忙请稍后重试！";
		if (StringUtils.hasText(sessionId)) {
			OrderServiceDubboApi orderservicedubboapi = (OrderServiceDubboApi) getSpringBean("orderservicedubboapi");
			Map<String, Object> orderMap = new HashMap<String, Object>();
			if (StringUtils.hasText(orderId)) {
				try {
					TOrder tOrder = orderservicedubboapi.getOrderById(Long.valueOf(orderId.trim()));//根据会员id和订单状态查询订单详情
					if(tOrder!=null){
						Map<String, String> detailMap = new HashMap<String, String>();
						SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
						detailMap.put("receiver",tOrder.getReceiver());// 收货人
						detailMap.put("phone",tOrder.getPhone());// 收货人电话
						detailMap.put("detailedAddress",tOrder.getDetailedAddress());// 收货人地址
						detailMap.put("orderSn",tOrder.getOrderSn());// 订单编号
						/******向前推一天处理*******/
						Calendar calendar = new GregorianCalendar();   
			            calendar.setTime(tOrder.getCreateDate());   
			            calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动   
			            String putDate = sdf.format(calendar.getTime()); //增加一天后的日期  
						detailMap.put("createDate",String.valueOf(sdf.format(tOrder.getCreateDate())));// 下单时间
						detailMap.put("cancelDate",putDate);// 自动取消时间
						detailMap.put("paymentId",String.valueOf(tOrder.getPaymentId()));// 支付方式
						detailMap.put("orderStatus",String.valueOf(tOrder.getOrderStatus()));// 订单状态
						detailMap.put("invoiceTitle",String.valueOf(tOrder.getInvoiceTitle()));// 发表抬头
						detailMap.put("invoiceType",String.valueOf(tOrder.getInvoiceType()));// 发表内容 1（1 明细 2 药品 3 保健品 4 医疗器械）
						detailMap.put("shippingFee",String.valueOf(tOrder.getShippingFee()));// 运费
						detailMap.put("payableAmount",String.valueOf(tOrder.getPayableAmount()));// 实付款额
						BigDecimal totalAmount = new BigDecimal(0);
						totalAmount = totalAmount.add(new BigDecimal(String.valueOf(tOrder.getPromotionalDiscount())));  
						totalAmount = totalAmount.add(new BigDecimal(String.valueOf(tOrder.getCouponDiscount())));  
						totalAmount = totalAmount.add(new BigDecimal(String.valueOf(tOrder.getIntegrationDiscount())));  
						totalAmount = totalAmount.add(new BigDecimal(String.valueOf(tOrder.getAdjustAmount())));  
						detailMap.put("couponAmount",String.valueOf(totalAmount));// 优惠金额
						
						List<Map<String, String>> items = new ArrayList<Map<String, String>>();//用于存放商品项
						Map<String, String> itemsMap = null;//存放商品项单条商品信息
						List<TOrderGoodModel> orderGoodList= orderservicedubboapi.getTOrderGoodListByOrderId(Long.valueOf(orderId.trim()),null);//
						/*评价状态的判断*/
						if (tOrder.getOrderStatus()==3) {
							int count = orderservicedubboapi.getReviewsByOrderId(Long.valueOf(orderId.trim()));
							detailMap.put("ifReviews", String.valueOf(count));
						}else {
							detailMap.put("ifReviews", "0");
						}
						if(orderGoodList!=null&&orderGoodList.size()>0){
							BigDecimal total = new BigDecimal(0);
							for (TOrderGoodModel goodModel:orderGoodList) {
								itemsMap= new HashMap<String, String>();
								itemsMap.put("goodId",String.valueOf(goodModel.getGoodsid()));//商品id
								itemsMap.put("goodName",goodModel.getGoodsName());//商品名称
								itemsMap.put("spec",goodModel.getSpec());//商品规格
								itemsMap.put("quantity",String.valueOf(goodModel.getQuantity()));//商品数量
								itemsMap.put("price",String.valueOf(goodModel.getPrice()));//商品单价
								itemsMap.put("sumPrice",String.valueOf(goodModel.getSumPrice()));//（某商品）商品总价
								itemsMap.put("img",goodModel.getAbbreviationPicture());//商品图片
								total = total.add(new BigDecimal(String.valueOf(goodModel.getSumPrice())));//该订单下所有商品项的商品总价
								items.add(itemsMap);
							}
							resultMap.put("count", orderGoodList.size());
							detailMap.put("orderId",String.valueOf(orderId));// 该订单id
							detailMap.put("total",String.valueOf(total));// 所有商品总价
						}else{
							resultMap.put("count", 0);
						}
						orderMap.put("detail", detailMap);
						orderMap.put("items", items);
						resultMap.put("order", orderMap);
						statusCode="1";
						message="请求成功";
					}else{
						statusCode="0";
						message="该订单信息不存在！";
					}
				} catch (Exception e) {
					statusCode="0";
					message="服务器繁忙请稍后重试！";
					e.printStackTrace();
				}
			}else {
				statusCode="0";
				message="该订单信息不存在！";
			}
		}else {
			statusCode="0";
			message="未登录，不能执行该操作！";
		}
		resultMap.put("statusCode", statusCode);
		resultMap.put("message", message);
		ConstantUtil.reJSON(JSONObject.fromObject(resultMap).toString(), request, response);
	}
	
}
