package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.model.OrderVo;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
/**
 * 用户带付款订单
 * @author Administrator
 *
 */
public class GetWaitReceiveService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974362142L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");
		String pageNo = request.getParameter("pageNo");// 当前页数
		String pageSize = request.getParameter("pageSize");// 显示条数
		Map map = new HashMap();
		if(null != sessionId && !("").equals(sessionId)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl)getSpringBean("tmembermanager");
			Long userId;
			try {
				userId = tmembermanager.getMemberId(sessionId);
			
				if(null != userId && userId != -1){
					if(null == pageNo || ("").equals(pageNo) || ""==pageNo){
						pageNo = "1";
					}
					if(null == pageSize || ("").equals(pageSize) || ""==pageSize){
						pageSize = "5";
					}
					OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
					Map map1 = new HashMap();
					map1.put("mId", userId);
					map1.put("receive", "1");
					PageWraper pw = new PageWraper();
					pw = om.selectForPageByMap(map1, "t_order.select_order_portion_count", "t_order.select_order_portion", new Integer(pageNo),  new Integer(pageSize));
					int size = pw.getResult().size();
					for(int i=0;i<size;i++){
						OrderVo order = (OrderVo) pw.getResult().get(i);
						map1.put("oId", order.getId());
						order.setList(om.selectForListByMap(map1, "t_order.selectOrderItem"));
					}
					map.put("statusCode", "1");
					map.put("list", pw.getResult());
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
