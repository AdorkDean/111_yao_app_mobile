package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 删除地址
 * 
 * @author user00
 * @createTime 2016-1-7 上午11:58:35
 */
public class DelReceiverAddressURIService extends BaseURIService {

	private static final long serialVersionUID = -984199835397218575L;
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
			String sessionid = request.getParameter("sessionid");
			String id = request.getParameter("id");
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

			if (id == null || "".equals(id)) {
				message = "非法收货地址ID";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
				return;
			}
			Map<String, String> publicMap = new HashMap<String, String>();
			publicMap.put("id", id);
			publicMap.put("member_id", memberId.toString());

			String homePageConfigId = sys.getKeys("public_service_url");
			String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId
					+ "delReceiverAddress");
			JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
			if (jsonObject != null) {
				statusCode = jsonObject.get("statusCode") == null ? "0" : jsonObject.get("statusCode").toString();// 成功
				message = jsonObject.get("message") == null ? "" : jsonObject.get("message").toString();
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
		JSONObject json = JSONObject.fromObject(result);
		ConstantUtil.reJSON(json.toString(), request, response);
		return;
	}
}
