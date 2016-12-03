package com.rc.openapi.model;

import java.io.Serializable;
import java.util.List;

import com.rc.openapi.util.NumberUtil;

/**
 * 订单购物车VO
 * 
 * @author user3
 * 
 */
public class OrderCartVO implements Serializable {
	private static final long serialVersionUID = 3888308756775199448L;
	/**
	 * 促销折扣
	 */
	private String promotionDiscount;
	/**
	 * 订单商品价格总和
	 */
	private String price;
	/**
	 * 应付金额
	 */
	private String amountPayable;
	
	/**
	 * 运费
	 */
	private String freight;
	/**
	 * 优惠券折扣
	 */
	private String couponDiscount;
	/**
	 * 收货地址
	 */
	private ReceiverVo receiver;
	/**
	 * 赠送积分
	 */
	private String sendPoint;
     
	private List<OrderPaymethodVO> paymethodList;
	
	/**
	 * 配送方式id
	 */
	private String deliveryId;
	/**
	 * 配送方式名称
	 */
	private String deliveryName;
	/**
	 * 配送方式描述
	 */
	private String deliveryInstro;
	

	public OrderCartVO() {
		super();
	}

	public OrderCartVO(OrderAddVO orderVO) {
		this.promotionDiscount = NumberUtil.format2String(orderVO.getPromotionDiscount());
		this.price = NumberUtil.format2String(orderVO.getPrice());
		this.amountPayable = NumberUtil.format2String(orderVO.getAmountPayable());
		this.freight = NumberUtil.format2String(orderVO.getFreight());
		this.couponDiscount = NumberUtil.format2String(orderVO.getCouponDiscount());
		this.receiver = orderVO.getReceiver();
		this.sendPoint = NumberUtil.long2String(orderVO.getSendPoint());
		this.paymethodList = orderVO.getPaymethodList();
		this.deliveryId = orderVO.getDeliveryId();
		this.deliveryName = orderVO.getDeliveryName();
		this.deliveryInstro = orderVO.getDeliveryInstro();
	}
	
	
	
	public List<OrderPaymethodVO> getPaymethodList() {
		return paymethodList;
	}

	public void setPaymethodList(List<OrderPaymethodVO> paymethodList) {
		this.paymethodList = paymethodList;
	}

	public String getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscount(String promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(String amountPayable) {
		this.amountPayable = amountPayable;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(String couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public ReceiverVo getReceiver() {
		return receiver;
	}

	public void setReceiver(ReceiverVo receiver) {
		this.receiver = receiver;
	}

	public String getSendPoint() {
		return sendPoint;
	}

	public void setSendPoint(String sendPoint) {
		this.sendPoint = sendPoint;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryInstro() {
		return deliveryInstro;
	}

	public void setDeliveryInstro(String deliveryInstro) {
		this.deliveryInstro = deliveryInstro;
	}
	
	
	
	
	

}
