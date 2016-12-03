package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.model.LogiData;
import com.rc.openapi.model.LogisticsDataVO;
import com.rc.openapi.model.LogisticsDataVO.ProductData;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;


public class GetLogisticsService extends BaseURIService {

	private static final long serialVersionUID = 8300296122274369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionid = request.getParameter("sessionId");// 用户Id
		Map map = new HashMap();
		String statusCode = null;
		if(StringUtils.hasText(sessionid)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			try {
				Long userId = tmembermanager.getMemberId(sessionid);
				if(0<userId){
					String orderId = request.getParameter("orderId");
					OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
					map.put("orderId", Long.parseLong(orderId));
					List<Map<String,Object>> goodss = om.selectForListByMap(map, "t_order_item.selectGoodsByOrderId");
					LogisticsDataVO lobj = new LogisticsDataVO();
					lobj.setStatus("正在途中");
					List<ProductData> plist = new ArrayList<ProductData>();
					for (Map<String,Object> obj : goodss) {
						ProductData pdata = lobj.new ProductData();
						pdata.setName((String)obj.get("name"));
						pdata.setPrice((String)obj.get("price"));
						pdata.setQuantity((Integer)obj.get("quantity"));
						pdata.setThumbnail((String)obj.get("thumbnail"));
						pdata.setId((Long)obj.get("id"));
						plist.add(pdata);
					}
					lobj.setPlist(plist);
					//物流状态 0在途1揽件2疑难3签收(4退签5派件6退回7转投  增值服务)
					List<Map<String,Object>> list = om.selectForListByMap(map, "t_order_shipment.selectLogisticsByOrderId");
					map.clear();
					for (Map<String,Object> obj : list) {
						lobj.setExpid((String)obj.get("expid"));
						lobj.setName((String)obj.get("name"));
						if(null!=obj.get("data")&&!"".equals(obj.get("data"))){
							JSONArray jsonArray = JSONArray.fromObject(obj.get("data"));  
							List<LogiData> ldata = (List<LogiData>) JSONArray.toCollection(jsonArray,LogiData.class); 
							lobj.setLlist(ldata);
							String stai = (String)obj.get("shipping_status");
							int sta = Integer.parseInt(stai);
							String stas = null;
							if(0==sta){
								stas = "正在途中";
							}else if (1==sta) {
								stas = "快递公司揽件中";
							}else if (2==sta) {
								stas = "发货疑难";
							}else if (3==sta) {
								stas = "已签收";
							}else if (4==sta) {
								stas = "已退签";
							}else if (5==sta) {
								stas = "正在派件";
							}else if (6==sta) {
								stas = "已退回";
							}else if (7==sta) {
								stas = "已转投";
							}
							if(null!=stas){
								lobj.setStatus(stas);
							}
						}
					}
					request.setAttribute("obj", lobj);
					request.getRequestDispatcher("page/view_logistics.jsp").forward(request, response);
					statusCode="1";
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
		if(!"1".equals(statusCode)){
			map.put("statusCode", statusCode);
			JSONObject json4 = JSONObject.fromObject(map);
			ConstantUtil.reJSON(json4.toString(), request, response);
		}
	}
}
