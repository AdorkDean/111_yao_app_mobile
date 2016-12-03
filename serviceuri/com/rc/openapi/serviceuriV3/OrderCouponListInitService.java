package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.NumberUtil;
/**
 * 结算页面 优惠券列表页面
 * @author user3
 *
 */
public class OrderCouponListInitService extends BaseURIService{
	private static final long serialVersionUID = -8567569487541745879L;
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = "0";
		String errorMessage ="";
		String platform = "3";//平台参数
		List<Map<String,String>>  couponList = new ArrayList<Map<String,String>>();
		if (sessionId != null && !"".equals(sessionId)) {
			try {
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if (userId == -1l) {
					statusCode = "3";// 非法用户
					errorMessage="无效用户";
				} else {
					TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
					String homePageConfigId = sys.getKeys("public_service_url");
					Map<String,String> publicMap = new HashMap<String,String>();
					publicMap.put("platform", platform);
					publicMap.put("memberId", String.valueOf(userId));
					String resultJsonstr =ClientSubmitPublic.getPublicService(publicMap, homePageConfigId+"ordercouponlist");
					Map<String, Class> classMap = new HashMap<String, Class>();  
					classMap.put("couponList", HashMap.class);  
					Map<String,Object> resultMap =(Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(resultJsonstr),HashMap.class,classMap);
					if(resultMap.get("flag").equals("1")){
						List<Map<String,Object>> couponPublicList =  (List<Map<String, Object>>) resultMap.get("couponList");
						if(couponPublicList!=null&&couponPublicList.size()>0){
							Map<String,String> couponMap = null;
							for(Map<String,Object> couponPublicMap:couponPublicList){
								if("1".equals(couponPublicMap.get("isuse"))){
									couponMap=new HashMap<String,String>();
									couponMap.put("id", String.valueOf(couponPublicMap.get("id")));
									couponMap.put("disPrice",NumberUtil.format2String(new BigDecimal(String.valueOf(couponPublicMap.get("dis_price")))));
									couponMap.put("name", String.valueOf(couponPublicMap.get("name")));
									couponMap.put("beginDate", String.valueOf(couponPublicMap.get("begin_Date")));
									couponMap.put("endDate", String.valueOf(couponPublicMap.get("end_date")));
									couponMap.put("isuse", String.valueOf(couponPublicMap.get("isuse")));
									couponList.add(couponMap);
								}
								
							}
							for(Map<String,Object> couponPublicMap:couponPublicList){
								if("0".equals(couponPublicMap.get("isuse"))){
									couponMap=new HashMap<String,String>();
									couponMap.put("id", String.valueOf(couponPublicMap.get("id")));
									couponMap.put("disPrice",NumberUtil.format2String(new BigDecimal(String.valueOf(couponPublicMap.get("dis_price")))));
									couponMap.put("name", String.valueOf(couponPublicMap.get("name")));
									couponMap.put("beginDate", String.valueOf(couponPublicMap.get("begin_Date")));
									couponMap.put("endDate", String.valueOf(couponPublicMap.get("end_date")));
									couponMap.put("isuse", String.valueOf(couponPublicMap.get("isuse")));
									couponList.add(couponMap);
								}
							}
						}
						statusCode = "1";// 成功
					}else{
						statusCode = "0";// 失败
						errorMessage="获取数据失败";
					}
				}
			} catch (Exception e) {
				statusCode = "0";// 失败
				errorMessage="获取数据失败";
				e.printStackTrace();
			}
		} else {
			statusCode = "2";// sessionId 不能为空
			errorMessage="无效用户";
		}
		map.put("statusCode", statusCode);
		map.put("errorMessage", errorMessage);
		map.put("couponList", couponList);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	

}
