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

import com.ibatis.common.jdbc.exception.NestedSQLException;
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
 * 添加收货地址
 * 
 * @author user00
 * @createTime 2016-1-7 上午11:55:49
 */
public class AddReceiverAddressURIService extends BaseURIService {

	private static final long serialVersionUID = 2001352847369121834L;
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
			// 添加地址
			// String memberId = request.getParameter("member_id");// 会员ID
			String sessionid = request.getParameter("sessionid");// 会员ID
			String mobile = request.getParameter("mobile");// 手机号
			String zipCode = request.getParameter("zipCode");// 邮编
			String receiver = request.getParameter("receiver");// 收货人
			String isDefault = request.getParameter("isDefault");// 是否默认1:是;0:否
			// String areaId = request.getParameter("areaId");// 地区ID
			String citycode = request.getParameter("citycode");// 地区编码
			String longitude = request.getParameter("longitude");// 经度
			String latitude = request.getParameter("latitude");// 纬度
			String locationAddress = request.getParameter("locationAddress");// 定位地址
			String address = request.getParameter("address");// 详细地址
			String storeId = "";

			TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			Long memberId = tmembermanager.getMemberId(sessionid);

			Map<String, String> publicMap = new HashMap<String, String>();

			if (sessionid == null || "".equals(sessionid)) {
				message = "sessionid不能为空";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}

			if (memberId == null || memberId < 0) {
				message = "用户信息有误";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
			publicMap.put("member_id", memberId.toString());
			if (mobile == null || "".equals(mobile) && mobile.length()>15) {
				message = "手机号不能为空!";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
			publicMap.put("mobile", mobile);
			if (receiver == null || "".equals(receiver)) {
				message = "收货人不能为空";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
			if (receiver.length()>20) {
				message = "收货人信息最大20个字符";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
			publicMap.put("receiver", receiver);
			if (isDefault == null || "".equals(isDefault) || !StringIsNumericUtil.isNumeric(isDefault)) {
				isDefault = "0";
			}
			publicMap.put("isDefault", isDefault);
			if (citycode == null || "".equals(citycode) || !StringIsNumericUtil.isNumeric(citycode)) {
				message = "地区编码不能为空";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}

			if (longitude == null || "".equals(longitude) || latitude == null || "".equals(latitude)) {
				message = "经纬度不能为空";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
			publicMap.put("longitude", longitude);
			publicMap.put("latitude", latitude);
			if (locationAddress == null || "".equals(locationAddress)) {
				message = "定位地址不能为空";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
			publicMap.put("locationAddress", locationAddress);
			if (address == null || "".equals(address)) {
				message = "详细地址不能为空";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
			if (zipCode != null && !"".equals(zipCode) && !StringIsNumericUtil.isZipCode(zipCode)) {
				message = "非法邮编";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
			publicMap.put("address", address);
			publicMap.put("zipCode", zipCode);
			publicMap.put("storeId", storeId);

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
			String homePageConfigId = sys.getKeys("public_service_url");

			String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId
					+ "addReceiverAddress");
			if (resultJsonstr == null) {
				message = "网络异常,请稍后重试";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
			JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
			if (jsonObject != null && jsonObject.get("statusCode") != null && jsonObject.get("statusCode").equals("1")) {
				statusCode = "1";// 成功
				message = jsonObject.get("message") == null ? "" : jsonObject.get("message").toString();
			} else {
				statusCode = "0";// 失败
				message = "服务器忙,请稍后重试";
//				message = jsonObject.get("message") == null ? "" : jsonObject.get("message").toString();
			}
		} catch (NestedSQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			message = "网络异常,请稍后";
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			statusCode = "0";
			message = "网络异常,请稍后";
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			message = "网络异常,请稍后";
		}
		result.put("statusCode", statusCode);
		result.put("message", message);
		JSONObject json = JSONObject.fromObject(result);
		ConstantUtil.reJSON(json.toString(), request, response);
		return;
	}

}
