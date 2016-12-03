package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 门店订单
 * @author Administrator
 *
 */
public class StoreOrderService extends BaseURIService {

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
		String pageNo = request.getParameter("pageNo"); //当前页
		String pageSize = request.getParameter("pageSize");//当页显示数
        if(null == pageNo || ("").equals(pageNo)){
        	pageNo = "1";
        }
        if(null == pageSize || ("").equals(pageSize)){
        	pageSize = "10";
        }
        if(null !=sessionId && !("").equals(sessionId)){
        	TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
    		try {
    			Long userId = tmembermanager.getMemberId(sessionId);
    			if(null !=userId && !("").equals(userId)){
    				Map<String, String> publicMap = new HashMap<String, String>();
    				publicMap.put("member_id", userId.toString());
    				publicMap.put("pageNumber", pageNo);
    				publicMap.put("pageSize", pageSize);
    				TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
    				String homePageConfigId = sys.getKeys("public_service_url");
    				//String homePageConfigId="http://test88.111yao.cn/111_yao_public/sltRouter?method=";
    				//String homePageConfigId="http://public.111yao.com/sltRouter?method=";
    				String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId
    						+ "getOffLineOrderList");  //调用public接口
    				JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
    				if (jsonObject != null) {
    					statusCode = jsonObject.get("statusCode") == null ? "0" : jsonObject.get("statusCode").toString();// 成功
    					message = jsonObject.get("message") == null ? "" : jsonObject.get("message").toString();
    					if(!statusCode.equals("0")){
    						JSONArray jsonArray=jsonObject.getJSONArray("list");
    						map.put("orderList", jsonArray);
    					}else{
    						if(message.contains("当前页大于总页码数")){
    							message = "无更多记录";
    							statusCode = "1";
    						}
    					}	
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
        	message="无效用户";
        }
		map.put("statusCode", statusCode);
		map.put("message", message);
		JSON json = JSONSerializer.toJSON(map);
		ConstantUtil.reJSON(json.toString(), request, response);
		
	}
	
}
