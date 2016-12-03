package com.rc.openapi.model;

import com.rc.openapi.util.NumberUtil;
import com.rc.openapi.vo.TMemberReceiver;



/**
 * Entity - 收货地址
 * 
 * 
 */
public class ReceiverVo{

	private String receiverId;

	/** 收货人 */
	private String consignee;

	/** 地区名称 */
	private String areaName;

	/** 地址 */
	private String address;

	/** 电话 */
	private String phone;
	
	/** 是否默认 */
	private String isDefault;
	
	private String area;

	/** 邮编*/
	private String zipCode;
	
	public ReceiverVo(){}
	
	public ReceiverVo(TMemberReceiver dstr) {
		super();
		this.receiverId = NumberUtil.long2String(dstr.getId());
		this.consignee = dstr.getReceiver();
		this.areaName = dstr.getArea();
		this.address = dstr.getAddress();
		this.phone = dstr.getMobile();
		this.isDefault = String.valueOf(dstr.getIsDefault());
		this.area = NumberUtil.long2String(dstr.getAreaId());
		this.zipCode = dstr.getZipCode();
	}
	

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	
}