package com.rc.openapi.model;

import java.math.BigDecimal;

public class OrderInfo {

	private long id;
	private String sn;
	private String create_date;
	private String consignee;
	private String phone;
	private int order_status;
	private int payment_status;
	private int method;
	private int payment_method;
	private String statusName;
	private String address;
	private String payment_method_name;
	private String freight;
	private String order_price;
	private String product_price;
	private String area_name;
	private String status;   //状态
	private String couponDiscount;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
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
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public int getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(int payment_status) {
		this.payment_status = payment_status;
	}
	public int getMethod() {
		return method;
	}
	public void setMethod(int method) {
		this.method = method;
	}
	public int getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(int payment_method) {
		this.payment_method = payment_method;
	}

	
	public String getStatusName() {
		
		if(order_status==2 || payment_status==4){
			statusName = "订单已完成";
			setStatus("1");
		}else if(order_status==3){
			statusName = "订单已取消";
			setStatus("0");
		}else if((order_status==0 || order_status==1) && payment_status==2){
			statusName = "正在配送中";
			setStatus("3");
		}else if((order_status==0 || order_status==1) && (payment_status==0 || payment_status==1) && (payment_method==4 ||payment_method==5)){
			statusName = "正在配送中";
			setStatus("3");
		}else if((order_status==0 || order_status==1) && (payment_status==0 || payment_status==1) && payment_method==1){
			statusName = "订单待支付";
			setStatus("2");
		}
		
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getPayment_method_name() {
		return payment_method_name;
	}
	public void setPayment_method_name(String payment_method_name) {
		this.payment_method_name = payment_method_name;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(Double freight) {
		this.freight =  convert(freight);
	}
	public String getOrder_price() {
		return order_price;
	}
	public void setOrder_price(Double order_price) {
		this.order_price = convert(order_price);
	}
	public String getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Double product_price) {
		this.product_price = convert(product_price);
	}
	
	
	public static String convert(Double value){
		  BigDecimal bd =  new   BigDecimal(value);   
		  bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP); 
		  return bd.toString();
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(String couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

}
