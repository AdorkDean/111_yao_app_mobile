package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.service.impl.TMemberIntegralManagerImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
import com.rc.openapi.vo.TMember;
import com.rc.openapi.vo.TMemberIntegral;
import com.rc.portal.jms.MessageSender;

/**
 * 用户签到积分
 * @author user3
 *
 */
public class AddSignService extends BaseURIService{
	private static final long serialVersionUID = -6813822681469855239L;
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
		if (sessionId != null && !"".equals(sessionId)) {
			try {
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if (userId == -1l) {
					statusCode = "3";// 非法用户
				} else {
					
						int sign_point =Integer.valueOf(InfoUtil.getInstance().getInfo("config", "user.sign_point"));
						int sign_point_jiange =Integer.valueOf(InfoUtil.getInstance().getInfo("config", "user.sign_point_jiange"));
						// 签到记录表
						TMemberIntegralManagerImpl tmemberintegralmanager = (TMemberIntegralManagerImpl) getSpringBean("tmemberintegralmanager");
						SimpleDateFormat yyyy = new SimpleDateFormat("yyyy-MM-dd");
						List singList = tmemberintegralmanager.selectByUserIdDate(userId.intValue(), yyyy.format(new Date()));
						TMember member = tmembermanager.selectByPrimaryKey(userId);
						if (singList != null && singList.size() > 0) {
							statusCode = "1";// 已经签到
						} else {
							List<TMemberIntegral> singBeforeList = tmemberintegralmanager.selectByUserIdDate(userId.intValue(), yyyy.format(getBeforeDate()));
//							TMember updateMember = new TMember();
							TMemberIntegral memberIntegral = new TMemberIntegral();
							memberIntegral.setMemberId(member.getId());
							memberIntegral.setCreateDate(new Date());
//							if(singBeforeList!=null&&singBeforeList.size()>0){
////								updateMember.setAccumulateIntegral(member.getAccumulateIntegral()+ singBeforeList.get(0).getIntegral()+sign_point_jiange);
//								memberIntegral.setIntegral(singBeforeList.get(0).getIntegral()+sign_point_jiange);
//								if(singBeforeList.get(0).getIntegral()==(sign_point+sign_point_jiange*6)){
////									updateMember.setAccumulateIntegral(member.getAccumulateIntegral()+ singBeforeList.get(0).getIntegral());
//									memberIntegral.setIntegral(singBeforeList.get(0).getIntegral());
//								}
//							}else{
////								updateMember.setAccumulateIntegral(member.getAccumulateIntegral() + sign_point);
//								memberIntegral.setIntegral(sign_point);
//							}
							memberIntegral.setIntegral(1);
							memberIntegral.setSource(8);//签到
//							updateMember.setId(userId);
							tmemberintegralmanager.insertPonit(memberIntegral,null);
							
							Map<String, String> memberMap = new HashMap<String, String>();
							System.out.println("String.valueOf(member.getId())======>"+String.valueOf(member.getId()));
							System.out.println(" String.valueOf(memberIntegral.getIntegral())===>"+ String.valueOf(memberIntegral.getIntegral()));
							memberMap.put("memberId", String.valueOf(member.getId()));
							memberMap.put("jifen", String.valueOf(memberIntegral.getIntegral()));
							memberMap.put("version", "v7");
							MessageSender topicMessageSender = (MessageSender) getSpringBean("topicMessageSender");
							topicMessageSender.sendMapMessage(memberMap);
							
							statusCode = "1";// 签到成功
						}
				}
			} catch (Exception e) {
				statusCode = "0";// 签到失败
				e.printStackTrace();
			}
		} else {
			statusCode = "2";// sessionId 不能为空
		}
		map.put("statusCode", statusCode);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
   
	public Date getBeforeDate(){
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(new Date());//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		return calendar.getTime();   //得到前一天的时间
	}
}
