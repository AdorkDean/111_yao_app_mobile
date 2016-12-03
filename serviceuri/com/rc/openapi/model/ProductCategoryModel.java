package com.rc.openapi.model;

import java.util.List;

/**
 * 商品分类
 *
 */
public class ProductCategoryModel {
	private String pid;
	private String name;
	private List<ProductCategoryModel> plist;
	private String imgs;
	private String type;
	private String describe;//20160714WWF添加,分类描述
	
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
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
	public List<ProductCategoryModel> getPlist() {
		return plist;
	}
	public void setPlist(List<ProductCategoryModel> plist) {
		this.plist = plist;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
}
