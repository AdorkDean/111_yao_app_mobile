package com.rc.openapi.serviceuriV2;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.impl.TMemberIntegralManagerImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
import com.rc.openapi.vo.TMember;
import com.rc.openapi.vo.TMemberIntegral;
/**
 * 用户中心 我的积分  用户名  V2
 * @author user3
 *
 */
public class MemberPointService extends BaseURIService{
	private static final long serialVersionUID = -1262520118440563926L;

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
		String signSuccess="0";//是否签到   0 未签到   1 已签到
		if(StringUtils.hasText(sessionId)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			try {
				Long userId = tmembermanager.getMemberId(sessionId);
				if(userId!=-1l){
					TMember member = tmembermanager.selectByPrimaryKey(userId);
					if(member!=null){
						TMemberIntegralManagerImpl tmemberintegralmanager = (TMemberIntegralManagerImpl) getSpringBean("tmemberintegralmanager");
						SimpleDateFormat yyyy = new SimpleDateFormat("yyyy-MM-dd");
						List<TMemberIntegral> singList = tmemberintegralmanager.selectByUserIdDate(userId.intValue(), yyyy.format(new Date()));
						if(singList!=null&&singList.size()>0){
							signSuccess="1";//已签到
						}
//						pointSum =String.valueOf(member.getAccumulateIntegral()-member.getIntegral());
						pointSum =String.valueOf(member.getIntegral());
						if(StringUtils.hasText(member.getUserName())){
							username = member.getUserName();
						}
						if(member.getSource()!=null&&member.getSource().intValue()==0){
							username = "微信会员";
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
		map.put("signSuccess", signSuccess);//是否签到  
		map.put("statusCode", statusCode);
		map.put("pointSum", pointSum);
		//2.0.3   2.0.2  2.0.1
		String client_version =request.getParameter("client_version");
		String client_type = request.getParameter("client_type");
//		v1.1.0    v2.2
	    if("Android".equals(client_type)&&(client_version.equals("2.0.3")||client_version.equals("2.0.2")||client_version.equals("2.0.1"))){
	    	map.put("jklxUrl", ""); // 健康领袖 url地址相对路径
	    	map.put("isJklxShow", "1"); // 是否显示健康领袖   0  显示  1 不显示
	    }else if("iOS".equals(client_type)&&(client_version.equals("v1.1.0")||client_version.equals("v2.2"))){
	    	map.put("jklxUrl", ""); // 健康领袖 url地址相对路径
	    	map.put("isJklxShow", "1"); // 是否显示健康领袖   0  显示  1 不显示
	    }else{
	    	map.put("jklxUrl", InfoUtil.getInstance().getInfo("config", "member.jklxUrl")); // 健康领袖 url地址
		    map.put("isJklxShow", InfoUtil.getInstance().getInfo("config", "member.isJklxShow")); // 是否显示健康领袖   0  显示  1 不显示
	    }
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	
}
