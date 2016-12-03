package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.PageResult;

/**
 * 营销活动:9块9,龙大夫等..
 * 
 */
public class GetPromotionProductPageService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageNo = request.getParameter("pageNo");// 当前页数
		String pageSize = request.getParameter("pageSize");// 显示条数
		Map map = new HashMap();
		String statusCode = null;
		try{
			OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
			PageWraper pw = new PageWraper();
			PageResult rs = new PageResult();
			TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
			String promotion = sys.getKeys("baoyouhuodong");
			map.put("promId", Integer.parseInt(promotion));
			rs.setP_curPage(Integer.parseInt(pageNo)); // 第一页数据
			rs.setP_pageSize(Integer.parseInt(pageSize)); // 第页显示2条，这里不设置，默认为20条
			pw = om.selectForPageByMap(map,
					"c_position.selectPromotionProductByPageCount",
					"c_position.selectPromotionProductByPage",
					rs.getP_curPage(), rs.getP_pageSize());
			List<Map<String, String>> result = pw.getResult();
			map.clear();
			int size = result.size();
			if (size == 0) {
				map.put("moreurl", null);
			} else {
				map.put("moreurl", (Integer.parseInt(pageNo) + 1) + "");
			}
			map.put("list", result);
			statusCode = "1";
		}catch (Exception e) {
			statusCode = "0";
			e.printStackTrace();
		}
		map.put("statusCode", statusCode);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

}
