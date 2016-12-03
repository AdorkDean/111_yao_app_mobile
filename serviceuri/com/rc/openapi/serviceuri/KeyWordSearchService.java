package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.MD5;
import com.rc.openapi.util.MemCached;
/**
 * 检索
 *
 */
public class KeyWordSearchService extends BaseURIService {

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
		String keyword = request.getParameter("keyword");
		if(null != keyword && !("").equals(keyword) ){
			String  returnStr=	(String) MemCached.getmcc().get(MD5.MD5(keyword));
			if(null != returnStr){
				JSONArray jsonarray = JSONArray.fromObject(returnStr); 
				List list = (List)JSONArray.toCollection(jsonarray, Map.class); 
				map.put("statusCode", "1");
				map.put("list", list);
			}else{
				String pageNo = request.getParameter("pageNo");// 当前页数
				String pageSize = request.getParameter("pageSize");// 显示条数
				if(null == pageNo || ("").equals(pageNo) || "" == pageNo){
					pageNo = "1";
				}
				if(null == pageSize || ("").equals(pageSize) || "" == pageSize){
					pageSize = "10";
				}
				
				Map map1 = new HashMap();
				map1.put("likekeyword", "'"+likeKeyword(keyword)+"'");
				map1.put("pageNo", pageNo);
				map1.put("pageSize", pageSize);
				try {
					OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
					List list = om.selectForListByMap(map1, "t_goods.select_keyword_search");
					map.put("statusCode", "1");
					map.put("list", list);
					if(null != list &&list.size()>0){
						JSONArray jsonArray = JSONArray.fromObject(list);
						System.out.println(jsonArray.toString());
						MemCached.getmcc().set(MD5.MD5(keyword), jsonArray.toString(),new Date(1000*300));
					}
				} catch (Exception e) {
					e.printStackTrace();
					map.put("statusCode", "0");
				}
			}
		}else{
			map.put("statusCode", "2");
		}
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

	private static String likeKeyword(String keyword){
		String s1[] = keyword.split("");
		String s2 ="%";
		for(int i=1;i<s1.length;i++){
			s2 += s1[i]+"%"; 
		}
		return s2;
	}

}
