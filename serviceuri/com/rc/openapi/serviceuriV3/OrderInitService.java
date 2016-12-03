package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.rc.openapi.model.order.CartGoodVO;
import com.rc.openapi.model.order.OrderInitPublicVO;
import com.rc.openapi.model.order.PaymethodVO;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.NumberUtil;
/**
 * 结算页面初始化
 * @author user3
 *
 */
public class OrderInitService extends BaseURIService{
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
		String receiverNewFlag =  request.getParameter("receiverNewFlag");//使用新的收货地址标记   Flag  1 表示是  
		
		String platform = "3";//平台参数
		Map<String, Object> resultmap = new HashMap<String, Object>();
		String statusCode = "";
		String errorMessage ="";
		OrderInitPublicVO orderinitpublicvo = new OrderInitPublicVO();
		TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
		if (sessionId != null && !"".equals(sessionId)) {
			try {
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if (userId == -1l) {
					statusCode = "0";// 非法用户
					errorMessage="无效用户";
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
					if(statusCode.equals("")){
						if(!StringUtils.hasText(paymentMethodId)){
							paymentMethodId="1";
						}
						Map<String,String> publicMap = new HashMap<String,String>();
						publicMap.put("paymentMethodId", paymentMethodId);
						publicMap.put("receiverId", receiverId);
						publicMap.put("couponCodeId", couponCodeId);
						publicMap.put("deliveryId", deliveryId);
						publicMap.put("platform", platform);
						publicMap.put("memberId", String.valueOf(userId));
						publicMap.put("receiverNewFlag", receiverNewFlag);
						
						String homePageConfigId = sys.getKeys("public_service_url");
						String resultJsonstr =ClientSubmitPublic.getPublicService(publicMap, homePageConfigId+"orderinit");
						if(JSONObject.fromObject(resultJsonstr).get("flag").equals("1")){
							Map<String, Class> classMap = new HashMap<String, Class>();  
							classMap.put("paymethodList", PaymethodVO.class);  
							classMap.put("goodsList", CartGoodVO.class);    
							classMap.put("memberCouponUserList", Map.class);
							orderinitpublicvo =  (OrderInitPublicVO) JSONObject.toBean(JSONObject.fromObject(JSONObject.fromObject(resultJsonstr).get("orderInitVO")),OrderInitPublicVO.class,classMap);
							statusCode = "1";// 成功
						}else{
							statusCode = "0";// 失败
							errorMessage="获取数据失败";
						}
					}
				}
			} catch (Exception e) {
				statusCode = "0";// 失败
				errorMessage="获取数据失败";
				e.printStackTrace();
			}
		} else {
			statusCode = "0";//
			errorMessage="无效用户";
		}
		resultmap.put("statusCode", statusCode);
		if("5".equals(statusCode)){
			errorMessage ="商品:"+errorMessage.substring(0, errorMessage.length()-1)+"库存不足,下次记得再眼疾手快一点哦！！";
		}
		resultmap.put("errorMessage", errorMessage);
		resultmap.put("couponCardId", orderinitpublicvo.getCouponCardId());//可用优惠券id
		resultmap.put("couponName", orderinitpublicvo.getCouponName());//优惠券名称
		resultmap.put("couponDiscount", NumberUtil.format2String(orderinitpublicvo.getCouponDiscount()));//优惠券折扣
		resultmap.put("couponSize", orderinitpublicvo.getMemberCouponUserList()==null?"":orderinitpublicvo.getMemberCouponUserList().size()+"");//可用优惠券数量
		
		resultmap.put("receiver", orderinitpublicvo.getReceiver());//收货地址
		if(orderinitpublicvo.getReceiver()!=null){
			String hdfk_area="";
			try {
				hdfk_area = sys.getKeys("paymethod_hdfk_area");
			} catch (SQLException e) {
				e.printStackTrace();
			}// 得到支持 货到付款的区域
			if(orderinitpublicvo.getReceiver().getAreaName().indexOf(hdfk_area)==-1){//不是北京
				paymentMethodId="1";
			}
		}
		Map<String,String> paymethodMap = null;
		List<Map<String,String>> paymethodList =new  ArrayList<Map<String,String>>();
		if(orderinitpublicvo.getPaymethodList()!=null&&orderinitpublicvo.getPaymethodList().size()>0){
			for(PaymethodVO paymethodvo:orderinitpublicvo.getPaymethodList()){
				paymethodMap = new HashMap<String,String>();
				paymethodMap.put("name", paymethodvo.getName());
				paymethodMap.put("paymethodId", paymethodvo.getPaymethodId());
				if(paymentMethodId.equals(paymethodvo.getPaymethodId())){
					paymethodMap.put("isDefault", "1");
				}else{
					paymethodMap.put("isDefault", "0");
				}
				paymethodList.add(paymethodMap);
			}
		}
		resultmap.put("paymethodList", paymethodList);//支付方式list
		resultmap.put("deliveryInstro", "药师到家：执业药师上门，用药指导、健康咨询、身体诊查一网打尽！体验价仅需9.9元！");//配送方式描述
		resultmap.put("deliveryName", orderinitpublicvo.getDeliveryName());//配送方式名称
		resultmap.put("deliveryId", orderinitpublicvo.getDeliveryId());//配送方式id
		resultmap.put("freight", NumberUtil.format2String(orderinitpublicvo.getFreight()));//运费
		resultmap.put("amountPayable", NumberUtil.format2String(orderinitpublicvo.getAmountPayable()));//应付金额
		resultmap.put("goodsprice", NumberUtil.format2String(orderinitpublicvo.getPrice()));//商品总金额
		resultmap.put("promotionDiscount", NumberUtil.format2String(orderinitpublicvo.getPromotionDiscount()));//促销折扣金额
		JSONObject json4 = JSONObject.fromObject(resultmap);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	
	
}
