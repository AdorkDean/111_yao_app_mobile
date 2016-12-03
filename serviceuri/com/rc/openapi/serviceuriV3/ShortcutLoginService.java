package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.rc.commons.util.InfoUtil;
import com.rc.openapi.service.TCouponCardManager;
import com.rc.openapi.service.TCouponManager;
import com.rc.openapi.service.TMemberGradeManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.CustomDigestUtils;
import com.rc.openapi.util.MD5;
import com.rc.openapi.util.MemCached;
import com.rc.openapi.util.NetworkUtil;
import com.rc.openapi.vo.TCoupon;
import com.rc.openapi.vo.TMember;
import com.rc.openapi.vo.TMemberExample;
import com.rc.openapi.vo.TMemberGrade;
import com.rc.openapi.vo.TMemberGradeExample;

/**
 * 手机快捷登录
 * @author Administrator
 *
 */
public class ShortcutLoginService extends BaseURIService {

	private static final long serialVersionUID = 8312396122974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map map = new HashMap();
		String mobile = request.getParameter("mobile");
		String mcode = request.getParameter("code");
		String client_type = request.getParameter("client_type");// 客户端系统
		if(null != mobile && !("").equals(mobile) && null != mcode && !("").equals(mcode)){
			String  code =	(String) MemCached.getmcc().get(MD5.MD5("shortcut"+mobile));
			if(null != code && !("").equals(code)){
				if(mcode.equals(code)){
					try {
					  TMemberManagerImpl tmembermanager = (TMemberManagerImpl)getSpringBean("tmembermanager");
					  TMemberExample example = new TMemberExample();
					  example.createCriteria().andMobileEqualTo(mobile).andIsMobileCheckEqualTo(1);
					  List list = tmembermanager.selectByExample(example);
					  if(null != list && list.size()>0){   //判断用户是否存在  
						  //用户存在直接取session
						  TMember member = (TMember) list.get(0);
						  tmembermanager.updateMemberToSession(member,example,map);
						  map.put("statusCode", "1");
					  }else{
						  //用户不存在创建用户并获取session
						  TMember member = new TMember();
						  member.setPassword(CustomDigestUtils.md5Hex("111yao",member));
						  member.setUserName("a_"+mobile);
						  member.setNickName(mobile);
						  member.setMobile(mobile);
						  member.setIsMobileCheck(1);
						  member.setRegisterDate(new Date());
						  member.setRegisterIp(NetworkUtil.getIpAddress(request));
						  member.setLastDate(new Date());
						  member.setLastIp(NetworkUtil.getIpAddress(request));
						  member.setStatus(0);
						  member.setEnterpriseDiscount(new BigDecimal(0));
						  member.setIsLeader(0);
						  member.setIntegral(0);
						  member.setSource(5);//用户来源 0，微信；1，QQ；2，支付宝；3，微博 4: 医卡通  5，手机,6，邮箱, 7:管理员后台注册  8 海典  9  其他
						  if(null !=client_type && client_type.equals("iOS")){
							member.setPlatform(4);
						  }else{
							member.setPlatform(3);
						  }
						  member.setIsAgent(0); //是否代理 0不是 1是
							TMemberGradeManager tmembergrademanager = (TMemberGradeManager)getSpringBean("tmembergrademanager");
							TMemberGradeExample tmge = new TMemberGradeExample();
							tmge.createCriteria().andIsDefaultEqualTo(true);
							List gradeList = tmembergrademanager.selectByExample(tmge);
							if(null != gradeList && gradeList.size()>0){
								TMemberGrade grade = (TMemberGrade) gradeList.get(0);
								member.setMemberGradeId(grade.getId()); //会员等级
							}else{
								member.setMemberGradeId(0L);
							}
						  Long id = tmembermanager.insertSelectiveAndSession(member, map);
						  String cp = InfoUtil.getInstance().getInfo("config", "regCouponId");
						  List regCouponMap = new ArrayList();
						  String regCouponId="";
						  Map couponMap=new HashMap();
						  if(cp!=null&&!cp.equals("")){
							  TCouponCardManager tcouponcardmanager = (TCouponCardManager) getSpringBean("tcouponcardmanager");
							  TCouponManager tcouponmanager = (TCouponManager) getSpringBean("tcouponmanager");
							  String c[] = cp.split(",");
							  for (int i = 0; i < c.length; i++) {
								regCouponId=c[i];
								TCoupon coupon = tcouponmanager.selectByPrimaryKey(new Long(regCouponId));
								regCouponMap.add(coupon);
							  }
							  tcouponcardmanager.bindingCoupon(regCouponMap,id, 1);
						  }
						  map.put("statusCode", "1");
					   }
					} catch (SQLException e) {
						map.put("statusCode", "0"); 
						e.printStackTrace();
					}
				}else{
					map.put("statusCode", "3"); //验证码不正确
				}
			}else{
				map.put("statusCode", "3"); //手机没有验证
			}
		}else{
			map.put("statusCode", "2"); //参数错误
		}
		
		JSON json = JSONSerializer.toJSON(map);
		ConstantUtil.reJSON(json.toString(), request, response);
	}
	
	
}
