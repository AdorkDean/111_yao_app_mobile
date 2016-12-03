package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.CLocationCityManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.StringIsNumericUtil;
import com.rc.openapi.vo.CLocationCity;
import com.rc.openapi.vo.CLocationCityExample;

/**
 * 修改收货地址
 * 
 * @author user00
 * @createTime 2016-1-7 上午11:57:35
 */
public class UpdateReceiverAddressURIService extends BaseURIService {

	private static final long serialVersionUID = 2867642544867447982L;
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
		try {
			String id = request.getParameter("id");// 会员ID
			String sessionid = request.getParameter("sessionid");// 会员ID
			String mobile = request.getParameter("mobile");// 手机号
			String zipCode = request.getParameter("zipCode");// 邮编
			String receiver = request.getParameter("receiver");// 收货人
			String isDefault = request.getParameter("isDefault");// 是否默认1:是;0:否
			String citycode = request.getParameter("citycode");// 地区编码
			String longitude = request.getParameter("longitude");// 经度
			String latitude = request.getParameter("latitude");// 纬度
			String locationAddress = request.getParameter("locationAddress");// 定位地址
			String address = request.getParameter("address");// 详细地址

			if (id == null || "".equals(id) || !StringIsNumericUtil.isNumeric(id)) {
				message = "非法ID!";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
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
			Map<String, String> publicMap = new HashMap<String, String>();
			publicMap.put("id", id);
			publicMap.put("member_id", memberId.toString());

			if (mobile != null && !"".equals(mobile)) {
				publicMap.put("mobile", mobile);
				if(mobile.length()>15){
					message = "手机号长度最大15位字符";
					result.put("statusCode", statusCode);
					result.put("message", message);
					JSONObject json = JSONObject.fromObject(result);
					ConstantUtil.reJSON(json.toString(), request, response);
					return;
				}
			}
			if (receiver != null && !"".equals(receiver)) {
				publicMap.put("receiver", receiver);
				if (receiver.length()>20) {
					message = "收货人信息最大20个字符";
					result.put("statusCode", statusCode);
					result.put("message", message);
					JSONObject json = JSONObject.fromObject(result);
					ConstantUtil.reJSON(json.toString(), request, response);
					return;
				}
			}
			if (isDefault != null && !"".equals(isDefault) && StringIsNumericUtil.isNumeric(isDefault)
					&& Integer.valueOf(isDefault) == 1) {
				publicMap.put("isDefault", isDefault);
			} else {
				publicMap.put("isDefault", "0");
			}
			// if (citycode != null && !"".equals(citycode) &&
			// StringIsNumericUtil.isNumeric(citycode)) {
			// publicMap.put("areaId", areaId);
			// }
			if (longitude != null && !"".equals(longitude)) {
				publicMap.put("longitude", longitude);
			}
			if (latitude != null && !"".equals(latitude)) {
				publicMap.put("latitude", latitude);
			}

			if (locationAddress != null && !"".equals(locationAddress)) {
				publicMap.put("locationAddress", locationAddress);
			}
			if (address != null && !"".equals(address)) {
				publicMap.put("address", address);
			}
			if (zipCode != null && !"".equals(zipCode)) {
				if (!StringIsNumericUtil.isZipCode(zipCode)) {
					message = "非法邮编";
					result.put("statusCode", statusCode);
					result.put("message", message);
					JSONObject json = JSONObject.fromObject(result);
					ConstantUtil.reJSON(json.toString(), request, response);
					return;
				}
				publicMap.put("zipCode", zipCode);
			}

			if (citycode != null && !"".equals(citycode)) {
				CLocationCityManager cLocationCityManager = (CLocationCityManager) getSpringBean("clocationcitymanager");

				CLocationCityExample cLocationCityExample = new CLocationCityExample();
				cLocationCityExample.createCriteria().andCitycodeEqualTo(citycode);
				cLocationCityExample.setOrderByClause(" parentid desc");
				List<CLocationCity> cityList = cLocationCityManager.selectByExample(cLocationCityExample);
				if (cityList == null || cityList.size() <= 0) {
					message = "城市编码有误,请确认";
					result.put("statusCode", statusCode);
					result.put("message", message);
					JSONObject json = JSONObject.fromObject(result);
					ConstantUtil.reJSON(json.toString(), request, response);
					return;
				}
				Integer areaId = cityList.get(0).getId();
				publicMap.put("areaId", areaId.toString());
			}

			String homePageConfigId = sys.getKeys("public_service_url");
			String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId
					+ "updateReceiverAddress");
			JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
			if (jsonObject != null && jsonObject.get("statusCode") != null && jsonObject.get("statusCode").equals("1")) {
				statusCode = "1";// 成功
				message = jsonObject.get("message") == null ? "" : jsonObject.get("message").toString();
			} else {
				statusCode = "0";// 失败
				message = jsonObject.get("message") == null ? "" : jsonObject.get("message").toString();
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			message = e.getMessage();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			message = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			message = e.getMessage();
		}
		result.put("statusCode", statusCode);
		result.put("message", message);
		JSONObject json = JSONObject.fromObject(result);
		ConstantUtil.reJSON(json.toString(), request, response);
		return;
	}

}
