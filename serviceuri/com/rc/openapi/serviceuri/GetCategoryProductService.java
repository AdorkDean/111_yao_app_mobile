package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.util.ConstantUtil;
/**
 * 查询某类别下的商品
 */
@SuppressWarnings({"unchecked", "rawtypes", "unused"})
public class GetCategoryProductService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsoncallback = request.getParameter("jsoncallback");
		String dataType = request.getParameter("dataType");
		Map map = new HashMap();
		String cId = request.getParameter("cId");
		
		//ptype = 1 全部  2 折扣 3价格
		String ptype = request.getParameter("ptype");
		
		if(null != cId && !("").equals(cId) && null != ptype && !("").equals(ptype)){
			String pageNo = request.getParameter("pageNo");// 当前页数
			String pageSize = request.getParameter("pageSize");// 显示条数
			if(null == pageNo || ("").equals(pageNo) || "" == pageNo){
				pageNo = "1";
			}
			if(null == pageSize || ("").equals(pageSize) || "" == pageSize){
				pageSize = "10";
			}		
			Map map1 = new HashMap();
			map1.put("cId", cId);
			map1.put("cIdS", "%,"+cId+",%");
			if(ptype.equals("1")){
				map1.put("order", " order by gp.is_top desc");	
			}else if(ptype.equals("2")){
				map1.put("order", " order by gp.price / d.price asc");
			}else if(ptype.equals("3")){
				map1.put("order", " order by gp.price asc");
			}else {
				map1.put("order", " order by gp.price desc");
			}
			if(dataType!=null&&!"".equals(dataType)){
				map1.put("price_type", "wap");
			}else{
				map1.put("price_type", "app");
			}
			try {
				OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
				PageWraper pw = new PageWraper();
				//此项目的sqlMap(t_goods.select_product_category)不建议在其他地方;
				pw = om.selectForPageByMap(map1, "t_goods.selectCount",
						"t_goods.select_product_category", new Integer(pageNo), new Integer(pageSize));
				map.put("statusCode", "1");
				map.put("cId", cId);
				map.put("ptype", ptype);
				map.put("list", pw.getResult());
			} catch (Exception e) {
				e.printStackTrace();
				map.put("statusCode", "0");
			}
		}else{
			map.put("statusCode", "2");
		}
		JSONObject json4 = JSONObject.fromObject(map);
		
		if (dataType != null) {
			ConstantUtil.reJSON(jsoncallback + "(" + json4.toString() + ");", request, response);
		} else {
			ConstantUtil.reJSON(json4.toString(), request, response);
		}
	}

}
