
package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rc.openapi.service.TGoodsManager;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TGoods;

public class IsStockNotNullService extends BaseURIService  {
	
	

	
	private static final long serialVersionUID = -4813231152211776388L;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");// 商品ID
		String statusCode = "0";
		Map<String, Object> resultmap = new HashMap<String, Object>();
		TGoodsManager tgoodsmanager = (TGoodsManager)getSpringBean("tgoodsmanager");
		
		try {
			TGoods goods = tgoodsmanager.selectByPrimaryKey(new Long(id));
			if(null != goods && null != goods.getId()){
			 	if(null != goods.getStock() && goods.getStock()>0){
			 		statusCode = "1_"+goods.getStock();
			 	}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ConstantUtil.reJSON(statusCode, request, response);
	}
}

