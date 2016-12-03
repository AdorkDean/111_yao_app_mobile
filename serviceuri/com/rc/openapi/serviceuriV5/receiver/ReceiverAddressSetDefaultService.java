package com.rc.openapi.serviceuriV5.receiver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.service.impl.TMemberReceiverLatLonManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TMemberReceiverLatLon;
import com.rc.openapi.vo.TMemberReceiverLatLonExample;
/**
 * 设为默认收获地址
 * @author 刘志强
 *
 */
public class ReceiverAddressSetDefaultService extends BaseURIService{
	private static final long serialVersionUID = 7191472975202808684L;
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		String receiverId= request.getParameter("receiverId");//
		Map<String, String> resultMap = new HashMap<String, String>();
		String statusCode = "";
		String errorMessage ="请求失败";
		
		if(StringUtils.hasText(sessionId)&&StringUtils.hasText(receiverId)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			try {
				Long userId = tmembermanager.getMemberId(sessionId);
				if(userId!=-1l){
					TMemberReceiverLatLonManagerImpl tmemberreceiverlatlonmanager = (TMemberReceiverLatLonManagerImpl) getSpringBean("tmemberreceiverlatlonmanager");
					TMemberReceiverLatLonExample latlonExample = new TMemberReceiverLatLonExample();
					latlonExample.createCriteria().andIdEqualTo(Long.valueOf(receiverId.trim())).andMemberIdEqualTo(userId);
					List<TMemberReceiverLatLon> latlonList = tmemberreceiverlatlonmanager.selectByExample(latlonExample);
					if(latlonList!=null&&latlonList.size()>0){
						if(latlonList.get(0).getIsDefault().intValue()!=1){
							boolean flag =tmemberreceiverlatlonmanager.receiverLatlonSetDefault(Long.valueOf(receiverId), userId);
							if(!flag){
								statusCode="0";
								errorMessage="操作失败，请稍后重试。";
							}
						}
						statusCode="1";
						errorMessage="";
					}else{
						statusCode = "3";// 非法用户
						errorMessage ="非法请求";
					}
				}else{
					statusCode = "3";// 非法用户
					errorMessage ="非法请求";
				}
			} catch (Exception e) {
				statusCode = "0";// 
				errorMessage ="服务器异常";
				e.printStackTrace();
			}
		}else{
			statusCode = "2";// sessionId 不能为空
			errorMessage ="非法请求";
		}
		resultMap.put("errorMessage", errorMessage);
		resultMap.put("statusCode", statusCode);
		JSONObject json4 = JSONObject.fromObject(resultMap);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	
	
	
}
