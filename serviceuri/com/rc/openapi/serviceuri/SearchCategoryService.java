package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.model.BigTableVO;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
/**
 * 检索页面底部热词，分类
 *
 */
public class SearchCategoryService extends BaseURIService {

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
		try {
			OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
			List rList = om.selectForListByMap(map, "open_sql.selectBigTable_keyword");
//			List rList = new ArrayList();
			List pList = om.selectForListByMap(map, "open_sql.selectBigTable_parent");
//			List pList = new ArrayList();
			String isThink = InfoUtil.getInstance().getInfo("config", "isThink");
			String name = (String) om.selectForObjectByMap(map, "open_sql.select_default"); 
			if(null != pList && pList.size()>0){
				int size = pList.size();
				for(int i=0;i<size;i++){
					BigTableVO bt = (BigTableVO) pList.get(i);
					map.put("cid", bt.getcId());
					List sList = om.selectForListByMap(map, "open_sql.selectBigTable_son");
				    bt.setBigList(sList);
				}
			}
			map.put("isThink", isThink);
			map.put("rList", rList);
			map.put("pList", pList);
			map.put("statusCode", "1");
			map.put("defaultName", "感冒");
		} catch (Exception e) {
			e.printStackTrace();
			map.clear();
			map.put("statusCode", "0");
		}
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	public static List  rList(){
		List rList = new ArrayList();
		Map map = new HashMap();
		map.put("bigList", "[]");
		map.put("cId", "-100");
		map.put("name", "消化系统");
		map.put("type", "3");
		rList.add(map);
		Map map1 = new HashMap();
		map1.put("bigList", "[]");
		map1.put("cId", "-100");
		map1.put("name", "内分泌");
		map1.put("type", "3");
		rList.add(map1);
		Map map2 = new HashMap();
		map2.put("bigList", "[]");
		map2.put("cId", "-100");
		map2.put("name", "心脑血管");
		map2.put("type", "3");
		rList.add(map2);
		Map map3 = new HashMap();
		map3.put("bigList", "[]");
		map3.put("cId", "-100");
		map3.put("name", "血糖仪");
		map3.put("type", "3");
		rList.add(map3);
		Map map4 = new HashMap();
		map4.put("bigList", "[]");
		map4.put("cId", "-100");
		map4.put("name", "血压仪");
		map4.put("type", "3");
		rList.add(map4);
		return rList;
	}

	public static List  pList(){
		List pList = new ArrayList();
		Map map = new HashMap();
		map.put("cId", "10");
		map.put("name", "中西成药 ");
		map.put("type", "2");
		
		List rList = new ArrayList();
		Map map1 = new HashMap();
		map1.put("bigList", "[]");
		map1.put("cId", "82");
		map1.put("name", "内分泌系统");
		map1.put("type", "2");
		rList.add(map1);
		Map map2 = new HashMap();
		map2.put("bigList", "[]");
		map2.put("cId", "43");
		map2.put("name", "滋补类 ");
		map2.put("type", "2");
		rList.add(map2);
		Map map3 = new HashMap();
		map3.put("bigList", "[]");
		map3.put("cId", "33");
		map3.put("name", "妇科类 ");
		map3.put("type", "2");
		rList.add(map3);
		Map map4 = new HashMap();
		map4.put("bigList", "[]");
		map4.put("cId", "16");
		map4.put("name", "清热解毒类");
		map4.put("type", "2");
		rList.add(map4);
		map.put("bigList", rList);
		pList.add(map);
		return pList;
	}
	

}
