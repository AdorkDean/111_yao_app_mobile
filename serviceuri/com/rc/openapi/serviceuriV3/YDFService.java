package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.MD5;
import com.rc.openapi.vo.TMember;

/**
 * 壹大夫
 * @author user3
 *
 */
public class YDFService extends BaseURIService{
	private static final long serialVersionUID = -6812222681469855239L;
	private static String logurl = "http://web.111doctor.com/login/passwordFree";
	private static String url = "http://web.111doctor.com/wechat/patient/homepage/getHomePageInfo";
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		Map<String, String> map = new HashMap<String, String>();
		String mobile = "";
		String str = "";
		String key="HLs8BSFH9riHZFYggp7E"; //秘钥
		if (sessionId != null && !"".equals(sessionId)) {
			try {
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if (userId == -1l) {
				} else {
					TMember member = tmembermanager.selectByPrimaryKey(userId);
					if(null != member && null!=member.getMobile()){
						mobile = member.getMobile();
						str = MD5.MD5(key+mobile);
						System.out.println("str=="+str+"||mobile=="+mobile);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		if(null != str && !("").equals(str)){
			System.out.println("logurl=="+logurl);
			response.sendRedirect(logurl+"?phone="+mobile+"&sign="+str);
		}else{
			response.sendRedirect(url);
		}
	}
   
	public Date getBeforeDate(){
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(new Date());//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		return calendar.getTime();   //得到前一天的时间
	}
}
