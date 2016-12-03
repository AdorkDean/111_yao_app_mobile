package com.rc.openapi.serviceuriV4.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.rc.dst.client.util.ClientSubmit;
import com.rc.openapi.model.SeachIndexModel;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;

/**
 * 搜索关键词联想
 * 
 * @author WWF
 * @createTime 2016-8-2 下午3:10:28
 */
public class AssociationKeyService extends BaseURIService {

	private static final long serialVersionUID = 5736761068062807795L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();

		String keyword = request.getParameter("keyword");
		// ptype = 1 全部 2 折扣 3价格
		if (null != keyword && !("").equals(keyword)) {
			String pageNo = "1";
			String pageSize = "10";

			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("parnterid", "1001");
			map1.put("method", "search");
			map1.put("from", "3");
			map1.put("sort", "-1");
			map1.put("order", "2");
			map1.put("pricea", "0");
			map1.put("priceb", "90000");
			map1.put("typeid", "-1");
			map1.put("brandid", "-1");
			map1.put("keyword", keyword);
			map1.put("filtertype", "1");
			map1.put("page", pageNo);
			map1.put("size", pageSize);
			String YAO_GATEWAY_URL = InfoUtil.getInstance().getInfo("config", "yao_geteway_url");
			String returnStr = "";
			try {
				returnStr = ClientSubmit.buildRequestForSearch(map1, YAO_GATEWAY_URL);
				JSONObject jsonObject = JSONObject.fromObject(returnStr);
				JSONArray jsonArray = jsonObject.getJSONArray("models");
				List<SeachIndexModel> ll = (List<SeachIndexModel>) jsonArray.toCollection(jsonArray,
						SeachIndexModel.class);
				List<AssociationKey> pList = new ArrayList<AssociationKey>();
				AssociationKey associationKey = null;

				for (int i = 0; i < ll.size(); i++) {
					SeachIndexModel im = ll.get(i);
					associationKey = new AssociationKey();
					associationKey.setName(im.getShortname());
					pList.add(associationKey);
				}
				map.put("list", pList);
				map.put("statusCode", "1");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("statusCode", "0");
			}

		} else {
			map.put("statusCode", "2");
		}
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

	public class AssociationKey {
		private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
