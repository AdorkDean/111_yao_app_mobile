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
 * 查询某品牌下的商品
 *
 */
public class GetBrandProductService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map map = new HashMap();
		String bId = request.getParameter("bId");
		
		//ptype = 1 全部  2 折扣 3价格
		String ptype = request.getParameter("ptype");
		if(null != bId && !("").equals(bId) && null != ptype && !("").equals(ptype)){
			String pageNo = request.getParameter("pageNo");// 当前页数
			String pageSize = request.getParameter("pageSize");// 显示条数
			if(null == pageNo || ("").equals(pageNo) || "" == pageNo){
				pageNo = "1";
			}
			if(null == pageSize || ("").equals(pageSize) || "" == pageSize){
				pageSize = "10";
			}		
			Map map1 = new HashMap();
			map1.put("bId", bId);
			if(ptype.equals("1")){
				map1.put("order", " order by gp.is_top desc");	
			}else if(ptype.equals("2")){
				map1.put("order", " order by gp.price / d.price asc");
			}else if(ptype.equals("3")){
				map1.put("order", " order by gp.price asc");
			}else {
				map1.put("order", " order by gp.price desc");
			}
			try {
				OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
				PageWraper pw = new PageWraper();
				pw = om.selectForPageByMap(map1, "t_goods.selectCount_brand",
						"t_goods.select_product_brand", new Integer(pageNo), new Integer(pageSize));
				map.put("statusCode", "1");
				map.put("ptype", ptype);
				map.put("bId", bId);
				map.put("list", pw.getResult());
			} catch (Exception e) {
				e.printStackTrace();
				map.put("statusCode", "0");
			}
		}else{
			map.put("statusCode", "2");
		}
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

}
