package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.rc.openapi.service.TMemberThreeBindingManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.MD5;
import com.rc.openapi.util.NetworkUtil;
import com.rc.openapi.vo.TCoupon;
import com.rc.openapi.vo.TMember;
import com.rc.openapi.vo.TMemberExample;
import com.rc.openapi.vo.TMemberGrade;
import com.rc.openapi.vo.TMemberGradeExample;
import com.rc.openapi.vo.TMemberThreeBinding;
import com.rc.openapi.vo.TMemberThreeBindingExample;
/**
 * 微信登录
 * @author Administrator
 *
 */
public class WXLoginService extends BaseURIService {

	private static final long serialVersionUID = 8312396122974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map map = new HashMap();
		String uid = request.getParameter("uid");
		String uidType = request.getParameter("uidType");
		String source = request.getParameter("source");
		String username = request.getParameter("username");
		String imgUrl   = request.getParameter("imgUrl");
		if(null  != uid && !("").equals(uid) && null != uidType && !("").equals(uidType) ){
			TMemberThreeBindingManager tmemberthreebindingmanager = (TMemberThreeBindingManager)getSpringBean("tmemberthreebindingmanager");
			TMemberThreeBindingExample tmbe = new TMemberThreeBindingExample();
			tmbe.createCriteria().andBindingUuidEqualTo(uid).andSourceEqualTo(0);
			List list = null;
			try {
				list =	tmemberthreebindingmanager.selectByExample(tmbe);
				if(null != list && list.size() > 0){
					TMemberThreeBinding binding = (TMemberThreeBinding) list.get(0);
					TMemberManagerImpl tmembermanager = (TMemberManagerImpl)getSpringBean("tmembermanager");
					TMember member = tmembermanager.selectByPrimaryKey(binding.getMemberId());
					if(member.getStatus()==1){
						map.put("statusCode", "3");
					}else{
						TMemberExample tme = new TMemberExample();
						tme.createCriteria().andUserNameEqualTo(member.getUserName());
						tmembermanager.updateWXMember(member,tme,map,imgUrl);
						map.put("statusCode", "1");
					}
				}else{
					TMemberManagerImpl tmembermanager = (TMemberManagerImpl)getSpringBean("tmembermanager");
					
					TMember member = new TMember();
					member.setUserName("wx_"+uid);
					member.setPassword(MD5.MD5("111123456yao"));		
					member.setRegisterDate(new Date());
					member.setRegisterIp(NetworkUtil.getIpAddress(request));
					member.setLastDate(new Date());
					member.setLastIp(NetworkUtil.getIpAddress(request));
					member.setStatus(0);
					member.setEnterpriseDiscount(new BigDecimal(0));
					member.setIsLeader(0);
					member.setIntegral(0);
					member.setSource(0); //用户来源 0，微信；1，QQ；2，支付宝；3，微博 4: 医卡通  5，手机,6，邮箱,   7:管理员后台注册  8 海典  9  其他
					if(null !=imgUrl && !("").equals(imgUrl)){
						member.setHeadPortrait(imgUrl); //头像
					}
					if(null != username && !("").equals(username)){
						member.setNickName(username);
					}
					System.out.println("=========="+source+"----------------");
					if(null !=source && source.equals("ios")){
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
					Long id = tmembermanager.insertMemberBinding(member,map,uid);
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
			} catch (Exception e) {
				map.put("statusCode", "0");
				e.printStackTrace();
			}
		}else{  //用户名 或密码为空时返回 用户名或密码错误
			map.put("statusCode", "2");
		}
		JSON json = JSONSerializer.toJSON(map);
		ConstantUtil.reJSON(json.toString(), request, response);
	}

}
