package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.PageResult;

/**
 * 获取首页活动
 * 
 */
public class GetHomePageActivityService3 extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = null;
		String str = null;
		try {
			String pageNo = request.getParameter("pageNo");// 当前页数
			String pageSize = request.getParameter("pageSize");// 显示条数
			
			String client_type = request.getParameter("client_type");// 客户端系统
			String type = "1";
			if ("iOS" == client_type || "iOS".equals(client_type)) {
				type = "2";
			} else if ("Android" == client_type || "Android".equals(client_type)) {
				type = "3";
			}
			if(pageNo==null || "".equals(pageNo)){
				pageNo = "1";
			}
			if(pageSize==null || "".equals(pageSize)){
				pageSize = "50";
			}
			if ("1".equals(pageNo)) {
				try {
					str = ""; //(String) MemCached.getmcc().get("homeDataxx3" + client_type);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (null == str || "".equals(str)) {
				List<Map<String, String>> result = getHomeData(pageNo, pageSize, type);
				int size = result.size();
				if (size == 0) {
					map.put("moreurl", null);
				} else {
					map.put("moreurl", (Integer.parseInt(pageNo) + 1) + "");
				}
				statusCode = "1";
				map.put("list", result);
				map.put("statusCode", statusCode);
				JSONObject json4 = JSONObject.fromObject(map);
				str = json4.toString();
				if ("1".equals(pageNo)) {
					try {
						//MemCached.getmcc().add("homeDataxx3" + client_type, str, new Date(1000 * 60 * 30));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			log.equals(e.getMessage());
			map.clear();
			map.put("statusCode", "0");
			JSONObject json4 = JSONObject.fromObject(map);
			str = json4.toString();
			e.printStackTrace();
		}
		ConstantUtil.reJSON(str, request, response);
	}

	private List<Map<String, String>> getHomeData(String pageNo, String pageSize, String type) throws Exception {
		TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
		String cid = sys.getKeys("homePageActivityConfigIdNew");
		if (!StringUtils.hasText(cid)) {
			return null;
		}
		int id = Integer.parseInt(cid);
		OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
		PageWraper pw = new PageWraper();
		PageResult rs = new PageResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channel", id);
		map.put("type", type);
		rs.setP_curPage(Integer.parseInt(pageNo)); // 第一页数据
		rs.setP_pageSize(Integer.parseInt(pageSize)); // 第页显示2条，这里不设置，默认为20条
		pw = om.selectForPageByMap(map, "c_position.selectPositionByPageCount3", "c_position.selectPositionByPage3",
				rs.getP_curPage(), rs.getP_pageSize());
		return pw.getResult();
	}
}
