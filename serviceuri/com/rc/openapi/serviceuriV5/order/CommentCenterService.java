package com.rc.openapi.serviceuriV5.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.util.StringUtils;

import com.rc.openapi.dubbo.model.TOrderGoodModel;
import com.rc.openapi.serviceApi.OrderServiceDubboApi;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 待评价商品接口
 * CommentCenterService<BR>
 * 创建人:Marlon <BR>
 * 时间：2016-8-30-上午11:43:53 <BR>
 * @version 1.0.0
 */
public class CommentCenterService extends BaseURIService {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");//sessionId
		String orderId = request.getParameter("orderId");//订单id
		Map<String, Object> resultMap = new HashMap<String, Object>();//将所有结果集存放到改map中用于统一返回json字符串
		String statusCode ="";
		String message="服务器繁忙请稍后重试！";
		if (StringUtils.hasText(sessionId)) {
			OrderServiceDubboApi orderservicedubboapi = (OrderServiceDubboApi) getSpringBean("orderservicedubboapi");
			if (StringUtils.hasText(orderId)) {
				resultMap.put("orderId", orderId);
				try {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("orderId", Long.parseLong(orderId.trim()));
					List<TOrderGoodModel> orderGoodList= orderservicedubboapi.getTOrderGoodListByOrderId(Long.valueOf(orderId.trim()),0);//
					List<Map<String,String>> lists = new ArrayList<Map<String,String>>();
					if(orderGoodList!=null&&orderGoodList.size()>0){
						Map<String,String>  itemsMap= null;
						for (TOrderGoodModel goodModel:orderGoodList) {
							itemsMap= new HashMap<String, String>();
							itemsMap.put("goodName",goodModel.getGoodsName());//商品名称
							itemsMap.put("goodId",String.valueOf(goodModel.getGoodsid()));//商品id
							itemsMap.put("img",goodModel.getAbbreviationPicture());//商品图片
							lists.add(itemsMap);
						}
						resultMap.put("count", orderGoodList.size());
					}else{
						resultMap.put("count", 0);
					}
					resultMap.put("lists", lists);
					statusCode="1";
					message="请求成功!";
				} catch (Exception e) {
					statusCode="0";
					message="服务器繁忙请稍后重试！";
					e.printStackTrace();
				}
			}else {
				statusCode="0";
				message="该订单信息不存在！";
			}
		}else {
			statusCode="0";
			message="未登录，不能执行该操作！";
		}
		resultMap.put("statusCode", statusCode);
		resultMap.put("message", message);
		ConstantUtil.reJSON(JSONObject.fromObject(resultMap).toString(), request, response);
	}
	
}
