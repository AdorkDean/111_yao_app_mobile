package com.rc.openapi.serviceuriV2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.openapi.service.TPrescriptionImageManager;
import com.rc.openapi.service.TPrescriptionManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TPrescription;

/**
 * 删除病例
 * 
 * @author user00
 * @createTime 2015-7-29 下午5:38:23
 */
public class DelPrescriptionURIService extends BaseURIService {

	private static final long serialVersionUID = 3810393917175631043L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = "0";
		String message = "";

		String sessionid = request.getParameter("sessionid");
		String id = request.getParameter("id");// 病情描述
		TPrescription tPrescription = null;
		if (sessionid == null || "".equals(sessionid.trim())) {
			message = "请先登录";
		} else {
			if (id == null || "".equals(id)) {
				message = "请选择要查询的病例";
			} else {
				try {
					TMemberManagerImpl man = (TMemberManagerImpl) getSpringBean("tmembermanager");
					TPrescriptionManager tPrescriptionManager = (TPrescriptionManager) getSpringBean("tprescriptionmanager");
					TPrescriptionImageManager tPrescriptionImageManager = (TPrescriptionImageManager) getSpringBean("tprescriptionimagemanager");
					Long userId = man.getMemberId(sessionid);
					if (userId > 0L) {
						tPrescription = tPrescriptionManager.selectByPrimaryKey(Long.valueOf(id));
						if (tPrescription == null) {
							message = "未查询到该病例信息,请确认";
						} else if (tPrescription.getUserId() != null
								&& tPrescription.getUserId().longValue() != userId.longValue()) {
							message = "未查询到您有该病例信息,请确认";
						} else {
							tPrescriptionImageManager.deleteByPrescriptionId(tPrescription.getId());
							tPrescriptionManager.deleteById(tPrescription.getId());
							message = "删除成功";
							statusCode = "1";
						}
					} else {
						message = "请您正常登录";
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		map.put("statusCode", statusCode);
		map.put("message", message);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

}
