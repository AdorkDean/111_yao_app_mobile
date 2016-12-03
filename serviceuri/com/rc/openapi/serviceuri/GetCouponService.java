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

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.DateUtils;
/**
 * 查看优惠券
 *
 */
public class GetCouponService extends BaseURIService {

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
					OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
					map.put("membId", userId);
					List<Map<String,Object>> list = om.selectForListByMap(map,"t_coupon_card.ibatorgenerated_selectByMember");
					map.clear();
					map.put("list", ListClear(list));
					statusCode = "1";
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
	private List<CouponBean> ListClear(List<Map<String,Object>> list){
		List<CouponBean> bean = new ArrayList<CouponBean>();
		DateUtils du = new DateUtils();
		String dq = du.getCurrentTime();
		for (Map<String,Object> map : list) {
			CouponBean cbean = new CouponBean();
			Object obdate = map.get("begin_Date");
			Object oedate =  map.get("end_date");
			String isenab = (String) map.get("is_Enabled");
			//是否启用
			if(!"1".equals(isenab)){//未启用
				continue;
			}
			if(null!=obdate){
				String bdate = obdate.toString();
				//当前日期大于等于开始日期
//				if(1==du.timeCompare(bdate.split("\\.")[0], dq)){
//					continue;
//				}
				cbean.setStartDate(bdate.split(" ")[0]);
			}else {
				cbean.setStartDate("2015-01-01");
			}
			if(null!=oedate){
				String edate = oedate.toString();
				//当前日期小于等于结束日期
				edate = edate.split("\\.")[0];
				if(-1==du.timeCompare(edate, dq)){
					continue;
				}
				cbean.setEndDate(edate.split(" ")[0]);
			}else {
				cbean.setEndDate("2016-01-01");
			}
			String isenal = (String) map.get("is_Enablemore");
			//是否启用循环
			if("1".equals(isenal)){
				cbean.setName(map.get("name").toString());
			}else {
				String isused = (String) map.get("is_used");
				//判断是否已使用
				if(!"1".equals(isused)){
					cbean.setName(map.get("name").toString());
				}else {
					continue;
				}
			}
			Object id = map.get("id");
			cbean.setCid(id.toString());
			bean.add(cbean);
		}
		return bean;
	}
	public class CouponBean{
		private String cid;
		private String name;
		private String endDate;
		private String startDate;
		
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public String getCid() {
			return cid;
		}
		public void setCid(String cid) {
			this.cid = cid;
		}
	}
}
