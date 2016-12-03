package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.model.AreaVo;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.util.ConstantUtil;
/**
 * 获取地址
 *
 */
public class GetAddressService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String areaId = request.getParameter("areaId");
		String client_version = request.getParameter("client_version");//版本号
		String client_type = request.getParameter("client_type");// 客户端系统
		if(StringUtils.hasText(client_version)&&StringUtils.hasText(client_type)){
			if("iOS".equals(client_type)){
				if(client_version.equals("v2.2")||client_version.equals("v2.1.0")){
					if("1".equals(areaId)){
						areaId="32";
					}
				}
			}
		}else {
			if(StringUtils.hasText(areaId)){
				if("1".equals(areaId)){
					areaId="32";
				}
			}
		}
		OpenSqlManageImpl opensqlmanage = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
		List<AreaVo> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = null;
		String area = null;
		try{
			if(null==areaId||"".equals(areaId.trim())){
				list = opensqlmanage.selectForListByMap(new HashMap(), "c_location.select_province");
			}else {
				map.put("pId", Long.parseLong(areaId));
				list = opensqlmanage.selectForListByMap(map, "c_location.select_downtown");
				if(null==list||0>=list.size()){
					AreaVo obj = (AreaVo) opensqlmanage.selectForObjectByMap(map, "c_location.select_areaById");
					area = obj.getName();
					AreaVo obj1 = new AreaVo();
					map.clear();
					int pids = obj.getParentid();
					while (-1!=pids) {
						map.put("pId", pids);
						obj1 = (AreaVo) opensqlmanage.selectForObjectByMap(map, "c_location.select_areaById");
						area = obj1.getName()+"-"+area;
						pids = obj1.getParentid();
					}
				}
			}
			statusCode="1";
		}catch (Exception e) {
			statusCode="0";
			e.printStackTrace();
		}
		map.clear();
		map.put("statusCode", statusCode);
		map.put("list", list);
		map.put("area", area);
		map.put("aId", areaId);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
}
