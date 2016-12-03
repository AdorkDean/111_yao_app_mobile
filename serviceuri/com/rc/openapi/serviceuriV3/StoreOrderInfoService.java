package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;

/**
 * 门店订单详情
 * @author Administrator
 *
 */
public class StoreOrderInfoService extends BaseURIService {

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
		TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
		try {
			Long userId = tmembermanager.getMemberId(sessionId);
			if(null !=userId && !("").equals(userId)){
				Map<String, String> publicMap = new HashMap<String, String>();
				publicMap.put("member_id", userId.toString());
				//publicMap.put("member_id", "171924");
				publicMap.put("order_id", orderSn);
				//publicMap.put("order_id", "13012081120014");
				TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
				String homePageConfigId = sys.getKeys("public_service_url");
				//String homePageConfigId="http://test88.111yao.cn/111_yao_public/sltRouter?method=";
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
						JSONArray jsonArray=jsonObject.getJSONArray("list");
						List list = JSONArray.toList(jsonArray, Map.class);
						request.setAttribute("goodsList", list);
						request.setAttribute("order", resultMap);
						System.out.println(list.size());
					}else{
						//错误
					}	
				}
			}else{
				statusCode="2";
			}
			
		} catch (Exception e) {
			statusCode = "0";
			e.printStackTrace();
		}
//		resultMap.put("orderAmount", "101");
//		resultMap.put("orderSn", "1234566888");
//		resultMap.put("orderStatus", "已完成");
//		resultMap.put("orderDT", "2016-01-15");
//		List list = new ArrayList();
//		for (int i = 0; i < 5; i++) {
//			Map map1 = new HashMap();
//			map1.put("spec", "1.17g*300片");
//			map1.put("price", "10"+i);
//			map1.put("shortName", "金斯利安多维片");
//			map1.put("number", i+"");
//			list.add(map1);
//		}
		request.setAttribute("sessionId", sessionId);
		request.setAttribute("orderSn", orderSn);
		request.getRequestDispatcher("/page/storeOrderInfo.jsp").forward(request, response);
	}
	
}
