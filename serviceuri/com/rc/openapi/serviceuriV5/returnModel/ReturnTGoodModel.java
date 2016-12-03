package com.rc.openapi.serviceuriV5.returnModel;

import java.io.Serializable;

/**
 * 订单 商品 model
 * 
 * @author acer
 * 
 */
public class ReturnTGoodModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商品id
	 */
	private String goodsid;
	/**
	 * 商品全称
	 */
	private String goodsName;
	/**
	 * 商品简称
	 */
	private String shortName;

	/**
	 * 规格
	 */
	private String spec;
	/**
	 * 缩略图
	 */
	private String abbreviationPicture;
	/**
	 * 商品单价
	 */
	private String price;
	/**
	 * 购买数量
	 */
	private String quantity;
	/**
	 * 购买商品总价
	 */
	private String sumPrice;
	/**
	 * 退款/退货过程 0 未发起 1 已发起 2 已过期(不允许退货)
	 */
	private String refundProcess;

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getAbbreviationPicture() {
		return abbreviationPicture;
	}

	public void setAbbreviationPicture(String abbreviationPicture) {
		this.abbreviationPicture = abbreviationPicture;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(String sumPrice) {
		this.sumPrice = sumPrice;
	}

	public String getRefundProcess() {
		return refundProcess;
	}

	public void setRefundProcess(String refundProcess) {
		this.refundProcess = refundProcess;
	}

}
