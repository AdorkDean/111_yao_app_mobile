package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.service.TMemberReceiverManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
/**
 *  修改默认地址
 * @author Administrator
 *
 */
public class UpdateDefaultAreaService extends BaseURIService {

	private static final long serialVersionUID = 1110222120274369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");
		String areaId = request.getParameter("areaId");
		Map map = new HashMap();
		if(null != sessionId && !("").equals(sessionId) && null != areaId && !("").equals(areaId)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl)getSpringBean("tmembermanager");
			Long userId;
			try {
				userId = tmembermanager.getMemberId(sessionId);
				if(null != userId && userId != -1){
					TMemberReceiverManager tmemberreceivermanager = (TMemberReceiverManager)getSpringBean("tmemberreceivermanager");
					int i = tmemberreceivermanager.updateDefaultArea(userId, new Long(areaId));
					map.put("statusCode", "1");
				}else{
					map.put("statusCode", "2");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				map.put("statusCode", "0");
			}
			
		}else{
			//无效用户
			map.put("statusCode", "2");
		}
		
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

}
