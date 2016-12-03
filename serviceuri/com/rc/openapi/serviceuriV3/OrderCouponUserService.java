package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
/**
 * 优惠券券码是否可用
 * @author user3
 *
 */
public class OrderCouponUserService  extends BaseURIService{
	private static final long serialVersionUID = 6457307508765790184L;

	
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		String couponCardNo = request.getParameter("couponCardNo");//优惠券券码
		String platform = "3";//平台参数
		String statusCode = "0";
		String errorMessage ="";
		String couponCardId ="";
		String couponName="";
		Map<String, Object> resultmap = new HashMap<String, Object>();
		if (StringUtils.hasText(sessionId)) {
			try {
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if (userId == -1l) {
					statusCode = "3";// 非法用户
					errorMessage="无效用户";
				} else {
					if(!StringUtils.hasText(couponCardNo)){
						errorMessage ="请输入优惠券编码!";
					}else{
						TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
						String homePageConfigId = sys.getKeys("public_service_url");
						Map<String,String> publicMap = new HashMap<String,String>();
						publicMap.put("couponCardNo", couponCardNo.trim());
						publicMap.put("platform", platform);
						publicMap.put("memberId", String.valueOf(userId));
						String resultJsonstr =ClientSubmitPublic.getPublicService(publicMap, homePageConfigId+"ordercouponuser");
						Map<String,String> resultMap =(Map<String, String>) JSONObject.toBean(JSONObject.fromObject(resultJsonstr),HashMap.class);
						if(resultMap.get("resultflag").equals("1")){
							statusCode = "1";// 成功
							couponCardId = resultMap.get("couponCardId");
						    couponName	=resultMap.get("couponName");	
						}else{
							statusCode = "0";// 失败
							errorMessage=resultMap.get("errorMessage");
						}
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
		resultmap.put("statusCode", statusCode);
		resultmap.put("errorMessage", errorMessage);
		resultmap.put("couponCardId", couponCardId);
		resultmap.put("couponName", couponName);
		JSONObject json4 = JSONObject.fromObject(resultmap);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
}
