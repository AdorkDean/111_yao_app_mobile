package com.rc.openapi.serviceuriV5.order.model;

/**
 * 返回数据实体
 * 
 * @author WWF
 * @createTime 2016-8-25 下午1:47:48
 */
public class ResultOrderItem {

	private String id;

	private String goodsId;

	private String ifPremiums;

	private String quantity;

	private String price;

	private String ifReviews;

	private String createTime;

	private String orderId;

	private String brokerage;

	private String refundProcess;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getIfPremiums() {
		return ifPremiums;
	}

	public void setIfPremiums(String ifPremiums) {
		this.ifPremiums = ifPremiums;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIfReviews() {
		return ifReviews;
	}

	public void setIfReviews(String ifReviews) {
		this.ifReviews = ifReviews;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(String brokerage) {
		this.brokerage = brokerage;
	}

	public String getRefundProcess() {
		return refundProcess;
	}

	public void setRefundProcess(String refundProcess) {
		this.refundProcess = refundProcess;
	}

}
