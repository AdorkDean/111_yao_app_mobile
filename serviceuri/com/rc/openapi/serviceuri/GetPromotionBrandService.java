package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.MemCached;


/**
 * 品牌特卖
 *
 */
public class GetPromotionBrandService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	/**
	 * promId 需要 有品牌特卖这个营销活动,如果没有则人工添加后查询ID
	 */
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String str = null;
		try {
			try {
				str = (String) MemCached.getmcc().get("ppData");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(null==str||"".equals(str)){
				str = getPPData();
				try {
					MemCached.getmcc().add("ppData",str,new Date(1000*60*30));
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			Map map = new HashMap();
			map.put("statusCode", "0");
			JSONObject json4 = JSONObject.fromObject(map);
			str=json4.toString();
			e.printStackTrace();
		}
		ConstantUtil.reJSON(str, request, response);
	}
	@SuppressWarnings("unchecked")
	private String getPPData() throws Exception{
//		String promId=InfoUtil.getInstance().getInfo("config", "promotionBrand");
//		String promId = request.getParameter("promId");
		Map map = new HashMap();
		String statusCode = "0";
		List<BrandData> mapList = null;
		OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
		List<Map<String,Object>> list = om.selectForListByMap(map, "t_promotion.selectPromotionBrand");
		mapList = listToLitMap(list);
		statusCode = "1";
		map.clear();
		map.put("list", mapList);
		map.put("statusCode", statusCode);
		JSONObject json4 = JSONObject.fromObject(map);
		return json4.toString();
	}
	private List<BrandData> listToLitMap(List<Map<String,Object>> list){
		Map<String, List<BrandList>> listMap = new LinkedHashMap<String,List<BrandList>>();
		for (Map<String,Object> brandList : list) {
			BrandList bean = new BrandList();
			Object id = brandList.get("id");
			bean.setId(id.toString());
			bean.setName(brandList.get("name").toString());
			String pys = (String) brandList.get("pingyin");
			String py = (String) pys.subSequence(0,1);
			bean.setPingyin(py);
			if(null==listMap.get(py)){
				List<BrandList> nlist = new ArrayList<BrandList>();
				if(isNum(py)){
					if(null==listMap.get("num")){
						nlist.add(bean);
						listMap.put("num", nlist);
					}else {
						List<BrandList> numlist = listMap.get("num");
						numlist.add(bean);
					}
				}else {
					nlist.add(bean);
					listMap.put(py, nlist);
				}
			}else {
				List<BrandList> nlist = listMap.get(py);
				nlist.add(bean);
			}
		}
//		List<BrandData> listobj = new ArrayList<BrandData>();
//		  Set<Map.Entry<String, List<BrandList>>> set = listMap.entrySet();
//	        for (Iterator<Map.Entry<String, List<BrandList>>> it = set.iterator(); it.hasNext();) {
//	            Map.Entry<String, List<BrandList>> entry = (Map.Entry<String, List<BrandList>>) it.next();
//	            BrandData bd = new BrandData();
//	            bd.setZimu(entry.getKey());
//	            bd.setList(entry.getValue());
//	            listobj.add(bd);
//	        }
        List<BrandData> nlistobj = new ArrayList<BrandData>();
	    String[] zimu = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","num"};
		for (String zm : zimu) {
			if(null!=listMap.get(zm)){
				List<BrandList> bl = listMap.get(zm);
				BrandData bd = new BrandData();
				bd.setList(bl);
				bd.setZimu(zm);
				nlistobj.add(bd);
			}else {
				BrandData bd = new BrandData();
				bd.setList(null);
				bd.setZimu(zm);
				nlistobj.add(bd);
			}
		}
	    return nlistobj;
	}
	private boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	public class BrandData{
		private String zimu;
		private List<BrandList> list;
		public String getZimu() {
			return zimu;
		}
		public void setZimu(String zimu) {
			this.zimu = zimu;
		}
		public List<BrandList> getList() {
			return list;
		}
		public void setList(List<BrandList> list) {
			this.list = list;
		}
		
	}
	public class BrandList{
		private String id;
		private String pingyin;
		private String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getPingyin() {
			return pingyin;
		}
		public void setPingyin(String pingyin) {
			this.pingyin = pingyin;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
