package com.rc.openapi.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TReturn {
    private Long id;

    private String orderSn;

    private Integer serviceType;

    private String expressDelivery;

    private String expressCompany;

    private String shipperPhone;

    private Long memberId;

    private BigDecimal refundAmount;

    private String refundDescribe;

    private Integer orderStatus;

    private String refundAccount;

    private String refundBank;

    private String refundRemark;

    private Date createTime;

    private Long oldOrderId;
    
    private List<String> imgPathList;
    private String products;
    
    public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public List<String> getImgPathList() {
		return imgPathList;
	}

	public void setImgPathList(List<String> imgPathList) {
		this.imgPathList = imgPathList;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundDescribe() {
        return refundDescribe;
    }

    public void setRefundDescribe(String refundDescribe) {
        this.refundDescribe = refundDescribe;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOldOrderId() {
        return oldOrderId;
    }

    public void setOldOrderId(Long oldOrderId) {
        this.oldOrderId = oldOrderId;
    }
}