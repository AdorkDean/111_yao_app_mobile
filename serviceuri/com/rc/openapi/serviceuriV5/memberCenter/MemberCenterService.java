package com.rc.openapi.serviceuriV5.memberCenter;

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
import com.rc.openapi.dubbo.model.TOrderStatusCountModel;
import com.rc.openapi.service.TMemberBaseMessageExtManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberCollectManagerImpl;
import com.rc.openapi.service.impl.TMemberIntegralManagerImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceApi.OrderServiceDubboApi;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
import com.rc.openapi.vo.TMember;
import com.rc.openapi.vo.TMemberBaseMessageExt;
import com.rc.openapi.vo.TMemberCollectExample;
import com.rc.openapi.vo.TMemberIntegral;
/**
 * 用户中心
 * @author acer
 *
 */
public class MemberCenterService extends BaseURIService{
	private static final long serialVersionUID = -2833948709050307384L;
	private final Logger log = Logger.getLogger(getClass());
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		Map<String, String> map = new HashMap<String, String>();
		String statusCode = "";
		String username="";
		String signSuccess="0";//是否签到   0 未签到   1 已签到
		String errorMessage ="请求失败";
		String onlinePoint ="0";//线上积分
		String offlinePoint="0";//线下积分
		//默认头像地址
		String headPortrait="";
		//我的关注
		String memberFollow ="0";
		//我的优惠券
		String memberCoupon ="0";
		String noPayOrderSize="0";//未支付订单个数
		String orderPay="0";//待发货
		String orderDelivery ="0";//待收货
		String orderNoEvaluate="0";//待评价
		
		TSysParameterManager sysParameter = (TSysParameterManager) getSpringBean("tsysparametermanager");
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
						}else{
							headPortrait =sysParameter.getKeys("member_head_portrait_default");
						}
						OrderServiceDubboApi orderservicedubboapi = (OrderServiceDubboApi) getSpringBean("orderservicedubboapi");
						TOrderStatusCountModel orderStatusCount =orderservicedubboapi.orderStatusCountByMemberid(null,null,userId);
						if(orderStatusCount!=null){
							noPayOrderSize = String.valueOf(orderStatusCount.getOrderNoPay());
							 orderPay=String.valueOf(orderStatusCount.getOrderPay());
							 orderDelivery = String.valueOf(orderStatusCount.getOrderDelivery());
						}
						orderNoEvaluate = String.valueOf(orderservicedubboapi.orderNoEvaluateCount(userId));
						
						TMemberCollectManagerImpl tmembercollectmanager= (TMemberCollectManagerImpl) getSpringBean("tmembercollectmanager");
						TMemberCollectExample collectExample = new TMemberCollectExample();
						collectExample.createCriteria().andMemberIdEqualTo(userId).andCollectTypeEqualTo(0);
						//我的关注 
						List collectList =tmembercollectmanager.selectByExample(collectExample);
						if(collectList!=null&&collectList.size()>0){
							memberFollow = String.valueOf(collectList.size());
						}
						
						//我的优惠券
						OpenSqlManageImpl opensqlmanage = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
						Map<String,String> publicMap = new HashMap<String,String>();
						publicMap.put("memberId", String.valueOf(member.getId()));
						Map<String, Object> countMap = (Map<String, Object>) opensqlmanage.selectForObjectByMap(map, "t_coupon_card.selectCouponCardCountByMemberid");
						if(countMap!=null&&countMap.get("couponCount")!=null){
							memberCoupon = String.valueOf(countMap.get("couponCount"));
						}
						String homePageConfigId = sysParameter.getKeys("public_service_url");
						String resultJsonstr =ClientSubmitPublic.getPublicService(publicMap, homePageConfigId+"getJifenOutline");
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
		if(!StringUtils.hasText(username)){
			username ="医药馆会员";
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
		map.put("orderPay", orderPay);//待发货
		map.put("orderDelivery", orderDelivery);//待收货
		map.put("orderNoEvaluate", orderNoEvaluate);//待评价
		map.put("memberFollow", memberFollow);//我的关注
		map.put("memberCoupon", memberCoupon);//我的优惠券
		map.put("jklxUrl", InfoUtil.getInstance().getInfo("config", "member.jklxUrl")); // 健康领袖 url地址
	    map.put("isJklxShow", InfoUtil.getInstance().getInfo("config", "member.isJklxShow")); // 是否显示健康领袖   0  显示  1 不显示
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
