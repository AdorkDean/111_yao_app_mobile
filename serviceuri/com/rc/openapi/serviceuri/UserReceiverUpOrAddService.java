package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.service.CLocationManager;
import com.rc.openapi.service.TMemberReceiverManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.CLocation;
import com.rc.openapi.vo.TMemberReceiver;
/**
 *  添加或修改用户收货地址
 * @author Administrator
 *
 */
public class UserReceiverUpOrAddService extends BaseURIService {

	private static final long serialVersionUID = 1220222120274369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");
		String receiverId = request.getParameter("receiverId");  //收货地址ID
		String consignee = request.getParameter("consignee"); //收货人
		String phone = request.getParameter("phone");  //电话
		String areaId = request.getParameter("areaId");  //区域ID
		String address  = request.getParameter("address"); //收货地址
		String zipCode  = request.getParameter("zipCode"); //邮编
		String isDefault  = request.getParameter("isDefault"); //是否默认
		
		Map map = new HashMap();
		if(null != sessionId && !("").equals(sessionId) ){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl)getSpringBean("tmembermanager");
			Long userId;
			try {
				userId = tmembermanager.getMemberId(sessionId);
				if(null != userId && userId != -1){
					CLocationManager clocationmanager = (CLocationManager)getSpringBean("clocationmanager");
					CLocation area = clocationmanager.selectByPrimaryKey(new Integer(areaId));
					if(null != area){
						TMemberReceiverManager tmemberreceivermanager = (TMemberReceiverManager)getSpringBean("tmemberreceivermanager");
						if(null != receiverId && !("").equals(receiverId)){ //修改收货地址
							TMemberReceiver dr = new TMemberReceiver();
							dr.setId(new Long(receiverId));
							dr.setAddress(address);
							dr.setReceiver(consignee);
							dr.setCreateDate(new Date());
							dr.setMobile(phone);
							dr.setZipCode(zipCode);
							if(isDefault.equals("1")){
								dr.setIsDefault(1);
							}else{
								dr.setIsDefault(0);
							}
							dr.setMemberId(userId);
							dr.setAreaId(new Long(areaId));
							String fullName = clocationmanager.selectAreaName(new Integer(areaId));
							dr.setArea(fullName);
							tmemberreceivermanager.userReceiverUpOrAdd(dr, userId);
							map.put("statusCode", "1");
						}else{   //添加收货地址
							TMemberReceiver dr = new TMemberReceiver();
							dr.setAddress(address);
							dr.setReceiver(consignee);
							dr.setCreateDate(new Date());
							dr.setMobile(phone);
							dr.setZipCode(zipCode);
							if(isDefault.equals("1")){
								dr.setIsDefault(1);
							}else{
								dr.setIsDefault(0);
							}
							dr.setMemberId(userId);
							dr.setAreaId(new Long(areaId));
							String fullName = clocationmanager.selectAreaName(new Integer(areaId));
							dr.setArea(fullName);
							tmemberreceivermanager.userReceiverUpOrAdd(dr, userId);
							map.put("statusCode", "1");
						}
					}else{
						//无效地址
						map.put("statusCode", "3");
					}	
				}else{
					map.put("statusCode", "2");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				map.put("statusCode", "0");
			}
			
		}else{
			//无效用户
			map.put("statusCode", "2");
		}
		
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

}
