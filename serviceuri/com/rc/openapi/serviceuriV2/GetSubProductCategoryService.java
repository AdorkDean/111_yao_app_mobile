package com.rc.openapi.serviceuriV2;

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

import com.rc.openapi.model.ProductCategoryModel;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
/**
 * 根据ID查分类导航
 *
 */
public class GetSubProductCategoryService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid  = request.getParameter("pid");//分类id
		String statusCode = "0";
		List<ProductCategoryModel> listpcm = new ArrayList<ProductCategoryModel>();
		try{
			OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
			Map<String, Object> mapv = new HashMap();
			mapv.put("parent", Long.parseLong(pid));
//			mapv.put("grade", 1);
			List<Map<String,Object>> list = om.selectForListByMap(mapv, "t_category.selectProductCategoryByGrade");
			if(null!=list&&0<list.size()){
				for (int i = 0; i < list.size(); i++) {
					boolean fla = false;
					Map<String,Object> map = list.get(i);
					ProductCategoryModel obj = new ProductCategoryModel();
					obj.setPid(map.get("id")+"");
					obj.setName(map.get("name")+"");
					obj.setType(0+"");
					List<ProductCategoryModel> listpcm1 = new ArrayList<ProductCategoryModel>();
					mapv.clear();
					mapv.put("parent", map.get("id"));
//					mapv.put("grade", 2);
					List<Map<String,Object>> list1 = om.selectForListByMap(mapv, "t_category.selectProductCategoryByGrade");
					if(null!=list1&&0<list1.size()){
						for (Map<String, Object> map2 : list1) {
							ProductCategoryModel obj1 = new ProductCategoryModel();
							obj1.setPid(map2.get("id")+"");
							obj1.setName(map2.get("name")+"");
							obj1.setType(0+"");
							mapv.clear();
							mapv.put("pcid", map2.get("id"));
							List<Map<String,Object>> img = om.selectForListByMap(mapv, "t_category.selectProductImgByPc");
							if(null!=img&&0<img.size()){
								for (Map<String, Object> map4 : img) {
									obj1.setImgs(map4.get("image")+"");
								}
								listpcm1.add(obj1);
								fla=true;
							}
//							Object opt = map2.get("product_type");
//							if("0"==opt||"0".equals(opt)){
//								List<Map<String,Object>> img = om.selectForListByMap(mapv, "dst_product_category.selectProductImgByPc");
//								if(null!=img&&0<img.size()){
//									for (Map<String, Object> map4 : img) {
//										obj1.setImgs(map4.get("image")+"");
//									}
//									listpcm1.add(obj1);
//									fla=true;
//								}
//							}else {
//								List<Map<String,Object>> img = om.selectForListByMap(mapv, "dst_product_category.selectProductImgByPc1");
//								if(null!=img&&0<img.size()){
//									for (Map<String, Object> map4 : img) {
//										obj1.setImgs(map4.get("image")+"");
//									}
//									listpcm1.add(obj1);
//									fla=true;
//								}
//							}
						}
					}else {
						ProductCategoryModel obj1 = new ProductCategoryModel();
						obj1.setPid(map.get("id")+"");
						obj1.setName(map.get("name")+"");
						obj1.setType(0+"");
						mapv.clear();
						mapv.put("pcid", map.get("id"));
						List<Map<String,Object>> img = om.selectForListByMap(mapv, "t_category.selectProductImgByPc");
						if(null!=img&&0<img.size()){
							for (Map<String, Object> map4 : img) {
								obj1.setImgs(map4.get("image")+"");
							}
							listpcm1.add(obj1);
							fla=true;
						}
//						Object opt = map.get("product_type");
//						if("0"==opt||"0".equals(opt)){
//							List<Map<String,Object>> img = om.selectForListByMap(mapv, "dst_product_category.selectProductImgByPc");
//							if(null!=img&&0<img.size()){
//								for (Map<String, Object> map4 : img) {
//									obj1.setImgs(map4.get("image")+"");
//								}
//								listpcm1.add(obj1);
//								fla=true;
//							}
//						}else {
//							List<Map<String,Object>> img = om.selectForListByMap(mapv, "dst_product_category.selectProductImgByPc1");
//							if(null!=img&&0<img.size()){
//								for (Map<String, Object> map4 : img) {
//									obj1.setImgs(map4.get("image")+"");
//								}
//								listpcm1.add(obj1);
//								fla=true;
//							}
//						}
					}
					obj.setPlist(listpcm1);
					if(fla){
						listpcm.add(obj);
					}
				}
			}
			statusCode="1";
		}catch (Exception e) {
			statusCode = "0";
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", statusCode);
		map.put("list", listpcm);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

}
