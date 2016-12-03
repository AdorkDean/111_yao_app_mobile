package com.rc.openapi.serviceuriV4.receiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.CLocationCityManager;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.CLocationCity;
import com.rc.openapi.vo.CLocationCityExample;
/**
 * 根据城市获取 区域 二级区域信息
 * @author user3
 *
 */
public class GetAddressByCityService extends BaseURIService {
	private static final long serialVersionUID = 3955056266546895690L;
	private final Logger log = Logger.getLogger(getClass());
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String citycode = request.getParameter("citycode");//城市编号
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String statusCode = "";
		String errorMessage ="";
		List<Map<String,Object>> areaList = new ArrayList<Map<String,Object>>();
		try {
		if(StringUtils.hasText(citycode)){
			CLocationCityManager cLocationCityManager = (CLocationCityManager) getSpringBean("clocationcitymanager");
			CLocationCityExample cLocationCityExample = new CLocationCityExample();
			cLocationCityExample.createCriteria().andCitycodeEqualTo(citycode);
			cLocationCityExample.setOrderByClause(" id asc");
			List<CLocationCity>	clocationCityList = cLocationCityManager.selectByExample(cLocationCityExample);
			if(clocationCityList!=null&&clocationCityList.size()>0){
				cLocationCityExample = new CLocationCityExample();
				cLocationCityExample.createCriteria().andParentidEqualTo(clocationCityList.get(0).getId());
				clocationCityList = cLocationCityManager.selectByExample(cLocationCityExample);
				Map<String,Object> areaMap = null;
				Map<String,String> twoAreaMap = null;
				List<Map<String,String>> twoAreaList = null;
				for(CLocationCity locationCity:clocationCityList){
					areaMap = new HashMap<String,Object>();
					areaMap.put("id", String.valueOf(locationCity.getId()));
					areaMap.put("name", locationCity.getName());
					twoAreaList = new ArrayList<Map<String,String>>();
					if("010".equals(citycode)){//判断是否是北京
						cLocationCityExample = new CLocationCityExample();
						cLocationCityExample.createCriteria().andParentidEqualTo(locationCity.getId());
						List<CLocationCity> towCityList = cLocationCityManager.selectByExample(cLocationCityExample);
						if(towCityList!=null&&towCityList.size()>0){
							for(CLocationCity towCity:towCityList){
								twoAreaMap  = new HashMap<String,String>();
								twoAreaMap.put("id", String.valueOf(towCity.getId()));
								twoAreaMap.put("name", towCity.getName());
								twoAreaList.add(twoAreaMap);
							}
						}
					}
					areaMap.put("list", twoAreaList);
					areaList.add(areaMap);
					
				}
			}
			statusCode="1";
		}else{
			statusCode="2";//城市编号 不能为空
			errorMessage="请选择城市";
		}
		} catch (Exception e) {
			statusCode="0";
			errorMessage="服务器异常,请稍后再试";
			e.printStackTrace();
		}
		resultMap.put("statusCode", statusCode);
		resultMap.put("errorMessage", errorMessage);
		resultMap.put("areaList", areaList);
		JSONObject json4 = JSONObject.fromObject(resultMap);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
}
