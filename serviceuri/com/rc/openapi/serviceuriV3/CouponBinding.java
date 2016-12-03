package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TCouponCardManager;
import com.rc.openapi.service.TCouponManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TCoupon;
/**
 * 领劵专区用户领取优惠劵
 *
 */
public class CouponBinding extends BaseURIService {
	private static final long serialVersionUID = 4L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String couponId = request.getParameter("couponId");
		String sessionId = request.getParameter("sessionId");
		String falg = "0";
		if(null != sessionId && !("").equals(sessionId)){  //sessionId是否存在
			if(null != couponId && !("").equals(couponId)){
				try {
					TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
					Long userId = tmembermanager.getMemberId(sessionId);
					if(userId==-1L){
						falg = "4"; //非法用户
					}else{
						TSysParameterManager tsysparametermanager = (TSysParameterManager) getSpringBean("tsysparametermanager");
						String cp  = tsysparametermanager.getKeys("couponId");
						// cp 网站活动可以领取的优惠劵ID集合 
						String c[] = cp.split(",");
						boolean b = false;
						for (int i = 0; i < c.length; i++) {
							if (couponId.equals(c[i])) {
								b = true;
								break;
							}
						}
						if (!b) {
							falg = "5"; //优惠劵不存在
						}else{
							Map<String,Object> querMap = new HashMap<String,Object>();
							querMap.put("cp", couponId);
							querMap.put("member_id", userId);
							OpenSqlManage opensqlmanage = (OpenSqlManage)getSpringBean("opensqlmanage");
							List list =  opensqlmanage.selectForListByMap(querMap, "t_coupon.getCoupon"); 
							if(null != list && list.size() >0){
								Map map = (Map) list.get(0);
								String isget =  map.get("isget").toString();
								if(null != isget && isget.equals("0")){
									 TCouponCardManager tcouponcardmanager = (TCouponCardManager)getSpringBean("tcouponcardmanager");
									 TCouponManager tcouponmanager = (TCouponManager)getSpringBean("tcouponmanager");
									 TCoupon coupon = tcouponmanager
												.selectByPrimaryKey(new Long(couponId));
										Long id = tcouponcardmanager.bindingCouponCard(coupon,
												userId, 1);
										if (id > 0) {
											falg = "1"; //成功
										}else{
											falg = "7";  //失败
										}
								}else{
									falg = "6"; //用户已经绑定过优惠劵
								}
							}else{
								falg = "5"; //优惠劵不存在
							}
						} 
					}
				} catch (SQLException e) {
					falg = "8";  //异常
					e.printStackTrace();
				}
			}else{
				falg = "3"; //优惠劵参数无效
			}
		}else{
			falg = "2"; //用户名参数无效
		}
		ConstantUtil.reJSON(falg, request, response);
	}
}
