
package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rc.openapi.model.plan.BigModel;
import com.rc.openapi.model.plan.Category;
import com.rc.openapi.model.plan.Plan;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.THealthyPlanRecommendManager;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.THealthyPlanRecommendManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;

public class HealthCatetory extends BaseURIService  {
	
	private static String appurl = "http://testrouter.111yao.com/jklx/";	// app静态页面的链接路径
	private static String wapurl = "http://testwap.111yao.com/jklx/";			// wap静态页面的链接路径 
	private static String base_url = "http://testpc.111yao.com/";  		// 图片的域名路径 
	
	public static void main(String[] args) throws SQLException {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
		OpenSqlManage opensqlmanage = (OpenSqlManageImpl) context.getBean("opensqlmanage");
		THealthyPlanRecommendManager thealthyplanrecommendmanager = (THealthyPlanRecommendManagerImpl) context.getBean("thealthyplanrecommendmanager");
		
		BigModel model = new HealthCatetory().createBigModel(opensqlmanage);
		
	}

	
	public Plan createPlan(Map m2){
		long classification_id = 0;
		Plan plan = new Plan();
		classification_id = (Long)m2.get("classification_id");
		plan = new Plan();
		plan.setId((Long)m2.get("id"));
		plan.setName((String)m2.get("name"));
		plan.setImg(base_url+(String)m2.get("plan_img_url"));
	    plan.setShare_img(base_url+(String)m2.get("wx_share_url"));
	    plan.setShare_discribe((String)m2.get("wx_share_content"));
	    plan.setWap_url(wapurl+((Long)m2.get("id"))+"-w.html");
	    plan.setUrl(appurl+((Long)m2.get("id"))+"-a.html");
	    plan.setShare_url(wapurl+((Long)m2.get("id"))+"-w.html");
	    plan.setWeight((Integer)m2.get("cateweight"));
	    
		return plan;
	}
	
	private BigModel createBigModel(OpenSqlManage opensqlmanage){
		
		
		BigModel model = new BigModel();
		List<HashMap> dataList = opensqlmanage.selectForListByMap(null, "t_healthy_plan.plan_category");
		
		Plan plan = null;
		Map<Long,List<Plan>> map = new HashMap<Long,List<Plan>>();  // key=分类ID value=Plan集合
		Map<Long,String> idAndNameMap = new HashMap<Long,String>(); // key=分类ID value=分类名称
		Map<Long,Integer> idAndWeightMap = new HashMap<Long,Integer>();	// key=分类ID value=分类权重
		Map<Long,Long> idAndFlagMap = new HashMap<Long,Long>(); // key=分类ID value=分类标识，是否是推荐分类 
		long classification_id = 0;
		List<Plan> planlist = null;
		
		int n = 1;
		long flag = 2;
		for(Map m2: dataList)
		{
			plan = createPlan(m2);
			classification_id = (Long)m2.get("classification_id");
			flag = (Long)m2.get("flag");
			if(flag==1){	// 如果是推荐方案
				if(n<4){
					plan.setSort((short)n);
				}else{
					plan.setSort((short)0);
				}
				n ++;
			}else{
				plan.setSort((short)0);
			}
			
			if( map.containsKey(classification_id)){	//  如果有
				planlist = map.get(classification_id);
				planlist.add(plan);
				
			}else{	// 如果还没有
				planlist = new ArrayList<Plan>();
				planlist.add(plan);
				map.put(classification_id, planlist);
			}
			
			if(!idAndNameMap.containsKey(classification_id))
			idAndNameMap.put(classification_id, (String)m2.get("classification_name"));
			
			if(!idAndWeightMap.containsKey(classification_id))
			idAndWeightMap.put(classification_id, (Integer)m2.get("cateweight"));
			
			
			if(!idAndFlagMap.containsKey(classification_id))
			idAndFlagMap.put(classification_id,(Long)m2.get("flag"));
			
			
		}
		
		List<Plan> childList = null;
		Category category = null;
		List<Category> cateList = new ArrayList<Category>();
		
		Set<Long> keys = map.keySet();
		for(Long key : keys)
		{
			childList = (List<Plan>)map.get(key);
			category = new Category();
			category.setCategory_id(key);
			category.setCategory_name(idAndNameMap.get(key));
			if(idAndFlagMap.get(key)==1){
				category.setWeight(Integer.MAX_VALUE);  // 推荐方案，将权重设置为最大
			}else{
				category.setWeight(idAndWeightMap.get(key));
			}
			category.setPlanList(childList);
			cateList.add(category);
		}
		
		Collections.sort(cateList);
		model.setCategory(cateList);
		
		return model;
	}

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		String statusCode = "0";
//		String key = request.getParameter("key");
//		String jsonstr = (String) MemCached.getmcc().get("key");
		BigModel model = new BigModel();
		
		PrintWriter out = null;
		String jsoncallback = request.getParameter("jsoncallback");
		String type = request.getParameter("type"); //  app wap
		try{
			
			out = response.getWriter();
			OpenSqlManage opensqlmanage = (OpenSqlManage) getSpringBean("opensqlmanage");
			model = createBigModel(opensqlmanage);
			statusCode = "1";
		}catch(Exception e){
			e.printStackTrace();
		}
		model.setStatusCode(statusCode);
		JSONObject json = JSONObject.fromObject(model);
		if("wap".equals(type)){
			out.print(jsoncallback+"("+json.toString()+");");
		}else{
			out.print(json.toString());
		}
		
		out.close();
	}
	
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		doGet(request,response);
	}
	
	/*
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		response.setContentType("text/html;charset=utf-8");
		
		String statusCode = "0";
		
//		String key = request.getParameter("key");
		
//		String jsonstr = (String) MemCached.getmcc().get("key");
		BigModel model = new BigModel();
		
		PrintWriter out = null;
		String jsoncallback = request.getParameter("jsoncallback");
		String type = request.getParameter("type"); //  app wap
		try{
			
			out = response.getWriter();
			OpenSqlManage opensqlmanage = (OpenSqlManage) getSpringBean("opensqlmanage");
			List<HashMap> list = opensqlmanage.selectForListByMap(null, "t_healthy_plan.plan_category");
			
			Category category = null;
			List<Category> categoryList = new ArrayList<Category>();
			String s = null;
			String[] array1 = null;
			String[] array2 = null;
			Plan plan = null;
			int n = 1;
			for(Map m : list)
			{
				category = new Category((Long)m.get("category_id"),(String)m.get("category_name"));
				s = (String)m.get("childs");
				if(s!=null)
				{
					array1 = s.split(",");
					System.out.print("===>"+array1);
					for(String str : array1){ 
						if(str!=null && !"".equals(str))
						{
							array2 = str.split("@");
							plan = new Plan(Long.parseLong(array2[0]),array2[1],base_url+array2[2],appurl+array2[0]+"-a.html");
							plan.setWap_url(wapurl+array2[0]+"-w.html");
							plan.setShare_url(wapurl+array2[0]+"-w.html");
							plan.setShare_img("http://testwap.111yao.com/web/images/plan_share.png");
							plan.setShare_discribe("爱健康，爱美丽");
							category.getPlanList().add(plan);
							if(n<4){
								plan.setSort((short)n);
							}else{
								plan.setSort((short)0);
							}
							n++;
							
						}
						
					}
					
				}
				model.getCategory().add(category);
			}
			
			statusCode = "1";
		}catch(Exception e){
			statusCode="1";
			e.printStackTrace();
		}
		model.setStatusCode(statusCode);
		JSONObject json = JSONObject.fromObject(model);
		model.setKey("222222222222");
		if("wap".equals(type)){
			out.print(jsoncallback+"("+json.toString()+");");
		}else{
			out.print(json.toString());
		}
		
		out.close();
	}*/

}

