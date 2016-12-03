package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.HashMap;
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

/**
 * 门店订单评论页面
 * @author Administrator
 *
 */
public class CommentService extends BaseURIService {

	private static final long serialVersionUID = 8312396122974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String statusCode = "0";
		String message = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map map = new HashMap();
		String sessionId = request.getParameter("sessionId");
		String orderSn = request.getParameter("orderSn");
		try {
			if(null !=sessionId && !("").equals(sessionId)){
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
    			Long userId = tmembermanager.getMemberId(sessionId);
			    if(null !=userId && !("").equals(userId) && null !=orderSn && !("").equals(orderSn)){
					Map<String, String> publicMap = new HashMap<String, String>();
					publicMap.put("member_id", userId.toString());
					publicMap.put("order_id", orderSn);
					TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
					String homePageConfigId = sys.getKeys("public_service_url");
					//String homePageConfigId="http://public.111yao.com/sltRouter?method=";
					String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId
							+ "getOffLineOrderItemList");  //调用public接口
					JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
					if (jsonObject != null) {
						System.out.println(jsonObject);
						statusCode = jsonObject.get("statusCode") == null ? "0" : jsonObject.get("statusCode").toString();// 成功
						message = jsonObject.get("message") == null ? "" : jsonObject.get("message").toString();
						if(!statusCode.equals("0")){
							resultMap = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonObject.get("order")),
									Map.class);
							request.setAttribute("order", resultMap);
						}else{
							statusCode = message;
						}	
					}
				}else{
					statusCode="参数错误";
				}
			}else{
				statusCode="参数错误";
			}
		} catch (Exception e) {
			statusCode = "系统异常";
			e.printStackTrace();
		}
		request.setAttribute("sessionId", sessionId);
		request.setAttribute("orderSn", orderSn);
		request.getRequestDispatcher("/page/comment.jsp").forward(request, response);
	}
	
}
