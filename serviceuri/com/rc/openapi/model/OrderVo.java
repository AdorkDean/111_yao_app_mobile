package com.rc.openapi.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderVo {
	
    private Long id;

    private String sn; //订单编号

    private String createDate ; //订单时间
    
    private String OrderStatus; //订单状态
    
    private String paymentStatus = "1"; //支付状态
    
    private String paymentMethod = "1"; //配送方式
    
    private String os ;//前台显示的订单状态
    
    private String status;   //状态
    
    List list ; //子订单
    
    private String amount; //订单总金额

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");  
        this.createDate =formatter.format(createDate);  
	}

	public String getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		if(orderStatus.equals("3")){
			setOs("订单已完成");
			setStatus("1");
		}else if(orderStatus.equals("4")){
			setOs("订单已取消");
			setStatus("0");
		}else if(orderStatus.equals("2")|| orderStatus.equals("1")){
			setOs("正在配送中");
			setStatus("3");
		}else if(orderStatus.equals("0")){
			setOs("订单待支付");
			setStatus("2");
		}
		OrderStatus = orderStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}