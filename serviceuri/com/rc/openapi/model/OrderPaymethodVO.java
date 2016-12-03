package com.rc.openapi.model;

import java.io.Serializable;
/**
 * 购物车支付方式 VO 根据地址变化
 * @author user3
 *
 */
public class OrderPaymethodVO implements Serializable{
	private static final long serialVersionUID = -8013490270541390262L;
	/**
	 * url地址
	 */
	private String imgurl;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 支付方式id
	 */
	private String paymethodId;
	
	/**
	 * 支付插件id
	 */
	private String paypluginId;
	
	/**
	 * 是否线上
	 */
	private String isonline;

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPaymethodId() {
		return paymethodId;
	}

	public void setPaymethodId(String paymethodId) {
		this.paymethodId = paymethodId;
	}

	public String getPaypluginId() {
		return paypluginId;
	}

	public void setPaypluginId(String paypluginId) {
		this.paypluginId = paypluginId;
	}

	public String getIsonline() {
		return isonline;
	}

	public void setIsonline(String isonline) {
		this.isonline = isonline;
	}
	
	
	

}
