package com.rc.openapi.serviceuriV2;

import java.io.IOException;
import java.util.ArrayList;
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

import com.rc.openapi.model.ProductCategoryModel;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.MemCached;
/**
 * 初始化分类导航
 *
 */
public class GetProductCategoryService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String str = null;
		try {
			try {
				str = (String) MemCached.getmcc().get("homeDatapc");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(null==str||"".equals(str)){
				str = getHomeData();
				try {
					MemCached.getmcc().add("homeDatapc",str,new Date(1000*60*30));
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
	private String getHomeData() throws Exception{
		String statusCode = "0";
		List<ProductCategoryModel> listpcm = new ArrayList<ProductCategoryModel>();
		try {
			TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
			String category_id = sys.getKeys("goods_category_id");
			if(!StringUtils.hasText(category_id)){
				return null;
			}
			OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
			Map<String, Object> mapv = new HashMap();
//			mapv.put("grade", 0);
			mapv.put("parent", Long.parseLong(category_id));
			List<Map<String,Object>> list = om.selectForListByMap(mapv, "t_category.selectProductCategoryByGrade");
			for (int i = 0; i < list.size(); i++) {
				//1
				Map<String,Object> map = list.get(i);
				ProductCategoryModel obj = new ProductCategoryModel();
				obj.setPid(map.get("id")+"");
				obj.setName(map.get("name")+"");
				obj.setType(""+0);
				List<ProductCategoryModel> listpcm1 = new ArrayList<ProductCategoryModel>();
				if(0==i){
					//2      
					mapv.clear();
					mapv.put("parent", map.get("id"));
					List<Map<String,Object>> listn = om.selectForListByMap(mapv, "t_category.selectProductCategoryByGrade");
					for (Map<String, Object> map2 : listn) {
						//3
						boolean fla = false;
						ProductCategoryModel obj1 = new ProductCategoryModel();
						obj1.setPid(map2.get("id")+"");
						obj1.setName(map2.get("name")+"");
						obj1.setType(0+"");
						mapv.clear();
						mapv.put("parent", map2.get("id"));
						List<Map<String,Object>> listn1 = om.selectForListByMap(mapv, "t_category.selectProductCategoryByGrade");
						List<ProductCategoryModel> listpcm2 = new ArrayList<ProductCategoryModel>();
						if(null!=listn1&&0<listn1.size()){
							for (Map<String, Object> map3 : listn1) {
								ProductCategoryModel obj2 = new ProductCategoryModel();
								obj2.setPid(map3.get("id")+"");
								obj2.setName(map3.get("name")+"");
								obj2.setType(0+"");
								mapv.clear();
								mapv.put("pcid", map3.get("id"));
								List<Map<String,Object>> img = om.selectForListByMap(mapv, "t_category.selectProductImgByPc");
								if(null!=img&&0<img.size()){
									for (Map<String, Object> map4 : img) {
										obj2.setImgs(map4.get("image")+"");
									}
									listpcm2.add(obj2);
									fla=true;
								}
							}
						}else {
							ProductCategoryModel obj2 = new ProductCategoryModel();
							obj2.setPid(map2.get("id")+"");
							obj2.setName(map2.get("name")+"");
							obj2.setType(0+"");
							mapv.clear();
							mapv.put("pcid", map2.get("id"));
							List<Map<String,Object>> img = om.selectForListByMap(mapv, "t_category.selectProductImgByPc");
							if(null!=img&&0<img.size()){
								for (Map<String, Object> map4 : img) {
									obj2.setImgs(map4.get("image")+"");
								}
								listpcm2.add(obj2);
								fla=true;
							}
						}
						obj1.setPlist(listpcm2);
						if(fla){
							listpcm1.add(obj1);
						}
					}
				}
				obj.setPlist(listpcm1);
				listpcm.add(obj);
			}
			statusCode = "1";
		}catch (Exception e) {
			statusCode = "0";
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", statusCode);
		map.put("list", listpcm);
		JSONObject json4 = JSONObject.fromObject(map);
		return json4.toString();
	}

}
