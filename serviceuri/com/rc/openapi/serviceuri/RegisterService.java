package com.rc.openapi.serviceuri;

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

import sun.misc.BASE64Decoder;

import com.rc.commons.util.InfoUtil;
import com.rc.openapi.service.TCouponCardManager;
import com.rc.openapi.service.TCouponManager;
import com.rc.openapi.service.TMemberGradeManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.MD5;
import com.rc.openapi.util.MemCached;
import com.rc.openapi.util.NetworkUtil;
import com.rc.openapi.vo.TCoupon;
import com.rc.openapi.vo.TMember;
import com.rc.openapi.vo.TMemberExample;
import com.rc.openapi.vo.TMemberGrade;
import com.rc.openapi.vo.TMemberGradeExample;

/**
 * 用户注册
 * @author Administrator
 *
 */
public class RegisterService extends BaseURIService {

	private static final long serialVersionUID = 8300296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map map = new HashMap();
		String vcode = request.getParameter("vcode");
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		String source = request.getParameter("source");
		if(null != mobile  && !("").equals(mobile) && null != password && !("").equals(password)){
			String  code=	(String) MemCached.getmcc().get(MD5.MD5(mobile));
		   	if( null != code && !("").equals(code) && code.equals(vcode)){
		   		    TMemberManagerImpl tmembermanager = (TMemberManagerImpl)getSpringBean("tmembermanager");
					TMemberExample dme = new TMemberExample();
					dme.createCriteria().andMobileEqualTo(mobile).andIsMobileCheckEqualTo(1);
					List list = null;
					try {
						list = tmembermanager.selectByExample(dme);
						if(null == list || list.size()==0){
							TMember member = new TMember();
							member.setUserName("a_"+mobile);
							BASE64Decoder decoder = new BASE64Decoder();  
							byte[] b = decoder.decodeBuffer(password); 
							//password = new String(b);
							member.setPassword(MD5.MD5("111"+new String(b)+"yao"));	
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
							member.setSource(5);//用户来源 0，微信；1，QQ；2，支付宝；3，微博 4: 医卡通  5，手机,6，邮箱,   7:管理员后台注册  8 海典  9  其他
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
						}else{
							//手机号码已经注册
							map.put("statusCode", "3");
						}
					} catch (Exception e) {
						e.printStackTrace();
						map.put("statusCode", "0");
					}
			}else{  //验证码不匹配时 返回验证码错误
		    		map.put("statusCode", "2");
			}	
		}else{    //用户名或密码为空时返回错误提示
			map.put("statusCode", "4");
		}
		JSON json = JSONSerializer.toJSON(map);
		ConstantUtil.reJSON(json.toString(), request, response);
	}
}
