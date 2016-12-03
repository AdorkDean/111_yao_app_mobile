package com.rc.openapi.model.plan;
import java.util.ArrayList;
import java.util.List;


public class Category implements Comparable<Category> {
	
	private long category_id;
	private String category_name;
	private int weight;
	private List<Plan> planList = new ArrayList<Plan>();
	
	
	

	@Override
	public int compareTo(Category o) {
		if(this.getWeight()>o.getWeight()){
			return -1;
		}else if(this.getWeight()<o.getWeight()){
			return 1;
		}else{
			if(this.getCategory_id()>o.getCategory_id()){
				return 1;
			}else if(this.getCategory_id()<o.getCategory_id()){
				return -1;
			}else{
				return 0;
			}
		}
	}
	
	
	
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Category(long category_id, String category_name) {
		this.category_id = category_id;
		this.category_name = category_name;
	}




	public Category(long category_id, String category_name, List<Plan> planList) {
		super();
		this.category_id = category_id;
		this.category_name = category_name;
		this.planList = planList;
	}




	public long getCategory_id() {
		return category_id;
	}




	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}




	public String getCategory_name() {
		return category_name;
	}




	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}




	public List<Plan> getPlanList() {
		return planList;
	}




	public void setPlanList(List<Plan> planList) {
		this.planList = planList;
	}




	public int getWeight() {
		return weight;
	}




	public void setWeight(int weight) {
		this.weight = weight;
	}




	
	

}
