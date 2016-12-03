package com.rc.openapi.serviceuriV3;

import java.io.IOException;
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

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TMemberBaseMessageExtManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberIntegralManagerImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.service.impl.TOrderManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
import com.rc.openapi.vo.TMember;
import com.rc.openapi.vo.TMemberBaseMessageExt;
import com.rc.openapi.vo.TMemberIntegral;
import com.rc.openapi.vo.TOrderExample;
/**
 * 用户中心 
 * @author user3
 *
 */
public class MemberCenterService extends BaseURIService{
	private static final long serialVersionUID = 8079626664100403441L;

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
		String username="医药馆会员";
		String signSuccess="0";//是否签到   0 未签到   1 已签到
		String errorMessage ="请求失败";
		String headPortrait ="http://ui1.111yao.com/static/image/temp/20160127/73569e0fe07f5e60234179baddec94eb.jpg";//头像地址
		String noPayOrderSize="0";//未支付订单个数
		String onlinePoint ="0";//线上积分
		String offlinePoint="0";//线下积分
		
		if(StringUtils.hasText(sessionId)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			TMemberBaseMessageExtManager tmemberbasemessageextmanager =(TMemberBaseMessageExtManager) getSpringBean("tmemberbasemessageextmanager");
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
						if(member.getIntegral()!=null){
							onlinePoint =String.valueOf(member.getIntegral());
						}
						
						TMemberBaseMessageExt memberbase= tmemberbasemessageextmanager.selectByPrimaryKey(member.getId());
						
						if(memberbase!=null){
							if(StringUtils.hasText(memberbase.getNickName())){
								username = memberbase.getNickName();
							}
						}
						if(!StringUtils.hasText(username)){
							if(member.getSource()!=null&&member.getSource().intValue()==0){
								username = "微信会员";
							}else{
								username = member.getUserName();
							}
						}
						if(StringUtils.hasText(member.getHeadPortrait())){
							if(member.getHeadPortrait().startsWith("http:")){
								headPortrait = member.getHeadPortrait();
							}else{
								headPortrait="http://ui1.111yao.com/"+member.getHeadPortrait();
							}
						}
						TOrderExample orderExample = new TOrderExample();
						orderExample.createCriteria().andMemberIdEqualTo(member.getId()).andOrderStatusEqualTo(0);
						TOrderManagerImpl tordermanager = (TOrderManagerImpl) getSpringBean("tordermanager");
						List orderlist =tordermanager.selectByExample(orderExample);
						if(orderlist!=null&&orderlist.size()>0){
							noPayOrderSize = String.valueOf(orderlist.size());
						}
						
						TSysParameterManager sysParameter = (TSysParameterManager) getSpringBean("tsysparametermanager");
						Map<String,String> publicMap = new HashMap<String,String>();
						publicMap.put("memberId", String.valueOf(member.getId()));
						String homePageConfigId = sysParameter.getKeys("public_service_url");
						String resultJsonstr =ClientSubmitPublic.getPublicService(publicMap, homePageConfigId+"getJifenOutline");
						// 这里 根据小魏返回内容处理
						JSONObject resultJson = JSONObject.fromObject(resultJsonstr);
						if(resultJson.get("statusCode").equals("1")){
							offlinePoint = String.valueOf(resultJson.get("jifen"));
						}
						statusCode="1";
						errorMessage="";
					}else{
						statusCode = "3";// 非法用户
						errorMessage ="非法请求";
					}
				}else{
					statusCode = "3";// 非法用户
					errorMessage ="非法请求";
				}
				
			} catch (Exception e) {
				statusCode = "0";// 
				errorMessage ="服务器异常";
				e.printStackTrace();
			}
		}else{
			statusCode = "2";// sessionId 不能为空
			errorMessage ="非法请求";
		}
		map.put("username", username);//用户名
		map.put("errorMessage", errorMessage);//错误信息
		map.put("headPortrait", headPortrait);//头像地址
		map.put("signSuccess", signSuccess);//是否签到  
		map.put("statusCode", statusCode);
		map.put("onlinePoint", onlinePoint);//线上积分
		map.put("offlinePoint", offlinePoint);//线下积分
		map.put("pointSum", String.valueOf(Integer.valueOf(onlinePoint)+Integer.valueOf(offlinePoint)));//总分数
		map.put("noPayOrderSize", noPayOrderSize);//未支付订单个数
		map.put("jklxUrl", InfoUtil.getInstance().getInfo("config", "member.jklxUrl")); // 健康领袖 url地址
	    map.put("isJklxShow", InfoUtil.getInstance().getInfo("config", "member.isJklxShow")); // 是否显示健康领袖   0  显示  1 不显示
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
