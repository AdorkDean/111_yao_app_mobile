package com.rc.openapi.model.plan;
import java.util.ArrayList;
import java.util.List;


public class BigModel {

	
	private String statusCode = "0";
	private List<Category> category = new ArrayList<Category>();
	private String key = "454354385043858";
	
	
	public BigModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BigModel(List<Plan> tuijian, List<Category> fenlei) {
		super();
		this.category = fenlei;
	}
	public List<Category> getCategory() {
		return category;
	}
	public void setCategory(List<Category> category) {
		this.category = category;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	

}
