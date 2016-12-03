package com.rc.openapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单添加daoVO
 * 
 * @author user3
 * 
 */
public class OrderAddVO implements Serializable {
	private static final long serialVersionUID = -7848888954192790919L;
	/**
	 * 促销折扣
	 */
	private BigDecimal promotionDiscount;
	/**
	 * 订单商品价格总和
	 */
	private BigDecimal price;
	/**
	 * 应付金额
	 */
	private BigDecimal amountPayable;
	/**
	 *  积分抵扣 
	 */
	private BigDecimal pointDiscount;
	/**
	 * 运费
	 */
	private BigDecimal freight;
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
	
	/**
	 * 优惠券折扣
	 */
	private BigDecimal couponDiscount;
	
	/**
	 * 收货地址
	 */
	private ReceiverVo receiver;
	/**
	 * 赠送积分
	 */
	private Long sendPoint;
	/**
	 * 支付方式list
	 * @return
	 */
	private List<OrderPaymethodVO> paymethodList = new ArrayList<OrderPaymethodVO>();
	/**
	 * 购物车商品集合
	 */
	private  List<OrderGoodCart> goodsList;
	

	public BigDecimal getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscount(BigDecimal promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
	}

	public BigDecimal getPointDiscount() {
		return pointDiscount;
	}

	public void setPointDiscount(BigDecimal pointDiscount) {
		this.pointDiscount = pointDiscount;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public ReceiverVo getReceiver() {
		return receiver;
	}

	public void setReceiver(ReceiverVo receiver) {
		this.receiver = receiver;
	}

	public Long getSendPoint() {
		return sendPoint;
	}

	public void setSendPoint(Long sendPoint) {
		this.sendPoint = sendPoint;
	}

	public List<OrderPaymethodVO> getPaymethodList() {
		return paymethodList;
	}

	public void setPaymethodList(List<OrderPaymethodVO> paymethodList) {
		this.paymethodList = paymethodList;
	}

	public List<OrderGoodCart> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<OrderGoodCart> goodsList) {
		this.goodsList = goodsList;
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
