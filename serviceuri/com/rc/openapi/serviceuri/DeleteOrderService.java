package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.util.StringUtils;

import com.rc.openapi.service.TOrderManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TOrder;
import com.rc.openapi.vo.TOrderExample;

/**
 * 删除订单
 * 
 * 
 */
public class DeleteOrderService extends BaseURIService {

	private static final long serialVersionUID = 6185969883988053936L;

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionid = request.getParameter("sessionId");// 用户Id
		Map map = new HashMap();
		String statusCode = null;
		if(StringUtils.hasText(sessionid)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			try {
				Long userId = tmembermanager.getMemberId(sessionid);
				if(0<userId){
					String orderId = request.getParameter("orderId");// 订单Id
					TOrderManager manager = (TOrderManager)getSpringBean("tordermanager");
					TOrderExample example = new TOrderExample();
					example.createCriteria().andIdEqualTo(Long.parseLong(orderId));
					TOrder record = new TOrder();
					record.setId(Long.parseLong(orderId));
					record.setDeleteStatus(1);
					int rows = manager.updateByExampleSelective(record, example);
					if(0<rows){
						statusCode="1";
					}else {
						statusCode="3";
					}
				}else {
					statusCode="2";
				}
			} catch (SQLException e) {
				statusCode="0";
				e.printStackTrace();
			}
		}else {
			statusCode="0";
		}
		map.put("statusCode", statusCode);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


}
