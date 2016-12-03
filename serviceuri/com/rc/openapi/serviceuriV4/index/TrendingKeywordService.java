package com.rc.openapi.serviceuriV4.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;

import com.rc.openapi.serviceApi.OrderReturnServiceDubboApi;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;

/**
 * 热搜
 * 
 * @author WWF
 * @createTime 2016-8-9 下午4:42:35
 */
public class TrendingKeywordService extends BaseURIService {

	private static final long serialVersionUID = 4573547073097697835L;

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String statusCode = "0";
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String redisJdbc = InfoUtil.getInstance().getInfo("config", "redis.jdbc.url");
		Jedis jedis = new Jedis(redisJdbc);
		System.out.println("Server is running: " + jedis.ping());
		System.out.println(jedis.isConnected());
		
		List<String> list = null;
		if(jedis.isConnected()){
			list = jedis.lrange("TrendingKeyword", 0,-1);
			System.out.println("redis读数据");
		}
		if(list==null||list.size()<=0){//redis还没有值
			list = new ArrayList<String>();
			//默认给的
			String strs = InfoUtil.getInstance().getInfo("config", "trending.keyword.list");
			String[] keys = strs.split(",");
			for (int i = 0; i < keys.length; i++) {
				if(keys[i]!=null&&!"".equals(keys[i])){
					jedis.lpush("TrendingKeyword", keys[i]);
					System.out.println("redis写数据");
					list.add(keys[i]);
				}
			}
		}
		Map<String, Object> map = null;
		for (String string : list) {
			map = new HashMap<String, Object>();
			map.put("name", string);
			resultList.add(map);
		}
		statusCode = "1";
		resultMap.put("statusCode", statusCode);
		resultMap.put("list", resultList);
		JSONObject json4 = JSONObject.fromObject(resultMap);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-*.xml");
		OrderReturnServiceDubboApi orderreturnservicedubboapi = (OrderReturnServiceDubboApi) applicationContext.getBean("orderreturnservicedubboapi");
		System.out.println("______"+orderreturnservicedubboapi);
		//
//		orderreturnservicedubboapi.submitReturnInfo(orderId, returnType, returnRemark, imgs, fileList, returnParam)
		
		
//		Jedis jedis = new Jedis("118.26.140.50");
//		Jedis jedis = new Jedis("138.128.213.83");
//		Jedis jedis = new Jedis("45.78.20.168");
//		jedis.auth("000000");
//		System.out.println("Server is running: " + jedis.ping());
//		System.out.println(jedis.isConnected());
//		
//		List<String> ll = jedis.lrange("TrendingKeyword", 0,-1);
//		
//		jedis.del("TrendingKeyword");
//		jedis.lpush("TrendingKeyword", "spring");
//		jedis.lpush("TrendingKeyword", "struts");
//		jedis.lpush("TrendingKeyword", "hibernate");
//		
//		
//		System.out.println(ll);
	}
}
