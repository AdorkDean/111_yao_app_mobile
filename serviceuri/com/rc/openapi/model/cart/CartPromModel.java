package com.rc.openapi.model.cart;

import java.math.BigDecimal;

public class CartPromModel {

  
   private Long	goods_id;
   private Integer quantity;
   private Long promotion_id;
   private Integer type;
   private BigDecimal app_price;
   private Long categoryid;
   private int stock;
   
public Long getGoods_id() {
	return goods_id;
}
public void setGoods_id(Long goods_id) {
	this.goods_id = goods_id;
}
public Integer getQuantity() {
	return quantity;
}
public void setQuantity(Integer quantity) {
	this.quantity = quantity;
}
public Long getPromotion_id() {
	return promotion_id;
}
public void setPromotion_id(Long promotion_id) {
	this.promotion_id = promotion_id;
}
public Integer getType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public BigDecimal getApp_price() {
	return app_price;
}
public void setApp_price(BigDecimal app_price) {
	this.app_price = app_price;
}
public Long getCategoryid() {
	return categoryid;
}
public void setCategoryid(Long categoryid) {
	this.categoryid = categoryid;
}
public int getStock() {
	return stock;
}
public void setStock(int stock) {
	this.stock = stock;
}
   
   
	

}
