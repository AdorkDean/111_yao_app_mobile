package com.rc.openapi.model;
/**
 * 商品详情类
 *
 */
public class ProductInfo {
	private String pid;//商品ID
	private String name;//名称
	private String price;//价格
	private String marketPrice;//原价
	private String guige;//规格
	private String isPrescription;//是否处方药
	private String zk;//折扣
	private String purl;
	
	public String getPurl() {
		return purl;
	}
	public void setPurl(String purl) {
		this.purl = purl;
	}
	public String getZk() {
		return zk;
	}
	public void setZk(String zk) {
		this.zk = zk;
	}
	public String getIsPrescription() {
		return isPrescription;
	}
	public void setIsPrescription(String isPrescription) {
		this.isPrescription = isPrescription;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getGuige() {
		return guige;
	}
	public void setGuige(String guige) {
		this.guige = guige;
	}
}
