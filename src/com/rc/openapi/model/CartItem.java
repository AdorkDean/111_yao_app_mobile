package com.rc.openapi.model;

import java.math.BigDecimal;

public class CartItem {

	private long cartItemId;
	private long productid;
	private String product_name;
	private int quantity;
	private BigDecimal sprice;
	private String imgurl;
	private String guige;
	private String checked;
	private int store;
	private String price;
	
	
	
	public BigDecimal getSprice() {
		return sprice;
	}
	public void setSprice(BigDecimal sprice) {
		this.sprice = sprice;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getStore() {
		return store;
	}
	public void setStore(int store) {
		this.store = store;
	}
	public long getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(long cartItemId) {
		this.cartItemId = cartItemId;
	}
	public long getProductid() {
		return productid;
	}
	public void setProductid(long productid) {
		this.productid = productid;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getPrice() {
		return price;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getGuige() {
		return guige;
	}
	public void setGuige(String guige) {
		this.guige = guige;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	
	


}
