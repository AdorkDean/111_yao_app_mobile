package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.Date;
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
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.MemCached;
import com.rc.openapi.util.PageResult;
/**
 * 获取首页活动
 *
 */
public class GetHomePageActivityService extends BaseURIService {

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
		String statusCode = null;
		String str = null;
		try {
			String pageNo = request.getParameter("pageNo");// 当前页数
			if("1".equals(pageNo)){
				try {
					str = (String) MemCached.getmcc().get("homeData1");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if(null==str||"".equals(str)){
				String pageSize = request.getParameter("pageSize");// 显示条数
				List<Map<String,String>> result = getHomeData(pageNo, pageSize);
				int size =result.size();
				if(size==0){
					map.put("moreurl",null);
				}else{
					map.put("moreurl", (Integer.parseInt(pageNo)+1)+"");
				}
				statusCode = "1";
				map.put("list", result);
				map.put("statusCode", statusCode);
				JSONObject json4 = JSONObject.fromObject(map);
				str = json4.toString();
				if("1".equals(pageNo)){
					try{
						MemCached.getmcc().add("homeData1",str,new Date(1000*60*30));
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			map.clear();
			map.put("statusCode", "0");
			JSONObject json4 = JSONObject.fromObject(map);
			str=json4.toString();
			e.printStackTrace();
		}
		ConstantUtil.reJSON(str, request, response);
	}
	@SuppressWarnings("unchecked")
	private List<Map<String,String>> getHomeData(String pageNo,String pageSize) throws Exception{
		//活动id
//		String cid =InfoUtil.getInstance().getInfo("config", "homePageActivityConfigId");
//		int id = Integer.parseInt(cid);
		TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
		String cid = sys.getKeys("homePageActivityConfigId");
		if(!StringUtils.hasText(cid)){
			return null;
		}
		OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
		PageWraper pw = new PageWraper();
		PageResult rs = new PageResult();
		Map map = new HashMap();
		map.put("channel", Long.parseLong(cid));
		map.put("type", "1");
		rs.setP_curPage(Integer.parseInt(pageNo));	// 第一页数据
		rs.setP_pageSize(Integer.parseInt(pageSize)); //  第页显示2条，这里不设置，默认为20条
		pw = om.selectForPageByMap(map, "c_position.selectPositionByPageCount", "c_position.selectPositionByPage", rs.getP_curPage(), rs.getP_pageSize());
		return pw.getResult();
	}
}
