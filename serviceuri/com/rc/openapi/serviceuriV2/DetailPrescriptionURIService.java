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
import com.rc.openapi.service.TPrescriptionImageManager;
import com.rc.openapi.service.TPrescriptionManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TPrescription;
import com.rc.openapi.vo.TPrescriptionImage;

/**
 * 病例详情接口
 * 
 * @author user00
 * @createTime 2015-7-23 上午11:23:53
 */
public class DetailPrescriptionURIService extends BaseURIService {

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

		// 返回实体
		Prescription prescription = null;

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
						List<TPrescriptionImage> imgsList = tPrescriptionImageManager.selectImagesByPrescriptionId(Long
								.valueOf(id));
						tPrescription = tPrescriptionManager.selectByPrimaryKey(Long.valueOf(id));
						if (tPrescription == null) {
							message = "未查询到该病例信息,请确认";
						} else if (tPrescription.getUserId() != null
								&& tPrescription.getUserId().longValue() != userId.longValue()) {
							message = "未查询到您有该病例信息,请确认";
						} else {
							List<String> urlList = new ArrayList<String>();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							for (TPrescriptionImage tPrescriptionImage : imgsList) {
								if (tPrescriptionImage != null && tPrescriptionImage.getPath() != null) {
									urlList.add(tPrescriptionImage.getPath());
								}
							}
							prescription = new Prescription();
							prescription.setId(tPrescription.getId().toString());
							prescription.setDiseaseDescrip(tPrescription.getDiseaseDescrip());
							prescription.setPhone(tPrescription.getPhone());
							prescription.setCreateDate(sdf.format(tPrescription.getCreateDate()));
							prescription.setStatus(tPrescription.getStatus() == null ? "0" : tPrescription.getStatus()
									.toString());
							prescription.setImgUrlList(urlList);
							prescription.setIfhelp(tPrescription.getIfhelp() == null ? "0" : tPrescription.getIfhelp()
									.toString());
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
		if (prescription != null) {
			map.put("dstPrescription", prescription);
		}
		map.put("statusCode", statusCode);
		map.put("message", message);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

}
