package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.dst.client.util.ClientSubmit;
import com.rc.openapi.model.ProductVo;
import com.rc.openapi.model.SeachIndexModel;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
/**
 * 关键字检索
 *
 */
public class FullSearchService extends BaseURIService {

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
		//ptype = 1 全部  2 折扣 3价格
		String ptype = request.getParameter("ptype");
		if(null != keyword && !("").equals(keyword) && null != ptype && !("").equals(ptype)){
			String pageNo = request.getParameter("pageNo");// 当前页数
			String pageSize = request.getParameter("pageSize");// 显示条数
			if(null == pageNo || ("").equals(pageNo) || "" == pageNo){
				pageNo = "1";
			}
			if(null == pageSize || ("").equals(pageSize) || "" == pageSize){
				pageSize = "10";
			}	
			
			Map map1 = new HashMap();
			map1.put("parnterid", "1001");
			map1.put("method", "search");
			map1.put("from", "3");
			if(ptype.equals("1")){
				map1.put("sort", "-1");	
			}else if(ptype.equals("2")){
				map1.put("sort", "4");	
			}else{
				map1.put("sort", "3");	
			}
			map1.put("order", "2");
			map1.put("pricea", "0");
			map1.put("priceb", "90000");
			map1.put("typeid", "-1");
			map1.put("brandid", "-1");
			map1.put("keyword",keyword);
			map1.put("filtertype","1");
			map1.put("page", pageNo);
			map1.put("size", pageSize);			
			String YAO_GATEWAY_URL = InfoUtil.getInstance().getInfo("config", "yao_geteway_url");
			System.out.println("======="+YAO_GATEWAY_URL);
			String returnStr = "";
			try {
				returnStr = ClientSubmit.buildRequestForSearch(map1,YAO_GATEWAY_URL);
				System.out.println("returnStr======"+returnStr);
				JSONObject jsonObject = JSONObject.fromObject(returnStr);
				JSONArray jsonArray = jsonObject.getJSONArray("models");
				List<SeachIndexModel> ll = (List<SeachIndexModel>) jsonArray.toCollection(jsonArray, SeachIndexModel.class);
			    List<ProductVo> pList = new ArrayList<ProductVo>();
			    ProductVo productVo = null;
				for(int i=0;i<ll.size();i++){
					SeachIndexModel im = ll.get(i);
					productVo = new ProductVo();
					productVo.setPid(im.getGoodsid());
					productVo.setImage(im.getImgpath());
					productVo.setDiscount(im.getDiscount());
					productVo.setName(im.getFullname());
					productVo.setPrice(im.getRealprice());
					productVo.setMarketPrice(im.getPrice());
					if(null!=im.getIsrx()&&!("").equals(im.getIsrx())&& im.getIsrx().equals("0")){
						productVo.setType("1");
					}else if(null!=im.getIsrx()&&!("").equals(im.getIsrx())&& im.getIsrx().equals("1")){
						productVo.setType("3");
					}else if(null!=im.getIsrx()&&!("").equals(im.getIsrx())&& im.getIsrx().equals("2")){
						productVo.setType("4");
					}else{
						productVo.setType("4");
					}
					pList.add(productVo);
			    	System.out.println(im.getShortname());
			    }
				map.put("list", pList);
			    map.put("statusCode", "1");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("statusCode", "0");
			}
			
		}else{
			map.put("statusCode", "2");
		}
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
}
