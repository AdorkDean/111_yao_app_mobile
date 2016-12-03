package com.rc.openapi.serviceuriV2;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
/**
 * 条形码扫描
 *
 */
public class BarCodeScanService extends BaseURIService {
	private static final long serialVersionUID = 1L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = "0";
		try {
			String code = request.getParameter("barCode"); //条形码
			if(null != code && !("").equals(code)){
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("barCode", code);
				OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
			    List goodsList =  om.selectForListByMap(map1, "open_sql.barcodescan");
			    if(null !=goodsList && goodsList.size()>0){
			    	    Map  mapobject = (Map) goodsList.get(0);
			        	statusCode = "1";
			        	map.put("product_Id", mapobject.get("id").toString());
			        	map.put("name", mapobject.get("short_name"));
			    }else{
			    	statusCode = "2"; //没有找到该商品
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("statusCode", statusCode);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
}
