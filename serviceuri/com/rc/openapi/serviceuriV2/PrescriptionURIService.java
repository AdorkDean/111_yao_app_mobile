package com.rc.openapi.serviceuriV2;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.openapi.model.Prescription;
import com.rc.openapi.service.TPrescriptionManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TPrescription;

/**
 * 病例列表接口
 * 
 * @author user00
 * @createTime 2015-7-23 上午11:23:53
 */
public class PrescriptionURIService extends BaseURIService {

	private static final long serialVersionUID = 3810393917175631043L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionid = request.getParameter("sessionid".toLowerCase());
		TMemberManagerImpl man = (TMemberManagerImpl) getSpringBean("tmembermanager");
		String statusCode = "0";
		String message = "";
		Map<String, Object> result = new HashMap<String, Object>();
		if (null == sessionid || "".equals(sessionid.trim())) {
			message = "请先登录";
			result.put("statusCode", statusCode);
			result.put("message", message);
			JSONObject json = JSONObject.fromObject(result);
			ConstantUtil.reJSON(json.toString(), request, response);
		} else {
			Long userId = 0L;
			try {
				userId = man.getMemberId(sessionid);
			} catch (SQLException e) {
				e.printStackTrace();
				message = e.getMessage();
			}
			if (userId > 0) {
				String pageNo = request.getParameter("pageNo");// 当前页数
				String pageSize = request.getParameter("pageSize");// 显示条数
				int _pageNo = 0;
				int _pageSize = 10;
				if (pageNo != null && !"".equals(pageNo)) {
					_pageNo = Integer.valueOf(pageNo);
				}
				if (pageSize != null && !"".equals(pageSize)) {
					_pageSize = Integer.valueOf(pageSize);
				}
				TPrescriptionManager tPrescriptionManager = (TPrescriptionManager) getSpringBean("tprescriptionmanager");
				List<TPrescription> list = tPrescriptionManager.queryList(userId, _pageNo, _pageSize);
				List<Prescription> returnList = new ArrayList<Prescription>();
				Prescription _Prescription = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (TPrescription tPrescription : list) {
					_Prescription = new Prescription();
					_Prescription.setId(tPrescription.getId().toString());
					_Prescription.setDiseaseDescrip(tPrescription.getDiseaseDescrip());
					_Prescription.setPhone(tPrescription.getPhone());
					_Prescription.setImageUrl(tPrescription.getImageUrl());
					_Prescription.setCreateDate(sdf.format(tPrescription.getCreateDate()));
					_Prescription.setStatus(tPrescription.getStatus() == null ? "0" : tPrescription.getStatus()
							.toString());
					_Prescription.setIfhelp(tPrescription.getIfhelp() == null ? "0" : tPrescription.getStatus()
							.toString());
					returnList.add(_Prescription);
				}
				statusCode = "1";
				result.put("statusCode", statusCode);
				result.put("prescriptionList", returnList);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
			} else {
				message = "请正常登录";
				result.put("statusCode", statusCode);
				result.put("message", message);
				JSONObject json = JSONObject.fromObject(result);
				ConstantUtil.reJSON(json.toString(), request, response);
			}
		}
	}
}
