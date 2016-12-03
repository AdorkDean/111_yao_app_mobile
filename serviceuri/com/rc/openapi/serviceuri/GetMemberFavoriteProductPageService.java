package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.sql.SQLException;
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
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.PageResult;
/**
 * 查询用户收藏商品
 *
 */
public class GetMemberFavoriteProductPageService extends BaseURIService {

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
					String pageNo = request.getParameter("pageNo");// 当前页数
					String pageSize = request.getParameter("pageSize");// 显示条数
					OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
					PageWraper pw = new PageWraper();
					PageResult rs = new PageResult();
					map.put("membId", userId);
					map.put("type", 0);
					rs.setP_curPage(Integer.parseInt(pageNo));	// 第一页数据
					rs.setP_pageSize(Integer.parseInt(pageSize)); //  第页显示2条，这里不设置，默认为20条
					pw = om.selectForPageByMap(map, "t_member_collect.selectMemberFavoriteBrandByPageCount1", "t_member_collect.selectMemberFavoriteBrandByPage1", rs.getP_curPage(), rs.getP_pageSize());
					map.clear();
					List<Map<String,String>> result = pw.getResult();
					int size =pw.getResult().size();
					if(size==0){
						map.put("moreurl",null);
					}else{
						map.put("moreurl", (Integer.parseInt(pageNo)+1)+"");
					}
					statusCode = "1";
					map.put("list", result);
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

}

