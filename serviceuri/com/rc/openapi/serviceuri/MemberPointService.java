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
import org.springframework.util.StringUtils;

import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TMember;

/**
 * 用户中心 我的积分  用户名
 * @author user3
 *
 */
public class MemberPointService extends BaseURIService{
	private static final long serialVersionUID = 2464726014051270108L;
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		Map<String, String> map = new HashMap<String, String>();
		String statusCode = "";
		String pointSum ="";
		String username="";
		if(StringUtils.hasText(sessionId)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			try {
				Long userId = tmembermanager.getMemberId(sessionId);
				if(userId!=-1l){
					TMember member = tmembermanager.selectByPrimaryKey(userId);
					if(member!=null){
						pointSum =String.valueOf(member.getIntegral());
						if(StringUtils.hasText(member.getUserName())){
							username = member.getUserName();
						}
						statusCode="1";
					}else{
						statusCode = "3";// 非法用户
					}
				}else{
					statusCode = "3";// 非法用户
				}
				
			} catch (SQLException e) {
				statusCode = "0";// 
				e.printStackTrace();
			}
		}else{
			statusCode = "2";// sessionId 不能为空
		}
		map.put("username", username);//用户名
		map.put("statusCode", statusCode);
		map.put("pointSum", pointSum);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	
	
	

}
