package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 兑换优惠劵
 * @author Administrator
 *
 */
public class ExchangeCouponService extends BaseURIService {

	private static final long serialVersionUID = 8312396122974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String statusCode = "1";
		String message = "";
		Map<String, Object> resultMap = null;
		
		Map map = new HashMap();
		String sessionId = request.getParameter("sessionId");
		String couponId = request.getParameter("couponId"); //优惠劵ID
		
        
        if(null !=sessionId && !("").equals(sessionId) && null != couponId && !("").equals(couponId)){
        	TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
    		try {
    			Long userId = tmembermanager.getMemberId(sessionId);
    			if(null !=userId && !("").equals(userId)){
    				Map<String, String> publicMap = new HashMap<String, String>();
    				publicMap.put("memberId", userId.toString());
    				publicMap.put("id", couponId);
    				TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
    				String homePageConfigId = sys.getKeys("public_service_url");
    				//String homePageConfigId="http://public.111yao.com/sltRouter?method=";
    				String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId
    						+ "convertCoupon");  //调用public接口
    				JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
    				if (jsonObject != null) {
    					statusCode = jsonObject.get("statusCode") == null ? "0" : jsonObject.get("statusCode").toString();// 成功
    					message = jsonObject.get("message") == null ? "" : jsonObject.get("message").toString();
    				}
    			}else{
    				statusCode = "0"; //用户不可用
    				message="无效用户";
    			}
    		} catch (Exception e) {
    			statusCode = "0";
    			e.printStackTrace();
    			message="系统异常";
    		}
        }else{
        	statusCode = "0"; //用户不可用
        	message="参数错误";
        }
		map.put("statusCode", statusCode);
		map.put("message", message);
		JSON json = JSONSerializer.toJSON(map);
		ConstantUtil.reJSON(json.toString(), request, response);
		
	}
	
}
