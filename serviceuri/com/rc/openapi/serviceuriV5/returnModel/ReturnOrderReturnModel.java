package com.rc.openapi.serviceuriV5.returnModel;

import java.io.Serializable;
import java.util.List;

/**
 * 返回退换货接口实体
 * 
 * @author WWF
 * @createTime 2016-9-5 上午10:28:04
 */
public class ReturnOrderReturnModel implements Serializable {

	private static final long serialVersionUID = -2024083001156159584L;

	private String id;

	/**
	 * 订单号
	 */
	private String orderSn;

	/**
	 * 服务类型退货 Refunds(0), 换货 exchange(1) ,2:退款(无需退货)
	 */
	private String serviceType;

	/**
	 * 快递单号
	 */
	private String expressDelivery;

	/**
	 * 快递公司
	 */
	private String expressCompany;

	/**
	 * 发货人电话
	 */
	private String shipperPhone;

	/**
	 * 会员id
	 */
	private String memberId;

	/**
	 * 退款金额
	 */
	private String refundAmount;

	/**
	 * 退款描述
	 */
	private String refundDescribe;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 退款账号
	 */
	private String refundAccount;

	/**
	 * 退款银行
	 */
	private String refundBank;

	/**
	 * 退款备注
	 */
	private String refundRemark;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 原订单id
	 */
	private String oldOrderId;

	/**
	 * 完成时间
	 */
	private String finishDt;
	
	private List<ReturnTGoodModel> goodsList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getExpressDelivery() {
		return expressDelivery;
	}

	public void setExpressDelivery(String expressDelivery) {
		this.expressDelivery = expressDelivery;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getShipperPhone() {
		return shipperPhone;
	}

	public void setShipperPhone(String shipperPhone) {
		this.shipperPhone = shipperPhone;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundDescribe() {
		return refundDescribe;
	}

	public void setRefundDescribe(String refundDescribe) {
		this.refundDescribe = refundDescribe;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRefundAccount() {
		return refundAccount;
	}

	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}

	public String getRefundBank() {
		return refundBank;
	}

	public void setRefundBank(String refundBank) {
		this.refundBank = refundBank;
	}

	public String getRefundRemark() {
		return refundRemark;
	}

	public void setRefundRemark(String refundRemark) {
		this.refundRemark = refundRemark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOldOrderId() {
		return oldOrderId;
	}

	public void setOldOrderId(String oldOrderId) {
		this.oldOrderId = oldOrderId;
	}

	public String getFinishDt() {
		return finishDt;
	}

	public void setFinishDt(String finishDt) {
		this.finishDt = finishDt;
	}

	public List<ReturnTGoodModel> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<ReturnTGoodModel> goodsList) {
		this.goodsList = goodsList;
	}

}
