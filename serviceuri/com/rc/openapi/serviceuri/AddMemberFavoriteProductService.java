package com.rc.openapi.serviceuri;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.TMemberCollectManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TMemberCollect;
/**
 * 添加商品收藏
 *
 */

public class AddMemberFavoriteProductService extends BaseURIService {

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
		String prodId = request.getParameter("prodId");// 商品Id
		Map map = new HashMap();
		String statusCode = null;
		if(StringUtils.hasText(sessionid)&&StringUtils.hasText(prodId)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			try {
				Long userId = tmembermanager.getMemberId(sessionid);
				if(0<userId){
					TMemberCollect bean = new TMemberCollect();
					bean.setCollectType(0);//1品牌  0商品
					bean.setRelevanceId(Long.parseLong(prodId.trim()));
					bean.setMemberId(userId);
					bean.setCreateDate(new Date());
					((TMemberCollectManager)getSpringBean("tmembercollectmanager")).insertSelective(bean);
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
}
