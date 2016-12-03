package com.rc.openapi.model;

import java.util.List;

public class CartResult {

	
	private String statusCode;
	private String orderAmount;	// 订单实际金额 
	private String discount; // 节省 
	private List<com.rc.openapi.model.CartItem> cartItemList; //购物车中的商品
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public List<com.rc.openapi.model.CartItem> getCartItemList() {
		return cartItemList;
	}
	public void setCartItemList(List<com.rc.openapi.model.CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}
	
	

}
