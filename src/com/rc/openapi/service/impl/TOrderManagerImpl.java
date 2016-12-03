package com.rc.openapi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.util.StringUtils;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.dao.CDeliveryWayDAO;
import com.rc.openapi.dao.CPaymentWayDAO;
import com.rc.openapi.dao.OpenSqlDAO;
import com.rc.openapi.dao.TCartDAO;
import com.rc.openapi.dao.TCartItemDAO;
import com.rc.openapi.dao.TCouponCardDAO;
import com.rc.openapi.dao.TCouponDAO;
import com.rc.openapi.dao.TGoodsBrokerageDAO;
import com.rc.openapi.dao.TGoodsDAO;
import com.rc.openapi.dao.TGoodsPriceDAO;
import com.rc.openapi.dao.TMemberDAO;
import com.rc.openapi.dao.TMemberIntegralDAO;
import com.rc.openapi.dao.TMemberLeaderDAO;
import com.rc.openapi.dao.TMemberReceiverDAO;
import com.rc.openapi.dao.TOrderDAO;
import com.rc.openapi.dao.TOrderFlowDAO;
import com.rc.openapi.dao.TOrderItemDAO;
import com.rc.openapi.dao.TOrderLeaderDAO;
import com.rc.openapi.dao.TPromotionDAO;
import com.rc.openapi.dao.TSysParameterDAO;
import com.rc.openapi.dao.impl.CartDaoImpl;
import com.rc.openapi.model.MobileAlipayRequestVO;
import com.rc.openapi.model.OrderAddVO;
import com.rc.openapi.model.OrderGoodCart;
import com.rc.openapi.model.OrderPaymethodVO;
import com.rc.openapi.model.ReceiverVo;
import com.rc.openapi.model.UnifiedOrderResDataAPPAPI;
import com.rc.openapi.model.WzfPayVO;
import com.rc.openapi.model.cart.CartGift;
import com.rc.openapi.model.cart.CartParam;
import com.rc.openapi.service.TOrderManager;
import com.rc.openapi.util.ConnectionUtil;
import com.rc.openapi.util.InfoUtil;
import com.rc.openapi.util.NetworkUtil;
import com.rc.openapi.util.NumberUtil;
import com.rc.openapi.util.RandomStringGenerator;
import com.rc.openapi.util.Signature;
import com.rc.openapi.vo.CDeliveryWay;
import com.rc.openapi.vo.CPaymentWay;
import com.rc.openapi.vo.TCart;
import com.rc.openapi.vo.TCartExample;
import com.rc.openapi.vo.TCartItemExample;
import com.rc.openapi.vo.TCategory;
import com.rc.openapi.vo.TCoupon;
import com.rc.openapi.vo.TCouponCard;
import com.rc.openapi.vo.TCouponRelevance;
import com.rc.openapi.vo.TGoods;
import com.rc.openapi.vo.TGoodsBrokerage;
import com.rc.openapi.vo.TGoodsBrokerageExample;
import com.rc.openapi.vo.TGoodsPrice;
import com.rc.openapi.vo.TGoodsPriceExample;
import com.rc.openapi.vo.TMemberIntegral;
import com.rc.openapi.vo.TMemberReceiver;
import com.rc.openapi.vo.TMemberReceiverExample;
import com.rc.openapi.vo.TOrder;
import com.rc.openapi.vo.TOrderExample;
import com.rc.openapi.vo.TOrderFlow;
import com.rc.openapi.vo.TOrderFlowExample;
import com.rc.openapi.vo.TOrderItem;
import com.rc.openapi.vo.TOrderItemExample;
import com.rc.openapi.vo.TPromotion;
import com.rc.portal.jms.MessageSender;

public class TOrderManagerImpl  implements TOrderManager {

    private TOrderDAO torderdao;
    private OpenSqlDAO opensqldao;
    private CPaymentWayDAO cpaymentwaydao;
    private TOrderFlowDAO torderflowdao;
    private TOrderItemDAO torderitemdao;
    private TPromotionDAO tpromotiondao;
    private TMemberReceiverDAO tmemberreceiverdao;
    private TGoodsDAO tgoodsdao;
    private CartDaoImpl cartdao;
    private  TCartDAO tcartdao;
	private TCartItemDAO tcartitemdao;
    private TSysParameterDAO tsysparameterdao;
    private TCouponCardDAO tcouponcarddao;
    private TCouponDAO tcoupondao;
    private TGoodsBrokerageDAO tgoodsbrokeragedao;
    
    private TMemberDAO tmemberdao;

    private TMemberIntegralDAO tmemberintegraldao;
    
    private TOrderLeaderDAO torderleaderdao;
    
    private TMemberLeaderDAO tmemberleaderdao;

    
    private MessageSender topicMessageSender;

    
    private CDeliveryWayDAO cdeliverywaydao;
    
    private TGoodsPriceDAO tgoodspricedao;

    public TOrderManagerImpl() {
        super();
    }
    public void  setTorderdao(TOrderDAO torderdao){
        this.torderdao=torderdao;
    }
    public TOrderDAO getTorderdao(){
        return this.torderdao;
    }
    public     int countByExample(TOrderExample example) throws SQLException{
        return torderdao. countByExample( example);
    }

    public     int deleteByExample(TOrderExample example) throws SQLException{
        return torderdao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return torderdao. deleteByPrimaryKey( id);
    }

    public     Long insert(TOrder record) throws SQLException{
        return torderdao. insert( record);
    }

    public     Long insertSelective(TOrder record) throws SQLException{
        return torderdao. insertSelective( record);
    }

    public     List selectByExample(TOrderExample example) throws SQLException{
        return torderdao. selectByExample( example);
    }

    public     TOrder selectByPrimaryKey(Long id) throws SQLException{
        return torderdao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TOrder record, TOrderExample example) throws SQLException{
        return torderdao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TOrder record, TOrderExample example) throws SQLException{
        return torderdao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TOrder record) throws SQLException{
        return torderdao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TOrder record) throws SQLException{
        return torderdao. updateByPrimaryKey( record);
    }
    
    /**
     * 检测货到付款  支持地区
     * @throws SQLException 
     */
    public boolean checkHdfkArea(String areaName) throws SQLException{
    	String hdfk_area =tsysparameterdao.getKeys("paymethod_hdfk_area");// 得到支持 货到付款的区域
    	if(areaName.indexOf(hdfk_area)!=-1){
    		return true;
    	}else{
    		return false;
    	}
    }
    
	@Override
	public Object updateOrderPayment(TOrder order, long memberId,
			long paymentmethodId, String paymentPluginId,
			HttpServletRequest request) throws Exception {
		 Object obj =null;
		   try{
			 TOrder updateOrder = new TOrder();  
			 updateOrder.setId(order.getId());
			 updateOrder.setPaymentId(paymentmethodId);
			 BigDecimal freight = new BigDecimal("0");
		    BigDecimal weight = new BigDecimal("0");
		    BigDecimal price = new BigDecimal("0"); //商品金额	
		    boolean feeflag = false;
			if(order.getCouponId()!=null){//判断是否使用免运费优惠券
				TCouponCard couponCard = tcouponcarddao.selectByPrimaryKey(order.getCouponId());
				if(couponCard!=null&&couponCard.getTicketId()!=null){
					TCoupon tcoupon =tcoupondao.selectByPrimaryKey(couponCard.getTicketId());
					if(tcoupon!=null&&tcoupon.getScope()!=null&&tcoupon.getScope().intValue()==1){//免运费
						feeflag = true;
					}
				}
			}
			Map<String,Object> paramMap = new HashMap<String,Object>(); 
		    paramMap.put("orderId", order.getId());
		    List orderPirceWeightlist =opensqldao.selectForListByMap(paramMap, "t_order.selectGoodsPriceWeightByorderID");
		    if(orderPirceWeightlist!=null&&orderPirceWeightlist.size()>0){
		    	Map<String,Object> resultMap = (Map<String, Object>) orderPirceWeightlist.get(0);
		    	if(resultMap.get("pricesum")!=null){
		    		price =(BigDecimal) resultMap.get("pricesum");
		    	}
		    	if(resultMap.get("weightsum")!=null){
		    		weight=(BigDecimal) resultMap.get("weightsum");
		    	}
		    }
		    if(!feeflag){
		    	String free_shippingfee_price =tsysparameterdao.getKeys("free_shippingfee_price");
		    	if(StringUtils.hasText(free_shippingfee_price)){
		    		if(price.compareTo(new BigDecimal(free_shippingfee_price.trim()))>=0){
		    			feeflag=true;
		    		}
		    	}
		    }
		    
		    CPaymentWay paymentWay=	cpaymentwaydao.selectByPrimaryKey(paymentmethodId);
		    String ysdjFlag ="0";// 0否   1 是
	       	 CDeliveryWay deliveryWay = new CDeliveryWay();
	       	 if(order.getDeliveryId()!=null){//配送方式id
	       		 deliveryWay= this.cdeliverywaydao.selectByPrimaryKey(Long.valueOf(order.getDeliveryId()));
	       		 if(deliveryWay.getDeliveryCode().equals("ysdj")){//药师到家
	       			 ysdjFlag ="1";
	       			freight=deliveryWay.calculateFreight(weight);
	       		 }
	       	 }
	       	 if(ysdjFlag.equals("0")){//不是 药师到家配送方式
	       		//计算全场免运费金额
	       		 if(!feeflag){
	 		    	String free_shippingfee_price =tsysparameterdao.getKeys("free_shippingfee_price");
	 		    	if(StringUtils.hasText(free_shippingfee_price)){
	 		    		if(price.compareTo(new BigDecimal(free_shippingfee_price.trim()))>=0){
	 		    			feeflag=true;
	 		    		}
	 		    	}
	 		      }
	        	String deliveryCode="ptkd";//普通快递
	    		 if(checkYsdYsdjAreaId(String.valueOf(order.getAreaId()))){
	    			 deliveryCode="ysd";//药士达
	    		 }else if(checkHdfkArea(order.getAreaName())){//判断是否是北京   
	 				if(paymentWay.getPaymentCode().equals("hdfk")){//货到付款
	 					deliveryCode="hdfk";
	 				}
	    		 }
	   	        paramMap.clear();
				paramMap.put("deliveryCode", deliveryCode);
			    List<CDeliveryWay> deliveryList =this.opensqldao.selectForListByMap(paramMap, "c_delivery_way.selectDeliveryWayByMap");
			    if(deliveryList!=null&&deliveryList.size()>0){
			    	deliveryWay = deliveryList.get(0);
			    	freight=deliveryWay.calculateFreight(weight);
			    }
	        	if(feeflag){//是否免运费
	        		freight = new BigDecimal("0");
	        	 }
	       	 }
	       	updateOrder.setDeliveryId(deliveryWay.getId());
		    
		    
		    if(paymentWay.getPaymentCode().equals("hdfk")){//货到付款
				updateOrder.setOrderStatus(1);//待发货
			}
			 updateOrder.setPayableAmount(order.getPayableAmount().subtract(order.getShippingFee()).add(freight));
			 updateOrder.setOrderAmount(order.getPayableAmount().subtract(order.getShippingFee()).add(freight));
			 updateOrder.setShippingFee(freight);
			 
      		 if(String.valueOf(order.getAreaId()).equals("1158")){//大兴区域
      			updateOrder.setShippingFee(order.getShippingFee());//运费
      			updateOrder.setOrderAmount(order.getOrderAmount());// 订单金额
      			updateOrder.setPayableAmount(order.getPayableAmount());// 应付金额
      			updateOrder.setDeliveryId(2l);//药士达
      		 }
			 
			 this.torderdao.updateByPrimaryKeySelective(updateOrder);
			 if(!paymentWay.getPaymentCode().equals("hdfk")){//货到付款 线下支付
				    TOrderFlowExample flowExample = new TOrderFlowExample();
					flowExample.createCriteria().andMemberIdEqualTo(order.getMemberId()).andOrderIdEqualTo(order.getId());
					List<TOrderFlow> orderFlowList = this.torderflowdao.selectByExample(flowExample);
					TOrderFlow orderFlow= null;
					String paymentPluginName="";
					if("wzfMobilePlugin".equals(paymentPluginId)){//微信
						paymentPluginName="APP微信支付";
					}else if("alipayMobilePlugin".equals(paymentPluginId)){//支付宝	
						paymentPluginName="APP支付宝支付";
					}
					if(orderFlowList!=null&&orderFlowList.size()>0){
						orderFlow = orderFlowList.get(0);
						orderFlow.setPaymentPlugin(paymentPluginId);
						orderFlow.setPayMethod(paymentPluginName);
						orderFlow.setHavePay(updateOrder.getPayableAmount());
						orderFlow.setOrderId(order.getId());
						this.torderflowdao.updateByPrimaryKeySelective(orderFlow);
					}else{
						orderFlow =new TOrderFlow();
						orderFlow.setMemberId(order.getMemberId());
						orderFlow.setPaymentStatus(0);//支付状态 0 未支付 1已支付
						orderFlow.setPaymentPlugin(paymentPluginId);
						orderFlow.setPayMethod(paymentPluginName);
						orderFlow.setHavePay(updateOrder.getPayableAmount());
						orderFlow.setCreateTime(new Date());
						orderFlow.setOrderId(order.getId());
						this.torderflowdao.insertSelective(orderFlow);
					}
					 paramMap.put("orderId", order.getId());
					 List<Map<String,Object>> orderGoodNameList =opensqldao.selectForListByMap(paramMap, "t_order.selectGoodsNameByorderID");	
					 String goodTitle="";
					 if (orderGoodNameList != null && orderGoodNameList.size() > 0) {
							for (Map<String,Object> goodNameMap : orderGoodNameList) {
								goodTitle = goodTitle+(String)goodNameMap.get("goods_name")+",";//商品标题
							}
				    }
					 //获取支付提交信息
					obj =getPayVO(paymentPluginId,goodTitle,order,order.getId(),request);	
			 }
		   }catch(Exception e){
			   e.printStackTrace();
			   throw new Exception(e.getMessage());
		   }
		   return obj;
	}
	/**
	 * 检查地区id  是否支持药师到家  药士达
	 * @param areaid
	 * @return
	 * @throws SQLException 
	 */
	public boolean checkYsdYsdjAreaId(String areaid) throws SQLException{
		String ysd_ysdj_areaid =tsysparameterdao.getKeys("deliveryway_ysd_ysdj_areaid");//配送方式-药士达-药师到家-支持区域id
		boolean ysd_ysdj_areaid_flag = false;
 		for(int i=0;i<ysd_ysdj_areaid.split(",").length;i++){
 			if(ysd_ysdj_areaid.split(",")[i].equals(areaid)){
 				ysd_ysdj_areaid_flag= true;
 			}
 		}
		return  ysd_ysdj_areaid_flag;
		
	}
	
	
	
	/**
	 * 根据购物车商品计算优惠券
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getCouponByGoods(String memberId)
			throws SQLException {
		// 可以使用的优惠券
		List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", Long.parseLong(memberId));
			paramMap.put("ifPremiums", 0);
			List<OrderGoodCart> goodsList = this.opensqldao
					.selectForListByMap(paramMap,"t_goods.selectGoodsByCartMemberid");
			if (goodsList != null && goodsList.size() > 0) {
				Map<String, List<OrderGoodCart>> promotionGoodsMap = new HashMap<String, List<OrderGoodCart>>();
				List<OrderGoodCart> porList = null;
				// 把商品按照促销划分一下 按照促销放到 map里面
				for (OrderGoodCart orderGoodCart : goodsList) {
					if (orderGoodCart.getPromotionId()!= null) {
						if (promotionGoodsMap.get(String.valueOf(orderGoodCart.getPromotionId())) != null) {
							porList = promotionGoodsMap.get(String.valueOf(orderGoodCart.getPromotionId()));
						} else {
							porList = new ArrayList<OrderGoodCart>();
						}
						porList.add(orderGoodCart);
						promotionGoodsMap.put(String.valueOf(orderGoodCart.getPromotionId()), porList);
					}
				}
				// 调用建龙方法计算购物车商品 参与的促销活动
				for (String promotionId : promotionGoodsMap.keySet()) {
					// 查询促销
					TPromotion promotion = tpromotiondao.selectByPrimaryKey(Long.parseLong(promotionId));
					if (promotion.getIsTiket() != null
							&& promotion.getIsTiket().intValue() == 2) {// 不能使用优惠券
						// 这里需要根据 建龙的方法判断 该促销的商品是否 满足促销 如果满足促销 则移除 该促销的使所有商品
						porList = promotionGoodsMap.get(promotionId);
						if (porList != null) {
							goodsList.removeAll(porList);
						}
					}
				}

				List<Map<String, Object>> memberCouponList = this.opensqldao.selectForListByMap(paramMap,"t_coupon_card.selectCouponCardByMemberid");
				if (memberCouponList != null && memberCouponList.size() > 0 &&goodsList!=null&&goodsList.size()>0) {
					String goodsIds = "";// 商品ids
					String brandIds = "";// 品牌ids
					for (OrderGoodCart orderGoodCart :goodsList) {
						if (orderGoodCart.getId()!= null) {
							goodsIds = goodsIds+ String.valueOf(orderGoodCart.getId()) + ",";
						}
						if (orderGoodCart.getBrandId()!= null) {
							brandIds = brandIds+ String.valueOf(orderGoodCart.getBrandId())+ ",";
						}
					}
					if(StringUtils.hasText(goodsIds)){
						goodsIds = goodsIds.substring(0,goodsIds.length() - 1);
					}
					if(StringUtils.hasText(brandIds)){
						brandIds = brandIds.substring(0,brandIds.length() - 1);
					}
					BigDecimal goodsumPrice = null;
					for (Map<String, Object> couponMap : memberCouponList) {
						if (couponMap.get("scope") != null) {
							if (String.valueOf(couponMap.get("scope")).equals("1")) {// 免运费
								couponList.add(couponMap);
							} else {
								goodsumPrice = new BigDecimal("0");
								if (String.valueOf(couponMap.get("scope")).equals("0")) {// 全场
									for (OrderGoodCart orderGoodCart :goodsList) {
										if (orderGoodCart.getAppPrice()!= null&&orderGoodCart.getIfPremiums()==0) {
											goodsumPrice = goodsumPrice.add(orderGoodCart.getAppPrice().multiply(new BigDecimal(orderGoodCart.getQuantity())));
										}
									}
								} else if (String.valueOf(couponMap.get("scope")).equals("2")) {// 分类
									paramMap.clear();
									paramMap.put("couponId",couponMap.get("couponid"));
									List<TCouponRelevance> couponRelevanceList = this.opensqldao
											.selectForListByMap(paramMap,"t_coupon_relevance.selectCouponRelevanceByMap");
									if (couponRelevanceList != null
											&& couponRelevanceList.size() > 0) {
										for (TCouponRelevance couponRelevance : couponRelevanceList) {
											for (OrderGoodCart orderGoodCart :goodsList) {
												if (orderGoodCart.getCategoryid() != null&& couponRelevance.getRelevanceId().longValue() == orderGoodCart.getCategoryid().longValue()) {
													if (orderGoodCart.getAppPrice() != null&&orderGoodCart.getIfPremiums()==0) {
														goodsumPrice = goodsumPrice.add(orderGoodCart.getAppPrice().multiply(new BigDecimal(orderGoodCart.getQuantity())));
													}
												} else if (orderGoodCart.getCategoryid() != null) {
													paramMap.clear();
													paramMap.put("id",couponRelevance.getRelevanceId());
													List<TCategory> categoryList = this.opensqldao
															.selectForListByMap(paramMap,"t_category.selectCategoryByMap");
													if (categoryList != null&& categoryList.size() > 0) {
														if (categoryList.get(0).getAllParentId().indexOf(String.valueOf(orderGoodCart.getCategoryid())) > 0) {
															if (orderGoodCart.getAppPrice() != null&&orderGoodCart.getIfPremiums()==0) {
																goodsumPrice = goodsumPrice.add(orderGoodCart.getAppPrice().multiply(new BigDecimal(orderGoodCart.getQuantity())));
															}
														}
													}
												}
											}
										}
									}

								} else if (String.valueOf(couponMap.get("scope")).equals("3")) {// 品牌
									if (StringUtils.hasText(brandIds)) {
										paramMap.clear();
										paramMap.put("couponId",couponMap.get("couponid"));
										paramMap.put("relevanceId", brandIds);
										List<TCouponRelevance> couponRelevanceList = this.opensqldao
												.selectForListByMap(paramMap,"t_coupon_relevance.selectCouponRelevanceByMap");
										if (couponRelevanceList != null
												&& couponRelevanceList.size() > 0) {
											for (TCouponRelevance couponRelevance : couponRelevanceList) {
												for (OrderGoodCart orderGoodCart : goodsList) {
													if (orderGoodCart.getBrandId()!= null
															&& orderGoodCart.getBrandId().longValue() == couponRelevance.getRelevanceId().longValue()) {
														if (orderGoodCart.getAppPrice() != null&&orderGoodCart.getIfPremiums()==0) {
															goodsumPrice = goodsumPrice.add(orderGoodCart.getAppPrice().multiply(new BigDecimal(orderGoodCart.getQuantity())));
														}
													}
												}
											}
										}
									}
								} else if (String.valueOf(couponMap.get("scope")).equals("4")) {// 商品
									if (StringUtils.hasText(goodsIds)) {
										paramMap.clear();
										paramMap.put("couponId",couponMap.get("couponid"));
										paramMap.put("relevanceId", goodsIds);
										List<TCouponRelevance> couponRelevanceList = this.opensqldao
												.selectForListByMap(paramMap,"t_coupon_relevance.selectCouponRelevanceByMap");
										if (couponRelevanceList != null
												&& couponRelevanceList.size() > 0) {
											for (TCouponRelevance couponRelevance : couponRelevanceList) {
												for (OrderGoodCart orderGoodCart :goodsList) {
													if (orderGoodCart.getId()!= null
															&& orderGoodCart.getId().longValue() == couponRelevance.getRelevanceId().longValue()) {
														if (orderGoodCart.getAppPrice()  != null&&orderGoodCart.getIfPremiums()==0) {
															goodsumPrice = goodsumPrice.add(orderGoodCart.getAppPrice().multiply(new BigDecimal(orderGoodCart.getQuantity())));
														}
													}
												}
											}
										}
									}
								}
								if (checkCoupon(couponMap, goodsumPrice)) {
									couponList.add(couponMap);
								}
							}
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return couponList;
	}
	/**
	 * 判断优惠券是否能用
	 * 
	 * @param couponMap
	 * @param goodsumPrice
	 * @return
	 */
	public boolean checkCoupon(Map<String, Object> couponMap,
			BigDecimal goodsumPrice)  {
		boolean flag = false;
		if (couponMap.get("start_price") != null
				&& couponMap.get("end_price") != null) {
			if (goodsumPrice.compareTo((BigDecimal) couponMap.get("start_price")) >= 0&& goodsumPrice.compareTo((BigDecimal) couponMap.get("end_price")) <= 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	
	/**
     * 计算订单相关金额
     * @param goodsList  商品list
     * @param couponCardId 优惠券id
     * @param receiverId   收货地址id
     * @param memberId   用户id
     * @param paymentWayId  支付方式
     * @param deliveryId 配送方式
     * @return
     * @throws SQLException
     */
	public Map<String, Object> getOrderPrice(List<OrderGoodCart> goodsList,String couponCardId, String receiverId,String memberId,String paymentWayId,String deliveryId) throws Exception {
		 Map<String,Object> resultMap = new HashMap<String,Object>();
         try{
        	 BigDecimal goodsPrice = new BigDecimal("0");//商品金额
        	 BigDecimal shippingFee = new BigDecimal("0");//运费
        	 BigDecimal couponDiscount = new BigDecimal("0");//优惠券优惠金额
        	 BigDecimal promotionalDiscount = new BigDecimal("0");//促销折扣金额
        	 Map<String, Object> paramMap = new HashMap<String, Object>();
			CartParam cartParam = new CartParam();
			cartParam.setUserId(Long.parseLong(memberId));
			Map cartMap = cartdao.accounts(cartParam);
			promotionalDiscount = (BigDecimal) cartMap.get("youhui");
			
			 //是否免运费
       	    boolean feeflag=false;
			
			//  李建龙修改
			List<CartGift> giftList = (List<CartGift>) cartMap.get("gift");
			
			if(giftList!=null&&giftList.size()>0){
				TGoods goods = null;
				OrderGoodCart goodCart = null;
				for(CartGift cartGift:giftList){
					goods = this.tgoodsdao.selectByPrimaryKey(cartGift.getGoodsid());
					goodCart = new OrderGoodCart();
					goodCart.setId(goods.getId());
					goodCart.setAppPrice(new BigDecimal("0"));
					goodCart.setGoodsName(goods.getGoodsName());
					goodCart.setStock(goods.getStock());
					goodCart.setQuantity(cartGift.getCnt());
					goodCart.setAbbreviationPicture(goods.getAbbreviationPicture());
					goodCart.setSpec(goods.getSpec());
					goodCart.setIfPremiums(1);
					goodCart.setWeight(goods.getWeight());
					goodsList.add(goodCart);
				}
			}
					
        	 BigDecimal weight = new BigDecimal("0");
        	 
        	 if(goodsList!=null&&goodsList.size()>0){//计算商品总金额  总重量
        		for(OrderGoodCart orderGoodCart:goodsList){
        			if(orderGoodCart.getIfPremiums()==0){
        				if (orderGoodCart.getAppPrice() != null) {
            				goodsPrice = goodsPrice.add(orderGoodCart.getAppPrice().multiply(new BigDecimal(orderGoodCart.getQuantity())));
    					}
        			}
        			if(orderGoodCart.getWeight() != null){
        				weight =weight.add(orderGoodCart.getWeight().multiply(new BigDecimal(orderGoodCart.getQuantity())));
        			}
        		}
        	 }
        	 //计算优惠券 优惠金额
        	 if(StringUtils.hasText(couponCardId)){
        		 //查询该用户所能使用的优惠券
        		 List<Map<String, Object>> memberCouponUserList =getCouponByGoods(String.valueOf(memberId));
        		 for(Map<String, Object> couponMap : memberCouponUserList){
        			 if(couponMap.get("id")!=null&&Long.valueOf(couponCardId.trim()).longValue()==((Long)couponMap.get("id")).longValue()){
        				 if(String.valueOf(couponMap.get("scope")).equals("1")){//免运费
        					 feeflag=true;
        				 }else{
        					 if(couponMap.get("dis_price")!=null){
            					 couponDiscount = (BigDecimal)couponMap.get("dis_price");
            				 }
        				 }
        				 
        			 }
        		 }
        	 }
        	 String ysdjFlag ="0";// 0否   1 是
        	 CDeliveryWay deliveryWay = new CDeliveryWay();
        	 
        	 String deliveryCode="ptkd";//普通快递
             if(StringUtils.hasText(receiverId)){//收获地址
         		 TMemberReceiver memberReceiver = this.tmemberreceiverdao.selectByPrimaryKey(Long.valueOf(receiverId.trim()));
         		 if(checkYsdYsdjAreaId(String.valueOf(memberReceiver.getAreaId()))){
         			if (StringUtils.hasText(deliveryId)) {// 配送方式id
    					deliveryWay = this.cdeliverywaydao.selectByPrimaryKey(Long
    							.valueOf(deliveryId.trim()));
    					if (deliveryWay.getDeliveryCode().equals("ysdj")) {// 药师到家
    						ysdjFlag = "1";
    						shippingFee = deliveryWay.calculateFreight(weight);
    					}
    				} 
         		 }
         		if(ysdjFlag.equals("0")){
         			 if(checkYsdYsdjAreaId(String.valueOf(memberReceiver.getAreaId()))){//判断望京   是望京
             			 deliveryCode="ysd";//药士达
             		 }else if(checkHdfkArea(memberReceiver.getArea())){//判断是否是北京   
             			    if(!StringUtils.hasText(paymentWayId)){
             			    	paymentWayId ="1";
             			    }
             			    CPaymentWay paymentWay=	cpaymentwaydao.selectByPrimaryKey(Long.valueOf(paymentWayId.trim()));
    	         				if(paymentWay.getPaymentCode().equals("hdfk")){//货到付款
    	         					deliveryCode="hdfk";
    	         		}
             		 }
         		}
         	 }
        	 
        	 if(ysdjFlag.equals("0")){//不是 药师到家配送方式
        		//计算全场免运费金额
            	 if(!feeflag){
    			    	String free_shippingfee_price =tsysparameterdao.getKeys("free_shippingfee_price");
    			    	if(StringUtils.hasText(free_shippingfee_price)){
    			    		if(goodsPrice.compareTo(new BigDecimal(free_shippingfee_price.trim()))>=0){
    			    			feeflag=true;
    			    		}
    			    	}
    			    }
            	    paramMap.clear();
	 				paramMap.put("deliveryCode", deliveryCode);
	 			    List<CDeliveryWay> deliveryList =this.opensqldao.selectForListByMap(paramMap, "c_delivery_way.selectDeliveryWayByMap");
	 			    if(deliveryList!=null&&deliveryList.size()>0){
	 			    	deliveryWay = deliveryList.get(0);
	 			    	shippingFee=deliveryWay.calculateFreight(weight);
	 			    }
	            	if(feeflag){//是否免运费
	            		  shippingFee = new BigDecimal("0");
	            	 }
            	 
        	 }
        	
        	//订单金额
        	 BigDecimal goodsPriceNoShippingFee = goodsPrice.subtract(couponDiscount).subtract(promotionalDiscount);
        	 if(goodsPriceNoShippingFee.compareTo(new BigDecimal("0"))<=0){
        		 goodsPriceNoShippingFee=new BigDecimal("0");
        		 couponDiscount = goodsPrice.subtract(promotionalDiscount);
        	 }
        	 BigDecimal orderPrice = goodsPriceNoShippingFee.add(shippingFee);
        	 if(orderPrice.compareTo(new BigDecimal("0"))<=0){
        		 orderPrice = new BigDecimal("0");
        	 }
        	 resultMap.put("goodsPrice", goodsPrice);
        	 resultMap.put("orderPrice", orderPrice.setScale(2,   BigDecimal.ROUND_HALF_UP));
        	 resultMap.put("shippingFee", shippingFee);
        	 resultMap.put("couponDiscount", couponDiscount);
        	 resultMap.put("promotionalDiscount",promotionalDiscount);
        	 resultMap.put("payableAmount", orderPrice.setScale(2,   BigDecimal.ROUND_HALF_UP));
        	 resultMap.put("goodsList", goodsList);
        	 resultMap.put("deliveryWay", deliveryWay);
         }catch(Exception e){
        	 e.printStackTrace();
        	 throw new Exception(e.getMessage());
         }
		return resultMap;
	}
	
	
	/**
	 * 跳转到结算页面
	 */
	public OrderAddVO getOrderPromotionDiscount(List<OrderGoodCart> goodsList,long memberId,
			Long paymentWayId, Long receiverId, Long couponCodeId,String deliveryId) {
		OrderAddVO orderVO = new OrderAddVO();
		try{
		String 	receiverIds ="";
		String  paymentWayIds="";	
		String  couponCodeIds="";
		if(paymentWayId!=null&&paymentWayId.longValue()>0){
			paymentWayIds = String.valueOf(paymentWayId);
		}
		if(couponCodeId!=null&&couponCodeId.longValue()>0){
			couponCodeIds = String.valueOf(couponCodeId);
		}
		TMemberReceiver receiver = null;
		if(receiverId!=null){
			receiver =tmemberreceiverdao.selectByPrimaryKey(receiverId);
		}else{
			TMemberReceiverExample example = new TMemberReceiverExample();
			example.createCriteria().andMemberIdEqualTo(memberId);
			example.setOrderByClause(" is_default desc");
			List<TMemberReceiver> receiverList = tmemberreceiverdao.selectByExample(example);
			if(receiverList!=null&&receiverList.size()>0){
				receiver=receiverList.get(0);
				orderVO.setReceiver(new ReceiverVo(receiver));
			}
		}
		if(receiver!=null){
			orderVO.setReceiver(new ReceiverVo(receiver));
			receiverIds =String.valueOf(receiver.getId());
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		if(receiver!=null&&receiver.getArea()!=null){
			 if(!checkHdfkArea(receiver.getArea())){//判断北京
				 paramMap.put("isonline", 1);
		     }
		}
		List<OrderPaymethodVO> paymethodList =opensqldao.selectForListByMap(paramMap, "app_paymethod_area.selectByAreaIsnullOrByArea");
		orderVO.setPaymethodList(paymethodList);
		
		Map<String,Object> orderPriceMap = this.getOrderPrice(goodsList, couponCodeIds, receiverIds, String.valueOf(memberId), paymentWayIds,deliveryId);
		orderVO.setPromotionDiscount((BigDecimal)orderPriceMap.get("promotionalDiscount"));//促销折扣
		orderVO.setFreight((BigDecimal)orderPriceMap.get("shippingFee"));//运费
		orderVO.setAmountPayable((BigDecimal)orderPriceMap.get("payableAmount"));//应付金额
		orderVO.setSendPoint(((BigDecimal)orderPriceMap.get("payableAmount")).longValue());//赠送积分
		orderVO.setCouponDiscount((BigDecimal)orderPriceMap.get("couponDiscount"));//优惠券优惠金额
		orderVO.setPrice((BigDecimal)orderPriceMap.get("goodsPrice"));//商品总金额
		orderVO.setGoodsList((List<OrderGoodCart>)orderPriceMap.get("goodsList"));//商品集合
		if(orderPriceMap.get("deliveryWay")!=null){
			 CDeliveryWay deliveryWay = (CDeliveryWay) orderPriceMap.get("deliveryWay");
			 if(deliveryWay!=null){
				 if(deliveryWay.getId()!=null){
					 orderVO.setDeliveryId(String.valueOf(deliveryWay.getId()));
				 }
				 orderVO.setDeliveryName(deliveryWay.getName());
				 orderVO.setDeliveryInstro(deliveryWay.getInstro());
			 }
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return orderVO;
	}
	
	
	/**
	 * 创建订单
	 * @param orderVO 
	 * @param memberId  用户id
	 * @param paymentmethodId 支付方式id
	 * @param receiverId   收货人id
	 * @param couponCodeId   优惠券id
	 * @param proint   使用积分
	 * @param invoiceTitle     发票抬头
	 * @param source    订单来源
	 * @param oflag    0元标记
	 * @return
	 */
	public Object createOrder(OrderAddVO orderVO, long memberId,
			Long paymentmethodId, Long receiverId, Long couponCodeId,
			Long proint, String invoiceTitle, String paymentPluginId, String source,
			boolean oflag, HttpServletRequest request) throws Exception {
		   Long orderId =null;
		   String goodTitle="";
		   Object obj= null;
		   try{
			     TOrder order = new TOrder();
				 String orderSn =getOrderSn();
				 order.setOrderSn("m"+orderSn);//订单号
				 order.setMemberId(memberId);
				 if(source.equals("2")){//安卓
					 order.setOrderSource(3);//订单来源 1 pc 2 wap 3 安卓 4 ios 5 手动下单
				 }else if(source.equals("5")){//ios
					 order.setOrderSource(4);//订单来源 1 pc 2 wap 3 安卓 4 ios 5 手动下单
				 }
				 order.setShippingFee(orderVO.getFreight());//运费
				 order.setPromotionalDiscount(orderVO.getPromotionDiscount());//促销折扣
				 order.setCouponDiscount(orderVO.getCouponDiscount());//优惠券折扣
				 if(couponCodeId!=null){//优惠券id
					 order.setCouponId(couponCodeId);
				 }
//				 order.setOrderAmount(new BigDecimal("0.01"));//订单金额
//				 order.setPayableAmount(new BigDecimal("0.01"));//应付金额
				 order.setOrderAmount(orderVO.getAmountPayable());//订单金额
				 order.setPayableAmount(orderVO.getAmountPayable());//应付金额
				 order.setIntegrationDiscount(new BigDecimal("0"));//积分折扣
				 order.setUseIntegration(0);//使用积分
				 order.setAdjustAmount(new BigDecimal("0"));//调整金额
				 order.setPaymentId(paymentmethodId);//支付方式
				 order.setPaidAmount(new BigDecimal("0"));//已付金额
				 order.setRewardIntegration(order.getOrderAmount().intValue()/2);//赠送积分   
			   
				 order.setReceiver(orderVO.getReceiver().getConsignee());//收货人 
				 order.setAreaId(Long.valueOf(orderVO.getReceiver().getArea()));//地区id
				 order.setAreaName(orderVO.getReceiver().getAreaName());//地区名称
				 order.setDetailedAddress(orderVO.getReceiver().getAddress());//详细地址
				 order.setZipCode(orderVO.getReceiver().getZipCode());//邮编
				 order.setPhone(orderVO.getReceiver().getPhone());//联系电话
				 boolean hdfkFlag = false;
				 CPaymentWay paymentWay=	cpaymentwaydao.selectByPrimaryKey(paymentmethodId);
				 if(paymentWay.getPaymentCode().equals("hdfk")){//货到付款
    				 hdfkFlag=true;
    			 }
				 Map<String, Object> paramMap = new HashMap<String, Object>();
				 order.setDeliveryId(Long.valueOf(orderVO.getDeliveryId()));
			   if(StringUtils.hasText(invoiceTitle)){//发票抬头
				   order.setIfInvoice(1);
				   order.setInvoiceType(1);//发票内容类型 1 明细 2 药品 3 保健品 4 医疗器械
				   order.setInvoiceTitle(invoiceTitle);
			   }else{
				   order.setIfInvoice(0);
				   order.setInvoiceTitle("");
			   }
			     
				 order.setIsPush(0);//是否推送海典 0 否 1 是
				 order.setPushTime(null);//推送海淀时间
				 order.setDeleteStatus(0);//删除状态 0 未删除 1 已删除
				 if(hdfkFlag||order.getPayableAmount().compareTo(new BigDecimal("0"))<=0){
					 order.setOrderStatus(1);//订单状态  待发货
				 }else{
					 order.setOrderStatus(0);//订单状态 0 未支付
				 }
				 order.setVerifyStatus(0);//核实状态  0 未核实  1 已核实
				 order.setOrderType(1);//订单类型  1-正常 2-秒杀 3-团购
				 order.setReturnType(1);//返佣状态 1 未返佣  2 已返佣
				 order.setCreateDate(new Date());//下单时间
				 
//////////////////////////////增加 大兴免运费暂时使用/////////////////////////////////////////////////////
				 
//				 TMemberReceiver memberReceiver = this.tmemberreceiverdao.selectByPrimaryKey(receiverId);
//	      		 if(String.valueOf(memberReceiver.getAreaId()).equals("1158")){//大兴区域
//	      			order.setShippingFee(new BigDecimal("0"));//运费
//	      			order.setOrderAmount(orderVO.getAmountPayable().subtract(orderVO.getFreight()));// 订单金额
//	    			order.setPayableAmount(orderVO.getAmountPayable().subtract(orderVO.getFreight()));// 应付金额
//	    			order.setDeliveryId(2l);//药士达
//	      		 }
				 
				//保存订单
				 orderId =this.torderdao.insertSelective(order);
				//判断商品
				 Map<String,Object> goodMap = null;
				 TOrderItem orderItem = null;
				 TGoodsBrokerageExample brokerageExample = null;
				 TGoodsPriceExample priceExample = null;
				 for(OrderGoodCart goodCart: orderVO.getGoodsList()){
					 goodTitle =goodTitle+goodCart.getGoodsName()+",";
					 paramMap.clear();
					 paramMap.put("id", goodCart.getId());
					 goodMap = (Map<String, Object>) this.opensqldao.selectForObjectByMap(paramMap, "t_goods.selectByPrimaryKeyForUpdate");
				     orderItem = new TOrderItem();
					 orderItem.setOrderId(orderId);
					 orderItem.setCreateTime(new Date());
					 orderItem.setGoodsId((Long)goodMap.get("id"));
					 orderItem.setIfPremiums(goodCart.getIfPremiums());
					 if(goodCart.getIfPremiums().intValue()==1){//赠品
						 orderItem.setPrice(new BigDecimal("0"));
						 if(goodCart.getStquan().intValue()>=0){//判断赠品库存是否充足
							 orderItem.setQuantity(goodCart.getQuantity());
						 }else{
							 orderItem.setQuantity(goodCart.getStock().intValue());
						 }
					 }else{
						    priceExample = new TGoodsPriceExample();
							priceExample.createCriteria().andGoodsIdEqualTo(orderItem.getGoodsId()).andPriceTypeEqualTo("app").andPlatformTypeEqualTo("111yao");
							List<TGoodsPrice> priceList =tgoodspricedao.selectByExample(priceExample);
							if(priceList!=null&&priceList.size()>0){
								 orderItem.setPrice(priceList.get(0).getPrice());
							}else{
								orderItem.setPrice(new BigDecimal("0"));
							}
						 orderItem.setQuantity(goodCart.getQuantity());
					 }
					 brokerageExample = new TGoodsBrokerageExample();
					 brokerageExample.createCriteria().andGoodsIdEqualTo(orderItem.getGoodsId());
					 List<TGoodsBrokerage> brokerageList= tgoodsbrokeragedao.selectByExample(brokerageExample);
					 if(brokerageList!=null&&brokerageList.size()>0){
						 orderItem.setBrokerage(brokerageList.get(0).getBrokerage());
					 }else{
						 orderItem.setBrokerage(Long.valueOf(InfoUtil.getInstance().getInfo("config", "good_yongjin_lilv").trim()));
					 }
					 orderItem.setIfReviews(0);
					 this.torderitemdao.insertSelective(orderItem);
					 paramMap.clear();
					 paramMap.put("id", goodCart.getId());
					 paramMap.put("quantity", goodCart.getQuantity());
					 this.opensqldao.updateByMap_drug(paramMap, "t_goods.updateGoodStockById");
				 }
//				 删除购物车
				 TCartExample cartExample = new TCartExample();
				 cartExample.createCriteria().andMemberIdEqualTo(order.getMemberId());
				 List<TCart> cartList =this.tcartdao.selectByExample(cartExample);
				 if(cartList!=null&&cartList.size()>0){
					 TCartItemExample cartItemExample = new TCartItemExample();
					 cartItemExample.createCriteria().andCartIdEqualTo(cartList.get(0).getId()).andIsSelectedEqualTo(1);
					 this.tcartitemdao.deleteByExample(cartItemExample);
				 }	 
				 
				//更新优惠券使用信息
				 if(couponCodeId!=null){
					 TCouponCard couponCard = new TCouponCard();
					 couponCard.setId(couponCodeId);
					 couponCard.setIsUse(1);//是否使用 1 已使用
					 couponCard.setUseTime(new Date());//使用时间
					 couponCard.setMemberId(order.getMemberId());
					 tcouponcarddao.updateByPrimaryKeySelective(couponCard);
				 }
				 if(paymentPluginId!=null &&paymentWay.getPaymentWay().intValue()== 0){//线上支付
					   TOrderFlow orderFlow =new TOrderFlow();
						orderFlow.setMemberId(order.getMemberId());
						orderFlow.setPaymentStatus(0);//支付状态 0 未支付 1已支付
						if("wzfMobilePlugin".equals(paymentPluginId)){//微信
						  orderFlow.setPaymentPlugin("wzfMobilePlugin");
						  orderFlow.setPayMethod("APP微信支付");
						}else if("alipayMobilePlugin".equals(paymentPluginId)){//支付宝支付
						  orderFlow.setPaymentPlugin("alipayMobilePlugin");
						  orderFlow.setPayMethod("APP支付宝支付");
						}
						orderFlow.setHavePay(order.getPayableAmount());
						orderFlow.setCreateTime(new Date());
						orderFlow.setOrderId(orderId);
						orderFlow.setPayDate(new Date());
					    this.torderflowdao.insertSelective(orderFlow);
					    if(!oflag){
					    	obj =getPayVO(paymentPluginId,goodTitle,order,orderId,request);
						}else{
							if(orderVO.getAmountPayable().compareTo(new BigDecimal(0)) > 0){
							obj =getPayVO(paymentPluginId,goodTitle,order,orderId,request);
							}
						}   
				 }
		   }catch(Exception e){
			   e.printStackTrace();
			   throw new Exception(e.getMessage());
		   }
		   return obj;
	}
	/**
	 * 获取支付提交信息
	 * @param paymentPluginId
	 * @param goodTitle
	 * @param order
	 * @param orderId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Object getPayVO(String paymentPluginId,String goodTitle,TOrder order,long orderId,HttpServletRequest request) throws Exception{
		Object obj = null;
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
//			    wzfMap.put("total_fee","1");//上线时 要修改
			    wzfMap.put("spbill_create_ip",NetworkUtil.getIpAddress(request));    //终端IP  APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。  (必填)
			    wzfMap.put("time_start","");    //交易起始时间  订单生成时间，格式为yyyyMMddHHmms
			    wzfMap.put("time_expire","");    //交易结束时间   订单失效时间，格式为yyyyMMddHHmmss
			    wzfMap.put("goods_tag","");    //商品标记   商品标记，代金券或立减优惠功能的参数，说明详见 WXG
			    wzfMap.put("notify_url",InfoUtil.getInstance().getInfo("config", "wzf.wxPay_notify_url") );    //通知地址  接收微信支付异步通知回调地址   (必填)
			    wzfMap.put("trade_type","APP");    //交易类型  取值如下：JSAPI，NATIVE，APP，WAP  (必填)
			    wzfMap.put("product_id",String.valueOf(order.getId()) );    //商品ID  rade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
			    wzfMap.put("openid","");    //用户标识  trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。下单前需要调用【网页授权获取用户信息】接口获取到用户的Openid。
				//app111yao   旧      newapp111yao  新
			    String client_type = request.getParameter("client_type"); //客户端类型
			    String client_version = request.getParameter("client_version"); //客户端版本
			    if((client_type.equals("iOS")&&client_version.equals("v2.1.2"))||(client_type.equals("Android")&&client_version.equals("2.1.2"))){
			    	 wzfMap.put("source", "newapp111yao");
			    }else{
			    	 wzfMap.put("source", "app111yao");
			    }
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
					UnifiedOrderResDataAPPAPI dataAPPAPI = (UnifiedOrderResDataAPPAPI) JSONObject
							.toBean(JSONObject.fromObject(postResult),
									UnifiedOrderResDataAPPAPI.class);
					WzfPayVO payVO = new WzfPayVO(dataAPPAPI);
					payVO.setOrderId(String.valueOf(orderId));
					obj = payVO;
				}else{
					throw new Exception("=====>提交订单到微信失败");
				}
				
		}else if("alipayMobilePlugin".equals(paymentPluginId)){//支付
			MobileAlipayRequestVO requestVO = new MobileAlipayRequestVO();
			requestVO.setOut_trade_no(order.getOrderSn());//订单号
	    	requestVO.setTotal_fee(NumberUtil.format2String(order.getPayableAmount()));//订单支付金额
	    	requestVO.setSubject(goodTitle.substring(0, goodTitle.length()-1).length()>120?goodTitle.substring(0, 120):goodTitle.substring(0, goodTitle.length()-1));
	    	requestVO.setBody(goodTitle.substring(0, goodTitle.length()-1).length()>500?goodTitle.substring(0, 500):goodTitle.substring(0, goodTitle.length()-1));
	    	requestVO.setNotify_url(InfoUtil.getInstance().getInfo("config", "alipay.notify_url"));
	    	requestVO.setSeller_id(InfoUtil.getInstance().getInfo("config", "alipay.seller_id"));
	    	requestVO.setOrderId(String.valueOf(orderId));
	    	obj=requestVO;
		}
		return obj;
	}
	
	
	/**
	 * 获取订单号
	 * @return
	 */
	public String getOrderSn(){
		   Map<String,Object> paramMap = new HashMap<String,Object>();
		   paramMap.put("len", 6);
		   String ordersn =(String) opensqldao.selectForObjectByMap(paramMap, "t_order.select_order_sn_call");
		   if("-1".equals(ordersn)){
			  return getOrderSn();
		   }else{
			   return ordersn;
		   }
	}
	public List<OrderPaymethodVO> getOrderPaymethodVOByOrderId(long orderId)
			throws Exception {
		 List<OrderPaymethodVO> paymethodList = new ArrayList<OrderPaymethodVO>();
		   try{
				// 查询订单
				TOrder order = this.torderdao.selectByPrimaryKey(orderId);
				Map<String,Object> paramMap = new HashMap<String,Object>();
				
				if(order!=null&&order.getAreaName()!=null){
					 if(!checkHdfkArea(order.getAreaName())){//判断北京
						 paramMap.put("isonline", 1);
				     }
				}
				 paymethodList =opensqldao.selectForListByMap(paramMap, "app_paymethod_area.selectByAreaIsnullOrByArea");
		   }catch(Exception e){
			   e.printStackTrace();
			   throw new Exception(e.getMessage());
		   }
		   return paymethodList;
	}
	public BigDecimal getOrderFrieghtPrice(long orderId, long memberId,long paymentMethodId) throws Exception {
		// 查询订单
		  TOrder order = this.torderdao.selectByPrimaryKey(orderId);
		   BigDecimal freight = new BigDecimal("0");
		    BigDecimal weight = new BigDecimal("0");
		    BigDecimal price = new BigDecimal("0"); //商品金额	
		    boolean feeflag = false;
			if(order.getCouponId()!=null){//判断是否使用免运费优惠券
				TCouponCard couponCard = tcouponcarddao.selectByPrimaryKey(order.getCouponId());
				if(couponCard!=null&&couponCard.getTicketId()!=null){
					TCoupon tcoupon =tcoupondao.selectByPrimaryKey(couponCard.getTicketId());
					if(tcoupon!=null&&tcoupon.getScope()!=null&&tcoupon.getScope().intValue()==1){//免运费
						feeflag = true;
					}
				}
			}
			Map<String,Object> paramMap = new HashMap<String,Object>(); 
		    paramMap.put("orderId", order.getId());
		    List orderPirceWeightlist =opensqldao.selectForListByMap(paramMap, "t_order.selectGoodsPriceWeightByorderID");
		    if(orderPirceWeightlist!=null&&orderPirceWeightlist.size()>0){
		    	Map<String,Object> resultMap = (Map<String, Object>) orderPirceWeightlist.get(0);
		    	if(resultMap.get("pricesum")!=null){
		    		price =(BigDecimal) resultMap.get("pricesum");
		    	}
		    	if(resultMap.get("weightsum")!=null){
		    		weight=(BigDecimal) resultMap.get("weightsum");
		    	}
		    }
		    if(!feeflag){
		    	String free_shippingfee_price =tsysparameterdao.getKeys("free_shippingfee_price");
		    	if(StringUtils.hasText(free_shippingfee_price)){
		    		if(price.compareTo(new BigDecimal(free_shippingfee_price.trim()))>=0){
		    			feeflag=true;
		    		}
		    	}
		    }
		    //计算运费
		    CPaymentWay paymentWay= null;
		    if(!checkYsdYsdjAreaId(String.valueOf(order.getAreaId()))&&!feeflag){//判断望京
				 paymentWay=	cpaymentwaydao.selectByPrimaryKey(paymentMethodId);
				String deliveryCode="";
				if(paymentWay.getPaymentCode().equals("hdfk")){//货到付款
					deliveryCode="hdfk";
				}else{
					deliveryCode="ptkd";
				}
				paramMap.clear();
				paramMap.put("deliveryCode", deliveryCode);
				
			    List<CDeliveryWay> deliveryList =opensqldao.selectForListByMap(paramMap, "c_delivery_way.selectDeliveryWayByMap");
			    if(deliveryList!=null&&deliveryList.size()>0){
			    	CDeliveryWay deliveryWay = deliveryList.get(0);
			    	freight=deliveryWay.calculateFreight(weight);
			    }
		   }
		
		return freight;
	}
	
	
	public TPromotionDAO getTpromotiondao() {
		return tpromotiondao;
	}
	public void setTpromotiondao(TPromotionDAO tpromotiondao) {
		this.tpromotiondao = tpromotiondao;
	}
	public OpenSqlDAO getOpensqldao() {
		return opensqldao;
	}
	public void setOpensqldao(OpenSqlDAO opensqldao) {
		this.opensqldao = opensqldao;
	}
	public CPaymentWayDAO getCpaymentwaydao() {
		return cpaymentwaydao;
	}
	public void setCpaymentwaydao(CPaymentWayDAO cpaymentwaydao) {
		this.cpaymentwaydao = cpaymentwaydao;
	}
	public TOrderFlowDAO getTorderflowdao() {
		return torderflowdao;
	}
	public void setTorderflowdao(TOrderFlowDAO torderflowdao) {
		this.torderflowdao = torderflowdao;
	}
	public TOrderItemDAO getTorderitemdao() {
		return torderitemdao;
	}
	public void setTorderitemdao(TOrderItemDAO torderitemdao) {
		this.torderitemdao = torderitemdao;
	}
	public TMemberReceiverDAO getTmemberreceiverdao() {
		return tmemberreceiverdao;
	}
	public void setTmemberreceiverdao(TMemberReceiverDAO tmemberreceiverdao) {
		this.tmemberreceiverdao = tmemberreceiverdao;
	}
	public TGoodsDAO getTgoodsdao() {
		return tgoodsdao;
	}
	public void setTgoodsdao(TGoodsDAO tgoodsdao) {
		this.tgoodsdao = tgoodsdao;
	}
	public CartDaoImpl getCartdao() {
		return cartdao;
	}
	public void setCartdao(CartDaoImpl cartdao) {
		this.cartdao = cartdao;
	}
	public TSysParameterDAO getTsysparameterdao() {
		return tsysparameterdao;
	}
	public void setTsysparameterdao(TSysParameterDAO tsysparameterdao) {
		this.tsysparameterdao = tsysparameterdao;
	}
	public TCartDAO getTcartdao() {
		return tcartdao;
	}
	public void setTcartdao(TCartDAO tcartdao) {
		this.tcartdao = tcartdao;
	}
	public TCartItemDAO getTcartitemdao() {
		return tcartitemdao;
	}
	public void setTcartitemdao(TCartItemDAO tcartitemdao) {
		this.tcartitemdao = tcartitemdao;
	}
	public TCouponCardDAO getTcouponcarddao() {
		return tcouponcarddao;
	}
	public void setTcouponcarddao(TCouponCardDAO tcouponcarddao) {
		this.tcouponcarddao = tcouponcarddao;
	}
	
	public TMemberDAO getTmemberdao() {
		return tmemberdao;
	}
	public void setTmemberdao(TMemberDAO tmemberdao) {
		this.tmemberdao = tmemberdao;
	}
	public TMemberIntegralDAO getTmemberintegraldao() {
		return tmemberintegraldao;
	}
	public void setTmemberintegraldao(TMemberIntegralDAO tmemberintegraldao) {
		this.tmemberintegraldao = tmemberintegraldao;
	}
	public TOrderLeaderDAO getTorderleaderdao() {
		return torderleaderdao;
	}
	public void setTorderleaderdao(TOrderLeaderDAO torderleaderdao) {
		this.torderleaderdao = torderleaderdao;
	}
	public TMemberLeaderDAO getTmemberleaderdao() {
		return tmemberleaderdao;
	}
	public void setTmemberleaderdao(TMemberLeaderDAO tmemberleaderdao) {
		this.tmemberleaderdao = tmemberleaderdao;
	}
	//取消订单
	public int cancelOrder(Long memberId, String sn) throws Exception {
		TOrderExample toe = new TOrderExample();
		toe.createCriteria().andMemberIdEqualTo(memberId).andOrderSnEqualTo(sn);
		List list =torderdao.selectByExample(toe);
		if(null != list && list.size()==1){
			TOrder order = (TOrder) list.get(0);
			TOrder torder = new TOrder();
			torder.setOrderStatus(4);
			TOrderItemExample toie = new TOrderItemExample();
			toie.createCriteria().andOrderIdEqualTo(order.getId());
			List itemList = torderitemdao.selectByExample(toie);
			for(int i=0; i>itemList.size();i++){
				TOrderItem item=(TOrderItem) itemList.get(i);
				TGoods goods = tgoodsdao.selectByPrimaryKey(item.getGoodsId());
				TGoods g = new TGoods();
				g.setStock(goods.getStock()+item.getQuantity());
				g.setId(goods.getId());
				tgoodsdao.updateByPrimaryKeySelective(g);
			}
			return torderdao.updateByExampleSelective(torder, toe);
		}else{
			return 0;
		}
	}
	public TCouponDAO getTcoupondao() {
		return tcoupondao;
	}
	public void setTcoupondao(TCouponDAO tcoupondao) {
		this.tcoupondao = tcoupondao;
	}
	public TGoodsBrokerageDAO getTgoodsbrokeragedao() {
		return tgoodsbrokeragedao;
	}
	public void setTgoodsbrokeragedao(TGoodsBrokerageDAO tgoodsbrokeragedao) {
		this.tgoodsbrokeragedao = tgoodsbrokeragedao;
	}
	
	public MessageSender getTopicMessageSender() {
		return topicMessageSender;
	}
	public void setTopicMessageSender(MessageSender topicMessageSender) {
		this.topicMessageSender = topicMessageSender;
	}
	/**
	 * 确认收获
	 */
	public void finishOrder(TOrder order,String publicServiceUrl) throws Exception {
		TOrder torder = new TOrder();
		torder.setId(order.getId());
		torder.setOrderStatus(3);//已完成
		torder.setFinishDate(new Date());
		this.torderdao.updateByPrimaryKeySelective(torder);
		
		long memberId = order.getMemberId();
		
		//TMember updateMember = new TMember();
		//updateMember.setId(memberId);
		//updateMember.setIntegral(this.tmemberdao.selectByPrimaryKey(memberId).getIntegral()+order.getRewardIntegration());
		//tmemberdao.updateByPrimaryKeySelective(updateMember);
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", String.valueOf(memberId));
		map.put("jifen", String.valueOf(order.getRewardIntegration()));
		map.put("version", "v7");
		topicMessageSender.sendMapMessage(map);
		TMemberIntegral memberIntegral = new TMemberIntegral();
		memberIntegral.setMemberId(memberId);
		memberIntegral.setCreateDate(new Date());
		memberIntegral.setSource(9);
		memberIntegral.setIntegral(order.getRewardIntegration());
		tmemberintegraldao.insertSelective(memberIntegral);
		CPaymentWay cPaymentWay = cpaymentwaydao.selectByPrimaryKey(order.getPaymentId());
		if(cPaymentWay!=null && cPaymentWay.getPaymentCode()!=null ){
			if (order != null && order.getMemberId() != null && order.getMemberId() > 0L) {
				Map<String,String> publicMap = new HashMap<String,String>();
				publicMap.put("orderId", String.valueOf(order.getId()));
				String resultJsonstr =ClientSubmitPublic.getPublicService(publicMap, publicServiceUrl+"leaderStayMoneyURLService");
				if(JSONObject.fromObject(resultJsonstr).get("statsCode").equals("0")){
					throw new Exception(String.valueOf(order.getId())+":佣金计算失败,失败原因:"+JSONObject.fromObject(resultJsonstr).get("message"));
				}
			}
		}
		
		
	}
	public CDeliveryWayDAO getCdeliverywaydao() {
		return cdeliverywaydao;
	}
	public void setCdeliverywaydao(CDeliveryWayDAO cdeliverywaydao) {
		this.cdeliverywaydao = cdeliverywaydao;
	}
	public TGoodsPriceDAO getTgoodspricedao() {
		return tgoodspricedao;
	}
	public void setTgoodspricedao(TGoodsPriceDAO tgoodspricedao) {
		this.tgoodspricedao = tgoodspricedao;
	}
	
	
	
}
