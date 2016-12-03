package com.rc.openapi.model;

import java.io.Serializable;

/**
 * 位置管理数据VO
 * @author user3
 *
 */
public class PositionVO implements Serializable {
	private static final long serialVersionUID = 1197622762872332465L;
    /**
     * 名称
     */
	private String name;
	/**
	 * 销售价格
	 */
	private String price;
	/**
	 * 市场价格
	 */
	private String marketPrice;
	/**
	 * 商品图片
	 */
	private String image; 
	
	/**
	 * 商品ID
	 */
	private String pid; 
	
	/**
	 * 折扣
	 */
	private String discount;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	
}
