package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 获取用户信息的收货地址
 * 
 * @author user00
 * @createTime 2016-1-7 上午11:49:24
 */
public class GetReceiverAddressURIService extends BaseURIService {

	private static final long serialVersionUID = -7309725851901418605L;
	private final Logger log = Logger.getLogger(getClass());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// String memberId = request.getParameter("member_id");
		String statusCode = "0";
		String message = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			String sessionid = request.getParameter("sessionid");
			if (sessionid == null || "".equals(sessionid)) {
				message = "sessionid不能为空";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}

			TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			Long memberId = tmembermanager.getMemberId(sessionid);
			if (memberId == null || memberId < 0) {
				message = "用户信息有误";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
			// List<TMemberReceiverLatLon> list = null;
			List<Map<String, Object>> _resultList = null;
			Map<String, String> publicMap = new HashMap<String, String>();
			publicMap.put("member_id", memberId.toString());
			String homePageConfigId = sys.getKeys("public_service_url");
			String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId
					+ "getReceiverAddress");
			JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
			if (jsonObject != null) {
				statusCode = jsonObject.get("statusCode") == null ? "0" : jsonObject.get("statusCode").toString();// 成功
				message = jsonObject.get("message") == null ? "" : jsonObject.get("message").toString();
			}
			JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("list"));
			_resultList = (List<Map<String, Object>>) JSONArray.toCollection(jsonArray, Map.class);
			for (Map<String, Object> map : _resultList) {
				if (map != null) {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					if (map.get("id") != null) {
						resultMap.put("id", map.get("id").toString());
					}
					if (map.get("mobile") != null) {
						resultMap.put("mobile", map.get("mobile").toString());
					}
					if (map.get("address") != null && map.get("area") != null) {
						
						if(map.get("latitude") instanceof JSONNull && map.get("longitude") instanceof JSONNull){
							resultMap.put("intact_address", map.get("area").toString()+"  "+ map.get("address").toString());
							resultMap.put("latitude","");
							resultMap.put("longitude","");
						}else{
							if(map.get("location_address") instanceof JSONNull){
								resultMap.put("intact_address", map.get("area").toString()+map.get("address").toString());
								resultMap.put("location_address","");
							}else{
								resultMap.put("intact_address", map.get("area").toString()+"  " + map.get("address").toString());
								String location_address =(String)map.get("location_address");
								String address =(String)map.get("address");
								String area = (String)map.get("area");
								if(StringUtils.hasText(location_address)){
									if(!address.startsWith(location_address)&&area.indexOf(location_address)==-1){
										address= location_address+address;
										resultMap.put("intact_address", map.get("area").toString()+"  " + address);
									}
								}
							}
							
						}
					}
					if (map.get("is_default") != null) {
						resultMap.put("is_default", map.get("is_default").toString());
					}
					if (map.get("zip_code") != null) {
						resultMap.put("zip_code", map.get("zip_code").toString());
					}
					if (map.get("receiver") != null) {
						resultMap.put("receiver", map.get("receiver").toString());
					}
					resultList.add(resultMap);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			statusCode = "0";
			message = e.getMessage();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			statusCode = "0";
			message = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			statusCode = "0";
			message = e.getMessage();
		}
		result.put("statusCode", statusCode);
		result.put("message", message);
		result.put("list", resultList);
		JSONObject json = JSONObject.fromObject(result);
		ConstantUtil.reJSON(json.toString(), request, response);
		return;
	}

}
