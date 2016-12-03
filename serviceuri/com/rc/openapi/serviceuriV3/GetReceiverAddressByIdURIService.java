package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.CLocationCityManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.CLocationCity;

/**
 * 根据ID获取地址信息
 * 
 * @author user00
 * @createTime 2016-1-7 下午12:00:05
 */
public class GetReceiverAddressByIdURIService extends BaseURIService {

	private static final long serialVersionUID = 3779472316177093947L;
	private final Logger log = Logger.getLogger(getClass());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String statusCode = "0";
		String message = "";
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> resultMap = null;
		try {
			String sessionid = request.getParameter("sessionid");
			String id = request.getParameter("id");
			if (sessionid == null || "".equals(sessionid)) {
				message = "sessionid不能为空!";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
			if (id == null || "".equals(id)) {
				message = "非法地址ID";
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
			// TMemberReceiverLatLon tMemberReceiverLatLon = null;
			Map<String, String> publicMap = new HashMap<String, String>();
			publicMap.put("id", id);
			publicMap.put("member_id", memberId.toString());

			CLocationCityManager cLocationCityManager = (CLocationCityManager) getSpringBean("clocationcitymanager");
			String homePageConfigId = sys.getKeys("public_service_url");
			String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId
					+ "getReceiverAddressById");
			JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
			if (jsonObject != null) {
				statusCode = jsonObject.get("statusCode") == null ? "0" : jsonObject.get("statusCode").toString();// 成功
				message = jsonObject.get("message") == null ? "" : jsonObject.get("message").toString();
				resultMap = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonObject.get("address")),
						Map.class);
			}
			if (resultMap != null && resultMap.get("area") != null
					&& resultMap.get("address") != null) {
				
				if(resultMap.get("latitude") instanceof JSONNull && resultMap.get("longitude") instanceof JSONNull){
					resultMap.put("intact_address", resultMap.get("area").toString()+ resultMap.get("address").toString());
					resultMap.put("latitude","");
					resultMap.put("longitude","");
				}else{
					if(resultMap.get("location_address") instanceof JSONNull){
						resultMap.put("intact_address", resultMap.get("area").toString()+resultMap.get("address").toString());
						resultMap.put("location_address","");
					}else{
						resultMap.put("intact_address", resultMap.get("area").toString()+"  " + resultMap.get("address").toString());
						String location_address =(String)resultMap.get("location_address");
						String address =(String)resultMap.get("address");
						String area = (String)resultMap.get("area");
						if(StringUtils.hasText(location_address)){
							if(!address.startsWith(location_address)&&area.indexOf(location_address)==-1){
								address= location_address+address;
								resultMap.put("intact_address", resultMap.get("area").toString()+"  " + address);
							}
						}
					}
				}
				if (resultMap.get("area") != null) {
					String _area = resultMap.get("area").toString();
					resultMap.put("cityName", _area);
					if (_area != null && !"".equals(_area)) {
						String[] str = _area.split("-");
						if (str.length > 1) {
							resultMap.put("area", str[1]);
						}
					}
				}
			}
			if (resultMap != null && resultMap.get("area_id") != null) {
				CLocationCity cLocationCity = cLocationCityManager.selectByPrimaryKey(Integer.valueOf(resultMap.get(
						"area_id").toString()));
				if(cLocationCity!=null){
					if(cLocationCity.getGrade().intValue()==3){
						cLocationCity = cLocationCityManager.selectByPrimaryKey(cLocationCity.getParentid());
					}else if(cLocationCity.getGrade().intValue()==4){
						cLocationCity = cLocationCityManager.selectByPrimaryKey(cLocationCity.getParentid());
						cLocationCity = cLocationCityManager.selectByPrimaryKey(cLocationCity.getParentid());
					}
					resultMap.put("citycode", cLocationCity.getCitycode());
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
		result.put("address", resultMap);
		JSONObject json = JSONObject.fromObject(result);
		ConstantUtil.reJSON(json.toString(), request, response);
		return;
	}

}
