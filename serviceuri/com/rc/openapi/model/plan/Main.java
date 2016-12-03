package com.rc.openapi.model.plan;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;


public class Main {

	
	public static void main(String[] args) {
		
		
		// 创建方案
		Plan Plan1 = new Plan(1,"方案1","a.jpg","http://www.111yao.com");
		Plan Plan2 = new Plan(2,"方案2","a.jpg","http://www.111yao.com");
		Plan Plan3 = new Plan(3,"方案3","a.jpg","http://www.111yao.com");
		
		Plan Plan4 = new Plan(4,"方案4","a.jpg","http://www.111yao.com");
		Plan Plan5 = new Plan(5,"方案5","a.jpg","http://www.111yao.com");
		Plan Plan6 = new Plan(6,"方案6","a.jpg","http://www.111yao.com");
		
	
		Plan Plan7 = new Plan(7,"方案7","a.jpg","http://www.111yao.com");
		Plan Plan8 = new Plan(8,"方案8","a.jpg","http://www.111yao.com");
		Plan Plan9 = new Plan(9,"方案9","a.jpg","http://www.111yao.com");
		
		Plan Plan10 = new Plan(10,"方案10","a.jpg","http://www.111yao.com");
		Plan Plan11 = new Plan(11,"方案11","a.jpg","http://www.111yao.com");
		Plan Plan12 = new Plan(12,"方案12","a.jpg","http://www.111yao.com");
		Plan Plan13 = new Plan(13,"方案12","a.jpg","http://www.111yao.com");
		
		
		Category c1 = new Category(100,"分类一");
		Category c2 = new Category(101,"分类二");
		Category c3 = new Category(102,"分类三");
		
		// 给分类 设置方案
		List<Plan> c1list = new ArrayList<Plan>();
		c1list.add(Plan1);
		c1list.add(Plan2);
		c1list.add(Plan3);
		c1.setPlanList(c1list);
		
		
		List<Plan> c2list = new ArrayList<Plan>();
		c2list.add(Plan4);
		c2list.add(Plan5);
		c2list.add(Plan6);
		c2.setPlanList(c2list);
		
		List<Plan> c3list = new ArrayList<Plan>();
		c3list.add(Plan7);
		c3list.add(Plan8);
		c3list.add(Plan9);
		c3.setPlanList(c3list);
		
//		List<Plan> tuijianlist = new ArrayList<Plan>();
//		tuijianlist.add(Plan10);
//		tuijianlist.add(Plan11);
//		tuijianlist.add(Plan12);
//		tuijianlist.add(Plan13);
		
		
		
		List<Category> fenlei = new ArrayList<Category>();
		fenlei.add(c1);
		fenlei.add(c2);
		fenlei.add(c3);
		
		BigModel model = new BigModel();
		model.setCategory(fenlei);
//		model.setKey("key00808080808");
		
		JSONObject json = JSONObject.fromObject(model);
		
		System.out.println(json.toString());
		
		

	}

}
