package com.rc.openapi.model;

import java.util.List;


/**
 * 售后详情
 *
 */
public class ReturnsVO {
	/**
	 * 订单编号
	 */
	private String osn;
	/**
	 * 售后单编号
	 */
	private String rsn;
	/**
	 * 下单时间
	 */
	private String oDate;
	/**
	 * 发起售后时间
	 */
	private String rDate;
	/**
	 * 支付方式
	 */
	private String paymentMethodName;
	/**
	 * 售后状态
	 */
	private String status;
	/**
	 * 后台回复
	 */
	private String remarks;
	/**
	 * 问题描述
	 */
	private String description;
	/**
	 * 售后商品
	 */
	private List<Product1Vo> plist;
	/**
	 * 收货地址
	 */
	private String area;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 收货人电话
	 */
	private String phone;
	/**
	 * 物流信息
	 */
	private String logisticsInfo;
	/**
	 * 总金额
	 */
	private String priceSum;
	
	public String getPriceSum() {
		return priceSum;
	}
	public void setPriceSum(String priceSum) {
		this.priceSum = priceSum;
	}
	public String getLogisticsInfo() {
		return logisticsInfo;
	}
	public void setLogisticsInfo(String logisticsInfo) {
		this.logisticsInfo = logisticsInfo;
	}
	public String getoDate() {
		return oDate;
	}
	public void setoDate(String oDate) {
		this.oDate = oDate;
	}
	public String getrDate() {
		return rDate;
	}
	public void setrDate(String rDate) {
		this.rDate = rDate;
	}
	public String getOsn() {
		return osn;
	}
	public void setOsn(String osn) {
		this.osn = osn;
	}
	public String getRsn() {
		return rsn;
	}
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
	public String getPaymentMethodName() {
		return paymentMethodName;
	}
	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Product1Vo> getPlist() {
		return plist;
	}
	public void setPlist(List<Product1Vo> plist) {
		this.plist = plist;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
