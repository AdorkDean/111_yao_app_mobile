package com.rc.openapi.serviceuri;

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

import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
/**
 *  获取某个用户地址
 * @author Administrator
 *
 */
public class GetAreaService extends BaseURIService {

	private static final long serialVersionUID = 1110222124974369342L;
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
					OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
					Map map1 = new HashMap();
					map1.put("membId", userId);
					map1.put("areaId", areaId);
					List rlist = om.selectForListByMap(map1, "t_member_receiver.select_receiverbyId");
					map.put("statusCode", "1");
					map.put("list", rlist);
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
