package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.model.Product1Vo;
import com.rc.openapi.model.ReturnsVO;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
/**
 * 查看售后详情
 *
 */
public class GetReturnsInfoService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
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
					String orderId = request.getParameter("orderId");// 订单Id
					String retuId = request.getParameter("retuId");// 售后单Id
					OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
					map.put("retId", Long.parseLong(retuId));
					Object obj = om.selectForObjectByMap(map, "t_return.selectReturnsByRetId");
					List list = om.selectForListByMap(map, "t_return.selectReturnsProductByRetId");
					map.clear();
					map.put("orderId", Long.parseLong(orderId));
					Object obj1 = om.selectForObjectByMap(map, "t_return.selectReturnsOrderByOrderId");
					ReturnsVO robj = dataGroup(obj, list, obj1);
					map.clear();
					map.put("obj", robj);
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
		map.put("statusCode", statusCode);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	private ReturnsVO dataGroup(Object obj,List list,Object obj1){
		Map<String,Object> map = (Map<String,Object>)obj;
		Map<String,Object> map1 = (Map<String,Object>)obj1;
		ReturnsVO robj = new ReturnsVO();
		String aname = (String) map1.get("area_name");
		String anamess = (String) map1.get("address");
		robj.setArea(aname+"  "+anamess);
		robj.setConsignee((String)map1.get("consignee"));
		robj.setPhone((String)map1.get("phone"));
		robj.setOsn((String)map.get("osn"));
		robj.setRsn((String)map.get("rsn"));
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Timestamp odate = (Timestamp) map.get("odate");
		robj.setoDate(sdf.format(odate));
		Timestamp rdate = (Timestamp) map.get("rdate");
		robj.setrDate(sdf.format(rdate));
		robj.setPaymentMethodName((String)map.get("payment_method_name"));
		Integer sta = (Integer) map.get("status");
		if(sta==1){
			robj.setLogisticsInfo("1");
			String linfo = (String)map.get("logistics_company")+","+(String)map.get("express_no");
			if(!linfo.contains("null")){
				robj.setLogisticsInfo("2");
			}
		}else {
			String linfo = (String)map.get("logistics_company")+","+(String)map.get("express_no");
			if(linfo.contains("null")){
				robj.setLogisticsInfo("");
			}else {
				robj.setLogisticsInfo(linfo);
			}
		}
		robj.setStatus(sta.toString());
		robj.setRemarks((String)map.get("remarks"));
		robj.setDescription((String)map.get("description"));
		List<Product1Vo> plist = new ArrayList<Product1Vo>();
		BigDecimal priceSum = new BigDecimal("0");
		for (Object object : list) {
			Product1Vo pobj = new Product1Vo();
			Map<String,Object> maplist = (Map<String,Object>)object;
			long pid = (Long)maplist.get("product");
			pobj.setpId(pid+"");
			pobj.setImage((String)maplist.get("img"));
			pobj.setName((String)maplist.get("name"));
			Integer qua = (Integer) maplist.get("quantity");
			pobj.setQuantity(qua.toString());
			BigDecimal bd = (BigDecimal) maplist.get("price");
			if(null==bd){
				pobj.setPrice("待确认");
			}else {
				pobj.setPrice(bd.toString());
				priceSum = priceSum.add(bd).multiply(new BigDecimal(""+qua));
			}
			plist.add(pobj);
		}
		robj.setPlist(plist);
		if(!priceSum.equals(new BigDecimal("0"))){
			robj.setPriceSum(priceSum.toString());
		}else {
			robj.setPriceSum("待确认");
		}
		return robj;
	}
}
