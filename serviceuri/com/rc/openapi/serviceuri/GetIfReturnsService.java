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
import org.springframework.util.StringUtils;

import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
/**
 * 判断该订单是否已加入售后
 *
 */
public class GetIfReturnsService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionid = request.getParameter("sessionId");// 用户Id
		Map map = new HashMap();
		String statusCode = null;
		if(StringUtils.hasText(sessionid)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			try {
				Long userId = tmembermanager.getMemberId(sessionid);
				if(0<userId){
					String ordId = request.getParameter("ordId");// 订单Id
					if(null!=ordId&&!"".equals(ordId.trim())){
						OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
						map.put("ordId", ordId);
						List list = om.selectForListByMap(map, "t_return.selectIfReturnsByOrdId");
						if(null!=list&&0<list.size()){
							statusCode = "1";
						}else {
							statusCode = "3";
						}
					}else {
						statusCode = "0";
					}
				}else {
					statusCode="2";
				}
			} catch (SQLException e) {
				statusCode="0";
				e.printStackTrace();
			}
		}else {
			statusCode="0";
		}
		map.clear();
		map.put("statusCode", statusCode);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
}
