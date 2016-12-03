package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.PageResult;

public class OrderPingLunPageURIService extends BaseURIService {

	private OpenSqlManage opensqlmanage;

	
	
	private PageWraper pw = new PageWraper();
	private PageResult rs = new PageResult();
	
	
	public static void main(String[] args) throws SQLException {
		
//		AddPingLunURIService o = new AddPingLunURIService();
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
//		o.opensqlmanage = (OpenSqlManage) context.getBean("opensqlmanage");
//		o.dstreviewmanager = (DstReviewManager) context.getBean("dstreviewmanager");
//		o.npinglunmanager = (NPinglunManager) context.getBean("npinglunmanager");
//		o.dstproductmanager = (DstProductManager) context.getBean("dstproductmanager");
		
	
	}
	

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		opensqlmanage=(OpenSqlManage)getSpringBean("opensqlmanage");
		String orderId = req.getParameter("order_id");
		String page = req.getParameter("page");
		rs.setP_curPage(1);
		
		Map map = new HashMap();
		map.put("order_id", orderId);
//		List<HashMap> list = opensqlmanage.selectForListByMap(map, "n_pinglun.order-to-pl-page");
		rs.setP_pageSize(200000);
		pw = opensqlmanage.selectForPageByMap(map, "t_goods_comment.order-to-pl-page-count", "t_goods_comment.order-to-pl-page", rs.getP_curPage(), rs.getP_pageSize());
		 
		String statusCode = "1";
		Map result = new HashMap();
		result.put("statusCode", statusCode);
		result.put("list", pw.getResult());
		ConstantUtil.reJSON(JSONObject.fromObject(result).toString(), req, resp);
	}


}
