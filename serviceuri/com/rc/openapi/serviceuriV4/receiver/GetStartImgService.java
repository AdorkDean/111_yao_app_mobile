package com.rc.openapi.serviceuriV4.receiver;

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

import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 获取开机动画
 * @author Administrator
 *
 */
public class GetStartImgService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());
	private OpenSqlManage opensqlmanage;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultJson = null;
		
		try {
			
			TSysParameterManager sysManager = (TSysParameterManager) getSpringBean("tsysparametermanager");
			String startImgId = sysManager.getKeys("startImgId");  //获取开机图片位置管理的ID
			if (!StringUtils.hasText(startImgId)) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("statusCode", "0");
				JSONObject json4 = JSONObject.fromObject(map);
				resultJson = json4.toString();
				return;
			}
			if(resultJson==null || "".equals(resultJson)){
				Map m = new HashMap();
				m.put("channel", startImgId);
				opensqlmanage=(OpenSqlManage)getSpringBean("opensqlmanage");
				List list  = (List)opensqlmanage.selectForListByMap(m, "c_position.getStart");
				Map<String, Object> resultMap = new HashMap<String, Object>();
				if(null != list && list.size()>0){
					resultMap.put("img", list.get(0));
					resultMap.put("statusCode", "1");
				}else {
					resultMap.put("img", "");
					resultMap.put("statusCode", "2");
				}
				JSONObject json4 = JSONObject.fromObject(resultMap);
				resultJson = json4.toString();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("statusCode", "0");
			JSONObject json4 = JSONObject.fromObject(map);
			resultJson = json4.toString();
		} 
		ConstantUtil.reJSON(resultJson, request, response);
	}


}
