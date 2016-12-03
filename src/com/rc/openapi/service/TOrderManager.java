package com.rc.openapi.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rc.openapi.model.OrderAddVO;
import com.rc.openapi.model.OrderGoodCart;
import com.rc.openapi.model.OrderPaymethodVO;
import com.rc.openapi.vo.TOrder;
import com.rc.openapi.vo.TOrderExample;

public interface TOrderManager {
    int countByExample(TOrderExample example) throws SQLException;

    int deleteByExample(TOrderExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TOrder record) throws SQLException;

    Long insertSelective(TOrder record) throws SQLException;

    List selectByExample(TOrderExample example) throws SQLException;

    TOrder selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TOrder record, TOrderExample example) throws SQLException;

    int updateByExample(TOrder record, TOrderExample example) throws SQLException;

    int updateByPrimaryKeySelective(TOrder record) throws SQLException;

    int updateByPrimaryKey(TOrder record) throws SQLException;
     
    /**
	 * 更改订单支付方式 去支付
	 * 
	 * @param orderId
	 * @param memberId
	 * @param paymentMethodId
	 * @param paymentPluginId
	 * @throws Exception
	 */
	public Object updateOrderPayment(TOrder order,long memberId,
			long paymentmethodId, String paymentPluginId,
			HttpServletRequest request) throws Exception;
	
	/**
	 * 根据购物车商品计算优惠券
	 * 
	 * @return
	 * @throws SQLException
	 */
    public List<Map<String,Object>> getCouponByGoods(String memberId) throws SQLException;
    
    
    /**
	 * 结算
	 * 
	 * @param memberId
	 *            用户id
	 * @param paymentmethodId
	 *            支付方式id
	 * @param receiverId
	 *            收货人id
	 * @param couponCodeId
	 *            优惠券id
	 * @param deliveryId
	 *            配送方式id     
	 * @return
	 */
	OrderAddVO getOrderPromotionDiscount(List<OrderGoodCart> goodsList,long memberId, Long paymentMethodId,
			Long receiverId, Long couponCodeId,String deliveryId);

	/**
	 * 创建订单
	 * 
	 * @param orderVO
	 * @param memberId
	 *            用户id
	 * @param paymentmethodId
	 *            支付方式id
	 * @param receiverId
	 *            收货人id
	 * @param couponCodeId
	 *            优惠券id
	 * @param proint
	 *            使用积分
	 * @param invoiceTitle
	 *            发票抬头
	 * @param source
	 *            订单来源
	 * @return
	 */
	Object createOrder(OrderAddVO orderVO, long memberId, Long paymentmethodId,
			Long receiverId, Long couponCodeId, Long proint, String invoiceTitle,
			String paymentPluginId, String source,boolean oflag, HttpServletRequest request)
			throws Exception;
	
	//取消订单
	int cancelOrder(Long memberId ,String sn)throws Exception;
	
	
	/**
	 * 根据订单id 查询 支付方式
	 * 
	 * @param orderId
	 * @throws Exception
	 */
	public List<OrderPaymethodVO> getOrderPaymethodVOByOrderId(long orderId)
			throws Exception;
	/**
	 * 计算订单运费
	 * 
	 * @param orderId
	 *            订单id
	 * @param memberId
	 *            用户id
	 * @param paymentMethodId
	 *            支付方式id
	 * @throws Exception
	 */
	public BigDecimal getOrderFrieghtPrice(long orderId, long memberId,
			long paymentMethodId) throws Exception;
	
    /**
     * 确认收获
     * @param orderId
     * @throws Exception
     */
    void finishOrder(TOrder order,String publicServiceUrl) throws Exception;
}
